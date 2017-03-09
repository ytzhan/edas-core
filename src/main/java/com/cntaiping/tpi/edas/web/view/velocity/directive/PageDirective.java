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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PageDirective extends PageActionDirective {
	private ObjectMapper objectMapper=new ObjectMapper();
	@Override
	public String getName() {
		return "Page";
	}

	@Override
	public int getType() {
		return Directive.BLOCK;
	}

	private String buildEntity(InternalContextAdapter context){
		ActionWrapper aw=this.getActionWrapper(context);
		String page=aw.getActionName();
		StringBuffer sb=new StringBuffer();
		String[] names=aw.getEntityMethods();
		sb.append("entity:{\n");
		for (int i=0;i<names.length;i++){
			if (i>0)
				sb.append(",\n");
			sb.append("\t");
			sb.append(names[i]).append(":remote(\"").append(page).append("/").append(names[i])
				.append("\")");
		}
		
		if (aw.getDefaultEntityMethod()!=null){
			if (names.length>0){
				sb.append(",\n");
			}
			sb.append("\t");
			String defaultName=aw.getDefaultEntityMethodName();
			sb.append(defaultName).append(":remote(\"").append(page).append("/").append(defaultName)
				.append("\")");
		}
		sb.append("\n}");
		return sb.toString();
	}
	
	private String buildRemote(InternalContextAdapter context){
		ActionWrapper aw=this.getActionWrapper(context);
		String page=aw.getActionName();
		StringBuffer sb=new StringBuffer();
		String[] names=aw.getRemoteMethods();
		sb.append("rpcs:{\n");
		for (int i=0;i<names.length;i++){
			if (i>0)
				sb.append(",\n");
			sb.append("\t");
			sb.append(names[i]).append(":remote(\"").append(page).append("/").append(names[i])
				.append("\")");
		}
		sb.append("\n}");
		return sb.toString();
	}
	
	private String buildCodeTable(InternalContextAdapter context) throws JsonProcessingException{
		ActionWrapper aw=this.getActionWrapper(context);
		StringBuffer sb=new StringBuffer();
		PageAction pa=(PageAction)aw.getAction();
		String[] names=pa.getCodeDataHelper();
		sb.append("codeTable:{\n");
		Object obj;
		for (int i=0;i<names.length;i++){
			if (i>0)
				sb.append(",\n");
			sb.append("\t");
			obj=pa.getCodeDataHelper(names[i]).list();
			sb.append(names[i]).append(":").append(objectMapper.writeValueAsString(obj));
		}
		sb.append("\n}");
		return sb.toString();
	}
	
	private String buildEntityEvents(InternalContextAdapter context){
		ActionWrapper aw=this.getActionWrapper(context);
		String page=aw.getActionName();
		StringBuffer sb=new StringBuffer();
		String[] names=aw.getEntityEvents();
		for (int i=0;i<names.length;i++){
			if (i>0)
				sb.append(",\n");
			sb.append(names[i]).append(":").append("function(){\n\tthis.page.update(remote(\"").append(page)
				.append("/").append(names[i]).append("\").post(this.page.data));\n}");
		}
		return sb.toString();
	}
	
	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		StringBuffer sb=new StringBuffer();
		sb.append("var _options={\n");
		sb.append(buildEntity(context));
		sb.append(",\n");
		sb.append(buildRemote(context));
		sb.append(",\n");
		sb.append(buildCodeTable(context));
		String s=buildEntityEvents(context);
		if (s.length()>0)
			sb.append(",\n");
		sb.append(s);
		sb.append("\n}");
		//body
		String body=null;
		if (node.jjtGetNumChildren()>0){
			body=(String)node.jjtGetChild(0).literal();
			body=body.trim();
		}
		if (body==null){
			body="var _extendOptions={}";
		}else{
			body="var _extendOptions="+body;
		}
		
		writer.write(sb.toString());
		writer.write(";\n");
		writer.write(body);
		writer.write(";\n");
		writer.write("Page(can.extend(_options,_extendOptions));");
		return false;
	}

}
