package com.cntaiping.tpi.edas.mybatis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.baomidou.mybatisplus.toolkit.PackageHelper;

public class DAORegPostProcessor implements
		BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		ClassPathDAODefinitionScanner scanner = new ClassPathDAODefinitionScanner(
				(BeanDefinitionRegistry) applicationContext);
		// scanner.setAnnotationClass();
		scanner.setResourceLoader(applicationContext);
		String[] scanPath = PackageHelper
				.convertTypeAliasesPackage("com.cntaiping.tpi.edas.*.mapper");
		scanner.scan(scanPath);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
