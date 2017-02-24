package com.cntaiping.tpi.edas.rpc;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.util.StringUtils;

import com.cntaiping.tpi.edas.annotation.RPC;
import com.taobao.hsf.app.spring.util.HSFSpringProviderBean;

public class RPCServRegProcessor implements
		BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

	private ApplicationContext applicationContext;
	private String servType = "none";
	private String servGroup = "default";

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
			this.servType = prop.getProperty("tp.rpc.serv.type", "none");
			this.servGroup = prop.getProperty("tp.rpc.serv.type", "default");
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
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
			if ("none".equals(servType)) {
				System.out.println("RPC服务端未开启");
				return;
			}
			regRPCExportBean(registry);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void regRPCExportBean(BeanDefinitionRegistry registry)
			throws ClassNotFoundException, LinkageError {

		for (String beanName : registry.getBeanDefinitionNames()) {
			BeanDefinition bd = registry.getBeanDefinition(beanName);
			if (StringUtils.isEmpty(bd.getBeanClassName())) {
				continue;
			}
			try {
				Class<?> beanClazz = this.getClass().getClassLoader()
						.loadClass(bd.getBeanClassName());
				if ("hessian".equals(servType)) {
					registerHessian(registry, beanName, beanClazz);
				} else if ("hsf".equals(servType)) {
					registerHSF(registry, beanName, beanClazz);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

	}

	private void registerHessian(BeanDefinitionRegistry registry,
			String beanName, Class<?> beanClazz) {
		RPC dp = beanClazz.getAnnotation(RPC.class);
		if (dp != null) {
			Class<?>[] intfs = dp.interfaceClass();
			if (intfs.length == 1 && intfs[0] == void.class) {
				intfs[0] = beanClazz.getInterfaces()[0];
			}
			for (Class<?> intf : intfs) {
				BeanDefinitionBuilder builder = BeanDefinitionBuilder
						.genericBeanDefinition(HessianServiceExporter.class);
				builder.addPropertyReference("service", beanName);
				builder.addPropertyValue("serviceInterface", intf);

				registry.registerBeanDefinition("/hessian/" + intf.getName(),
						builder.getBeanDefinition());
			}
		}
	}

	private void registerHSF(BeanDefinitionRegistry registry, String beanName,
			Class<?> beanClazz) {
		RPC dp = beanClazz.getAnnotation(RPC.class);
		if (dp != null) {
			Class<?>[] intfs = dp.interfaceClass();
			if (intfs.length == 1 && intfs[0] == void.class) {
				intfs[0] = beanClazz.getInterfaces()[0];
			}
			for (Class<?> intf : intfs) {
				String tmpBeanName = servGroup + "-provider-" + intf.getName();
				if (registry.containsBeanDefinition(tmpBeanName)) {
					System.err.println("over writer :" + tmpBeanName);
				}
				BeanDefinitionBuilder builder = BeanDefinitionBuilder
						.genericBeanDefinition(HSFSpringProviderBean.class);
				builder.addPropertyValue("serviceInterface", intf.getName());
				builder.addPropertyReference("target", beanName);
				builder.addPropertyValue("version", dp.version());
				builder.addPropertyValue("serviceGroup", servGroup);
				registry.registerBeanDefinition(tmpBeanName,
						builder.getBeanDefinition());

			}
		}
	}
}
