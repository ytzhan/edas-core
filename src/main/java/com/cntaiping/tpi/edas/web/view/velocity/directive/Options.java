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
import com.fasterxml.jackson.databind.ObjectMapper;

public class Options extends PageActionDirective {
	private ObjectMapper objectMapper=new ObjectMapper();
	@Override
	public String getName() {
		return "options";
	}

	@Override
	public int getType() {
		return Directive.LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		int count=node.jjtGetNumChildren();
		if (count>0){
			String command=(String)node.jjtGetChild(0).value(context);
			System.out.println(command);
			ActionWrapper actionWrapper=getActionWrapper(context);
			writer.write(objectMapper.writeValueAsString(actionWrapper._execute(command, null)));
		}else
			writer.write("[]");
		return false;
	}


}
