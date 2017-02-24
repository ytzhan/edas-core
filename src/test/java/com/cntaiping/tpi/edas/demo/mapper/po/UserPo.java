package com.cntaiping.tpi.edas.demo.mapper.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;


@TableName("sys_user")
public class UserPo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Integer age;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}