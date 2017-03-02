package com.cntaiping.tpi.edas.web.view.velocity.directive;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

public class JSON extends Directive {

	@Override
	public String getName() {
		return "json";
	}

	@Override
	public int getType() {
		return Directive.BLOCK;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		int count=node.jjtGetNumChildren();
		if (count>0){
			String url=node.jjtGetChild(0).literal();
			writer.write("ajax("+url);
			String function=node.jjtGetChild(1).literal();
			writer.write(","+function);
			writer.write(")");
		}else{
			writer.write("#json:\"url not found\"");
		}
		return false;
	}


}
