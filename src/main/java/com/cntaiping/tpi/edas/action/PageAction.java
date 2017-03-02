package com.cntaiping.tpi.edas.action;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.cntaiping.tpi.edas.action.intf.IDefaultEntity;
import com.cntaiping.tpi.edas.action.intf.IDeleteEntity;
import com.cntaiping.tpi.edas.action.intf.IFindAllEntity;
import com.cntaiping.tpi.edas.action.intf.IFindOneEntity;
import com.cntaiping.tpi.edas.action.intf.ISaveEntity;
import com.cntaiping.tpi.edas.annotation.EntityFunction;



public abstract class PageAction{
	private boolean hasInterface(Class<?> intf){
		Class<?>[] intfs=this.getClass().getInterfaces();
		for (Class<?> _intf:intfs){
			if (_intf==intf)
				return true;
		}
		return false;
	}
	
	public Object createDefault(){
		if (hasInterface(IDefaultEntity.class)){
			IDefaultEntity action=(IDefaultEntity)this;
			return action.createEntity();
		}
		throw new RuntimeException(this.getClass().getName()+"has'nt createEntity method");
	}
	
	public int delete(String id){
		if (hasInterface(IDeleteEntity.class)){
			IDeleteEntity action=(IDeleteEntity)this;
			return action.deleteEntity(id);
		}
		throw new RuntimeException(this.getClass().getName()+"has'nt delete method");
	}
	
	public int save(Object entity){
		if (hasInterface(ISaveEntity.class)){
			ISaveEntity action=(ISaveEntity)this;
			return action.saveEntity(entity);
		}
		throw new RuntimeException(this.getClass().getName()+"has'nt save method");
	}
	
	public Object findOne(String id){
		if (hasInterface(IFindOneEntity.class)){
			IFindOneEntity action=(IFindOneEntity)this;
			return action.findOneEntity(id);
		}
		throw new RuntimeException(this.getClass().getName()+"has'nt findOne method");
	}
	
	public Object findAll(String id){
		if (hasInterface(IFindAllEntity.class)){
			IFindAllEntity action=(IFindAllEntity)this;
			return action.findAllEntity(id);
		}
		throw new RuntimeException(this.getClass().getName()+"has'nt findAll method");
	}
	
	public String[] getEntityFunctions(){
		ArrayList<String> _methods=new ArrayList<String>();
		Method[] methods=this.getClass().getMethods();
		for (Method method:methods){
			EntityFunction ef = null;
			if ((ef=method.getAnnotation(EntityFunction.class))!=null){
				_methods.add(ef.name());
			}
			
		}
		String[] result=new String[_methods.size()];
		_methods.toArray(result);
		return result;
	}
	
	public Object execute(String action,String json){
		try {
			Method method=this.getClass().getMethod(action, Object.class);
			return method.invoke(this, json);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(this.getClass().getName()+" method "+action+" execute error!");
		}
	}
}
