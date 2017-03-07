package com.cntaiping.tpi.edas.action;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.cntaiping.tpi.edas.action.validator.ValidatorFactory;
import com.cntaiping.tpi.edas.annotation.Action;
import com.cntaiping.tpi.edas.util.WebUtil;

public class AnnotationActionDispatcher implements IActionDispatcher, BeanFactoryPostProcessor {
	Logger logger = LoggerFactory.getLogger(getClass());
	Map<String, SceneDef> scenes = new HashMap<String, SceneDef>(32);

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		ValidatorFactory vf = beanFactory.getBean(ValidatorFactory.class);
		Map<String, Object> actions = beanFactory.getBeansWithAnnotation(Action.class);
		for (Object action : actions.values()) {
			SceneDef scene = findScene(action.getClass().getPackage());
			if (scene != null) {
				scene.addAction(new ActionWrapper(action,vf));
			}
		}
	}

	private SceneDef findScene(Package pack) {
		String packName = pack.getName();
		packName = packName.substring(WebUtil.WEB_PKG_ROOT_LEN);
		SceneDef scene = scenes.get(packName);
		if (scene != null) {
			return scene;
		}
		String[] parts = packName.split("\\.");
		if (parts.length == 3) {
			scene = new SceneDef(parts[0], parts[1], parts[2]);
			scenes.put(packName, scene);
			return scene;
		} else {
			logger.error("Action包路径{}不符合规范{}", pack.getName(), WebUtil.WEB_PKG_ROOT + "[module].[app].[scene]");
			return null;
		}

	}

	@Override
	public ActionWrapper get(String module, String app, String scene, String action) {
		SceneDef sceneDef = scenes.get(module + "." + app + "." + scene);
		if (sceneDef != null)
			return sceneDef.getAction(action);
		return null;
	}
}
