package com.cntaiping.tpi.edas.mybatis;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import com.cntaiping.tpi.edas.BaseMapper;
import com.cntaiping.tpi.edas.annotation.Mapper;

public class ClassPathDAODefinitionScanner extends
		ClassPathBeanDefinitionScanner {

	private MapperFactoryBean<?> mapperFactoryBean = new MapperFactoryBean<Object>();

	public ClassPathDAODefinitionScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

		if (beanDefinitions.isEmpty()) {
			logger.warn("No MyBatis mapper was found in '"
					+ Arrays.toString(basePackages)
					+ "' package. Please check your configuration.");
		} else {
			processBeanDefinitions(beanDefinitions);
		}

		return beanDefinitions;
	}

	@Override
	protected boolean isCandidateComponent(MetadataReader metadataReader)
			throws IOException {
		String clazzName = metadataReader.getClassMetadata().getClassName();
		try {
			Class<?> clazz = ClassUtils.forName(clazzName, getClass()
					.getClassLoader());
			if (BaseMapper.class.isAssignableFrom(clazz)
					|| clazz.getAnnotation(Mapper.class) != null) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (LinkageError e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected boolean isCandidateComponent(
			AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isInterface()
				&& beanDefinition.getMetadata().isIndependent();
	}

	private void processBeanDefinitions(
			Set<BeanDefinitionHolder> beanDefinitions) {
		GenericBeanDefinition definition;
		for (BeanDefinitionHolder holder : beanDefinitions) {
			definition = (GenericBeanDefinition) holder.getBeanDefinition();

			logger.info("Creating MapperFactoryBean with name '"
					+ holder.getBeanName() + "' and '"
					+ definition.getBeanClassName() + "' mapperInterface");

			// the mapper interface is the original class of the bean
			// but, the actual class of the bean is MapperFactoryBean
			definition.getConstructorArgumentValues().addGenericArgumentValue(
					definition.getBeanClassName()); // issue #59
			definition.setBeanClass(this.mapperFactoryBean.getClass());

			definition.getPropertyValues().add("addToConfig", true);

			definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		}
	}

}
