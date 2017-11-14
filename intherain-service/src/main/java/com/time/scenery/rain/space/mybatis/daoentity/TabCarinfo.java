package com.time.scenery.rain.space.mybatis.daoentity;

public class TabCarinfo {
	private String car_id;
	private String car_no;
	private String enterprise_store_name;
	private String police_county;
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPolice_county() {
		return police_county;
	}
	public void setPolice_county(String police_county) {
		this.police_county = police_county;
	}
	public String getEnterprise_store_name() {
		return enterprise_store_name;
	}
	public void setEnterprise_store_name(String enterprise_store_name) {
		this.enterprise_store_name = enterprise_store_name;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
}
