package com.cntaiping.tpi.edas.web.view.velocity.directive;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import com.cntaiping.tpi.edas.action.ActionWrapper;

public class PageEvents extends PageActionDirective {

	@Override
	public String getName() {
		return "entityEvents";
	}

	@Override
	public int getType() {
		return Directive.LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		ActionWrapper action=getActionWrapper(context);
		String[] events=action.getEntityEvents();
		String actionName=action.getActionName();
		for (int i=0;i<events.length;i++){
			writer.write(events[i]+":function(){this.page.update(ajax(\""+actionName+"/"+events[i]+"\",this.page.data));}");
			if (i>0)
				writer.write(",");
		}
		return false;
	}


}
