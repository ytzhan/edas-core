package com.cntaiping.tpi.edas.web.view.velocity.directive;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.directive.Directive;

import com.cntaiping.tpi.edas.action.ActionWrapper;
import com.cntaiping.tpi.edas.util.WebUtil;

public abstract class PageActionDirective extends Directive {
	protected ActionWrapper getActionWrapper(InternalContextAdapter context){
		return (ActionWrapper) context.get("_pageObject");
	}
}
