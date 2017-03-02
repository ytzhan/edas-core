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

public class Entity extends PageActionDirective {
	@Override
	public String getName() {
		return "entity";
	}

	@Override
	public int getType() {
		return Directive.LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		ActionWrapper actionWrapper=getActionWrapper(context);
		int count=node.jjtGetNumChildren();
		if (count>0){
			String url=node.jjtGetChild(0).literal();
			writer.write("ajax(\""+actionWrapper.getActionName()+"."+url+"\")");
		}else{
			writer.write("{}");
		}
		return false;
	}


}
