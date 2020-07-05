package com.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Since 2010-2016
 * @Description: 
 * @author ***
 * @date 2016-06-15 17:40:42
 *
 */
public class SysDatainterfaceLastRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//alias
	public static final String TABLE_ALIAS = "SysDatainterfaceLastRequest";
	
	//columns START
	/**
	 * @Fields id:id
	 */
	private Integer id;
	
	/**
	 * @Fields interfaceId:接口编号
	 */
	private String interfaceId;
	
	/**
	 * @Fields interfaceName:接口名称
	 */
	private String interfaceName;
	
	/**
	 * @Fields interfaceUrl:接口URL
	 */
	private String interfaceUrl;
	
	/**
	 * @Fields userType:用户类型
	 */
	private Integer userType;
	
	/**
	 * @Fields userName:请求账户
	 */
	private String userName;
	
	/**
	 * @Fields lastRequestTime:上一次访问时间
	 */
	private Date lastRequestTime;
	
	/**
	 * @Fields isUsing:是否启用
	 */
	private Integer isUsing;
	
	private Integer requestTimesByDay;//每日访问次数
	private Integer limitTimesByDay;//每日访问次数限制
	//columns END

	public SysDatainterfaceLastRequest(){
	}

	public SysDatainterfaceLastRequest(Integer id){
		this.id = id;
	}

	
	public Integer getRequestTimesByDay() {
		return requestTimesByDay;
	}

	public void setRequestTimesByDay(Integer requestTimesByDay) {
		this.requestTimesByDay = requestTimesByDay;
	}

	public Integer getLimitTimesByDay() {
		return limitTimesByDay;
	}

	public void setLimitTimesByDay(Integer limitTimesByDay) {
		this.limitTimesByDay = limitTimesByDay;
	}

	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return id;
	}
	
	public void setInterfaceId(String interfaceId){
		this.interfaceId = interfaceId;
	}
	
	public String getInterfaceId(){
		return interfaceId;
	}
	
	public void setInterfaceName(String interfaceName){
		this.interfaceName = interfaceName;
	}
	
	public String getInterfaceName(){
		return interfaceName;
	}
	
	public void setInterfaceUrl(String interfaceUrl){
		this.interfaceUrl = interfaceUrl;
	}
	
	public String getInterfaceUrl(){
		return interfaceUrl;
	}
	
	public void setUserType(Integer userType){
		this.userType = userType;
	}
	
	public Integer getUserType(){
		return userType;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setLastRequestTime(Date lastRequestTime){
		this.lastRequestTime = lastRequestTime;
	}
	
	public Date getLastRequestTime(){
		return lastRequestTime;
	}
	
	public void setIsUsing(Integer isUsing){
		this.isUsing = isUsing;
	}
	
	public Integer getIsUsing(){
		return isUsing;
	}


}