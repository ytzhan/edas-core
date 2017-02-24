package com.cntaiping.tpi.edas.rpc;

import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.util.ClassUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.taobao.hsf.app.spring.util.HSFSpringConsumerBean;

public class RPCRefRegProcessor implements
		BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

	private ApplicationContext applicationContext;

	private String refType = "none";

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		this.applicationContext = applicationContext;

	}

	protected void parseAppCfg(Resource cfgRes) throws Exception {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = cfgRes.getInputStream();
			prop.load(cfgRes.getInputStream());
			this.refType = prop.getProperty("tp.rpc.ref.type", "none");

		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {

				}
			}
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		Resource cfgRes = applicationContext
				.getResource("application.properties");
		try {
			if (cfgRes.exists()) {
				parseAppCfg(cfgRes);
			}
			if ("none".equals(refType)) {
				System.out.println("RPC客户端未开启");
				return;
			}
			Resource[] ress = applicationContext
					.getResources("classpath*:rpc/*/*.xml");
			for (Resource res : ress) {
				parseRPCConsumer(res, registry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void parseRPCConsumer(Resource res,
			BeanDefinitionRegistry registry) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		InputStream in = null;
		try {
			in = res.getInputStream();
			Document doc = docBuilder.parse(in);
			NodeList list = doc.getElementsByTagName("service");
			for (int i = 0; i < list.getLength(); i++) {
				Element ele = (Element) list.item(i);
				String intf = ele.getAttribute("interface");
				String group = ele.getAttribute("group");
				String version = ele.getAttribute("version");
				String hessian = ele.getAttribute("hessian");
				regRPCRefBean(intf, group, version, hessian, registry);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {

				}
			}
		}

	}

	protected void regRPCRefBean(String intf, String group, String version,
			String hessian, BeanDefinitionRegistry registry)
			throws ClassNotFoundException, LinkageError {
		Class<?> infClazz = ClassUtils.forName(intf, this.getClass()
				.getClassLoader());
		if ("hessian".equals(refType)) {
			BeanDefinitionBuilder builder = BeanDefinitionBuilder
					.genericBeanDefinition(HessianProxyFactoryBean.class);
			builder.addPropertyValue("serviceUrl", hessian);
			builder.addPropertyValue("serviceInterface", infClazz);
			registry.registerBeanDefinition(infClazz.getName(),
					builder.getBeanDefinition());
		} else if ("hsf".equals(refType)) {
			BeanDefinitionBuilder builder = BeanDefinitionBuilder
					.genericBeanDefinition(HSFSpringConsumerBean.class);
			builder.addPropertyValue("interfaceName", intf);
			builder.addPropertyValue("group", group);
			builder.addPropertyValue("version", version);
			registry.registerBeanDefinition(infClazz.getName(),
					builder.getBeanDefinition());
		}
	}
}
