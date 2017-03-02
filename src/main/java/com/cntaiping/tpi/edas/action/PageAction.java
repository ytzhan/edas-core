package com.cntaiping.tpi.edas.action;

import com.cntaiping.tpi.edas.action.intf.IDefaultEntity;
import com.cntaiping.tpi.edas.action.intf.IDeleteEntity;
import com.cntaiping.tpi.edas.action.intf.IFindAllEntity;
import com.cntaiping.tpi.edas.action.intf.IFindOneEntity;
import com.cntaiping.tpi.edas.action.intf.ISaveEntity;



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
}
