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
import com.cntaiping.tpi.edas.action.PageAction;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Options extends PageActionDirective {
	private ObjectMapper objectMapper=new ObjectMapper();
	@Override
	public String getName() {
		return "codeTable";
	}

	@Override
	public int getType() {
		return Directive.LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		ActionWrapper aw=getActionWrapper(context);
		PageAction pa=aw.getAction();
		String[] names=pa.getCodeDataHelper();
		if (names.length>0){
			writer.write("codeTable:{");
			for (int i=0;i<names.length;i++){
				if (i>0)
					writer.write(",");
				Object obj=pa.getCodeDataHelper(names[i]).list();
				String json=objectMapper.writeValueAsString(obj);
				writer.write(names[i]+":"+json);
			}
			writer.write("}");
		}else
			writer.write("codeTable:{}");
		return false;
	}


}
