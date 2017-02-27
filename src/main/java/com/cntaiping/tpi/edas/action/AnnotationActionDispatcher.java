package com.cntaiping.tpi.edas.action;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import com.cntaiping.tpi.edas.annotation.Action;

public class AnnotationActionDispatcher
		implements IActionDispatcher, BeanDefinitionRegistryPostProcessor, ApplicationContextAware {
	ApplicationContext applicationContext;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	private String createKey(String module, String app, String page) {
		return module + ":" + app + ":" + page;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		for (String beanName : registry.getBeanDefinitionNames()) {
			BeanDefinition bd = registry.getBeanDefinition(beanName);
			if (StringUtils.isEmpty(bd.getBeanClassName())) {
				continue;
			}
			try {
				Class<?> beanClazz = this.getClass().getClassLoader().loadClass(bd.getBeanClassName());
				Action action = beanClazz.getAnnotation(Action.class);
				if (action != null) {
					registry.registerAlias(beanName, createKey(action.module(), action.app(), action.page()));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public PageAction get(String module, String app, String page) {
		return applicationContext.getBean(createKey(module, app, page), PageAction.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
