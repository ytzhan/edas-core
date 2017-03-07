package com.cntaiping.tpi.edas.action.validator;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class ValidatorFactory implements InitializingBean {
	Map<String, Class<?>> validatorClazzs = new HashMap<String, Class<?>>();

	public Class<?> getValidatorClazz(String name) {
		Class<?> clazz = validatorClazzs.get(name);
		Assert.notNull(clazz, "未找到校验器:" + name);
		return clazz;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Properties prop = new Properties();
		Enumeration<URL> urls = this.getClass().getClassLoader()
				.getResources("META-INF/resources/validator.properties");
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			InputStream in = null;
			try {
				in = url.openStream();
				prop.load(in);
			} catch (Exception e) {
				if (in != null) {
					try {
						in.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		for (Map.Entry<?, ?> entry : prop.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			Class<?> clazz = ClassUtils.resolveClassName(value, this.getClass().getClassLoader());
			validatorClazzs.put(key, clazz);
		}
	}

}
