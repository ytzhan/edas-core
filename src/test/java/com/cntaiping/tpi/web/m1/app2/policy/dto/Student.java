package com.cntaiping.tpi.web.m1.app2.policy.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Student {
	private String name;
	private String field1;
	private boolean field2;
	private Date startDate;
	private String search;
	private String vcode;
	private String direction;
	private String email;
	private String chose1;
	private String chose2;
	private String card;
	private String citys;
	private String area;
	private String radio;
	private List<String> ch=new ArrayList<String>();
	private List<Detail> detail=new ArrayList<Detail>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public boolean isField2() {
		return field2;
	}
	public void setField2(boolean field2) {
		this.field2 = field2;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getChose1() {
		return chose1;
	}
	public void setChose1(String chose1) {
		this.chose1 = chose1;
	}
	public String getChose2() {
		return chose2;
	}
	public void setChose2(String chose2) {
		this.chose2 = chose2;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getCitys() {
		return citys;
	}
	public void setCitys(String citys) {
		this.citys = citys;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public List<String> getCh() {
		return ch;
	}
	public void setCh(List<String> ch) {
		this.ch = ch;
	}
	public List<Detail> getDetail() {
		return detail;
	}
	public void setDetail(List<Detail> detail) {
		this.detail = detail;
	}
}
