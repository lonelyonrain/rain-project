package com.time.scenery.rain.space.mybatis.daoentity;

/**
* @ClassName: RentEnterpriseStore
* @Description:RentEnterpriseStore数据实体类
* @author Shuzs
* @date 2017年07月31日  18时26分30秒
*/
public class RentEnterpriseStoreMap {

	/**门店名称*/
	private String enterprise_store_name;

	/**门店地址*/
	private String enterprise_store_addr;

	/**门店电话*/
	private String enterprise_store_phone;

	/**门店联系人*/
	private String enterprise_store_man;

	/**工商营业执照号*/
	private String enterprise_store_license_num;

	/**营业执照发证日期*/
	private Long enterprise_store_license_time;

	/**营业执照到期日期*/
	private Long enterprise_store_license_end_time;

	/**添加时间*/
	private String enterprise_store_add_time;

	/**所属企业名称*/
	private String enterprise;

	/**门店状态*/
	private Integer enterprise_store_status;

	/**门店所在省*/
	private String area_pro;

	/**门店所在市*/
	private String area_city;

	/**门店所在区县*/
	private String area_county;

	/**操作标识(1-新增、2-修改、3-删除)*/
	private Integer operation;
	//门店guid
	private String shop_guid;
	//所属大队
	private String police_county;
	
	public String getPolice_county() {
		return police_county;
	}

	public void setPolice_county(String police_county) {
		this.police_county = police_county;
	}

	public String getShop_guid() {
		return shop_guid;
	}

	public void setShop_guid(String shop_guid) {
		this.shop_guid = shop_guid;
	}

	/**
	* 获取门店名称
	*/
	public String getEnterprise_store_name() {
		return enterprise_store_name;
	}

	/**
	* 获取门店地址
	*/
	public String getEnterprise_store_addr() {
		return enterprise_store_addr;
	}

	/**
	* 获取门店电话
	*/
	public String getEnterprise_store_phone() {
		return enterprise_store_phone;
	}

	/**
	* 获取门店联系人
	*/
	public String getEnterprise_store_man() {
		return enterprise_store_man;
	}

	/**
	* 获取工商营业执照号
	*/
	public String getEnterprise_store_license_num() {
		return enterprise_store_license_num;
	}

	/**
	* 获取营业执照发证日期
	*/
	public Long getEnterprise_store_license_time() {
		return enterprise_store_license_time;
	}

	/**
	* 获取营业执照到期日期
	*/
	public Long getEnterprise_store_license_end_time() {
		return enterprise_store_license_end_time;
	}

	

	/**
	* 获取所属企业名称
	*/
	public String getEnterprise() {
		return enterprise;
	}

	/**
	* 获取门店状态
	*/
	public Integer getEnterprise_store_status() {
		return enterprise_store_status;
	}

	/**
	* 获取门店所在省
	*/
	public String getArea_pro() {
		return area_pro;
	}

	/**
	* 获取门店所在市
	*/
	public String getArea_city() {
		return area_city;
	}

	/**
	* 获取门店所在区县
	*/
	public String getArea_county() {
		return area_county;
	}

	/**
	* 获取操作标识(1-新增、2-修改、3-删除)
	*/
	public Integer getOperation() {
		return operation;
	}

	/**
	* 设置门店名称
	*/
	public void setEnterprise_store_name(String enterprise_store_name) {
		this.enterprise_store_name = enterprise_store_name;
	}

	/**
	* 设置门店地址
	*/
	public void setEnterprise_store_addr(String enterprise_store_addr) {
		this.enterprise_store_addr = enterprise_store_addr;
	}

	/**
	* 设置门店电话
	*/
	public void setEnterprise_store_phone(String enterprise_store_phone) {
		this.enterprise_store_phone = enterprise_store_phone;
	}

	/**
	* 设置门店联系人
	*/
	public void setEnterprise_store_man(String enterprise_store_man) {
		this.enterprise_store_man = enterprise_store_man;
	}

	/**
	* 设置工商营业执照号
	*/
	public void setEnterprise_store_license_num(String enterprise_store_license_num) {
		this.enterprise_store_license_num = enterprise_store_license_num;
	}

	/**
	* 设置营业执照发证日期
	*/
	public void setEnterprise_store_license_time(Long enterprise_store_license_time) {
		this.enterprise_store_license_time = enterprise_store_license_time;
	}

	/**
	* 设置营业执照到期日期
	*/
	public void setEnterprise_store_license_end_time(Long enterprise_store_license_end_time) {
		this.enterprise_store_license_end_time = enterprise_store_license_end_time;
	}

	
	public String getEnterprise_store_add_time() {
		return enterprise_store_add_time;
	}

	public void setEnterprise_store_add_time(String enterprise_store_add_time) {
		this.enterprise_store_add_time = enterprise_store_add_time;
	}

	/**
	* 设置所属企业名称
	*/
	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	/**
	* 设置门店状态
	*/
	public void setEnterprise_store_status(Integer enterprise_store_status) {
		this.enterprise_store_status = enterprise_store_status;
	}

	/**
	* 设置门店所在省
	*/
	public void setArea_pro(String area_pro) {
		this.area_pro = area_pro;
	}

	/**
	* 设置门店所在市
	*/
	public void setArea_city(String area_city) {
		this.area_city = area_city;
	}

	/**
	* 设置门店所在区县
	*/
	public void setArea_county(String area_county) {
		this.area_county = area_county;
	}

	/**
	* 设置操作标识(1-新增、2-修改、3-删除)
	*/
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
}

