package com.sys.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @Since 2010-2019
 * @Description: TODO
 * @author ***
 * @date 2019-05-15 13:51:48
 *
 */
@Entity
@Table(name = "SYS_USER")
public class SysUser {
	
	//alias
	public static final String TABLE_ALIAS = "SysUser";
	
	//columns START
	/**
	 * @Fields userid:用户ID
	 */
	private String userid;
	
	/**
	 * @Fields username:用户名称
	 */
	private String username;
	
	/**
	 * @Fields userpwd:密码
	 */
	private String userpwd;
	
	/**
	 * @Fields userstatus:状态(0未启用1启用)
	 */
	private String userstatus;
	
	/**
	 * @Fields loginlasttime:最后登录时间
	 */
	private Date loginlasttime;
	
	/**
	 * @Fields isadmin:是否主用户
	 */
	private String isadmin;
	
	/**
	 * @Fields extime:生成时间
	 */
	private Date extime;
	
	/**
	 * @Fields orgid:机构ID
	 */
	private String orgid;
	
	/**
	 * @Fields certid:证书唯一标识
	 */
	private String certid;
	
	/**
	 * @Fields mustca:必须CA登录
	 */
	private String mustca;
	
	/**
	 * @Fields iscompany:是否是企业用户（0：非企业用户）
	 */
	private String iscompany;
	
	/**
	 * @Fields departname:所属部门或科室
	 */
	private String departname;
	
	/**
	 * @Fields contactperson:联系人
	 */
	private String contactperson;
	
	/**
	 * @Fields contacttel:联系电话
	 */
	private String contacttel;
	
	/**
	 * @Fields id:用户唯一标识
	 */
	private String id;
	
	/**
	 * @Fields certsign:证书电子签章
	 */
	private String certsign;
	
	/**
	 * @Fields usertype:用户类别(1药品，2耗材，3行政)
	 */
	private BigDecimal usertype;
	
	/**
	 * @Fields adminuserid:主用户Id
	 */
	private String adminuserid;
	
	/**
	 * @Fields updateusername:维护人姓名
	 */
	private String updateusername;
	
	/**
	 * @Fields updateuserposition:维护人职务
	 */
	private String updateuserposition;
	
	/**
	 * @Fields updateuserdepartment:维护人所在部门
	 */
	private String updateuserdepartment;
	
	/**
	 * @Fields updateuserphone:维护人手机号码
	 */
	private String updateuserphone;
	
	/**
	 * @Fields updateusertel:维护人座机号码
	 */
	private String updateusertel;
	
	//columns END

	public SysUser(){
	}

	public SysUser(String userid){
		this.userid = userid;
	}

	
	public void setUserid(String userid){
		this.userid = userid;
	}
	
	public String getUserid(){
		return userid;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUserpwd(String userpwd){
		this.userpwd = userpwd;
	}
	
	public String getUserpwd(){
		return userpwd;
	}
	
	public void setUserstatus(String userstatus){
		this.userstatus = userstatus;
	}
	
	public String getUserstatus(){
		return userstatus;
	}
	
	public void setLoginlasttime(Date loginlasttime){
		this.loginlasttime = loginlasttime;
	}
	
	public Date getLoginlasttime(){
		return loginlasttime;
	}
	
	public void setIsadmin(String isadmin){
		this.isadmin = isadmin;
	}
	
	public String getIsadmin(){
		return isadmin;
	}
	
	public void setExtime(Date extime){
		this.extime = extime;
	}
	
	public Date getExtime(){
		return extime;
	}
	
	public void setOrgid(String orgid){
		this.orgid = orgid;
	}
	
	public String getOrgid(){
		return orgid;
	}
	
	public void setCertid(String certid){
		this.certid = certid;
	}
	
	public String getCertid(){
		return certid;
	}
	
	public void setMustca(String mustca){
		this.mustca = mustca;
	}
	
	public String getMustca(){
		return mustca;
	}
	
	public void setIscompany(String iscompany){
		this.iscompany = iscompany;
	}
	
	public String getIscompany(){
		return iscompany;
	}
	
	public void setDepartname(String departname){
		this.departname = departname;
	}
	
	public String getDepartname(){
		return departname;
	}
	
	public void setContactperson(String contactperson){
		this.contactperson = contactperson;
	}
	
	public String getContactperson(){
		return contactperson;
	}
	
	public void setContacttel(String contacttel){
		this.contacttel = contacttel;
	}
	
	public String getContacttel(){
		return contacttel;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setCertsign(String certsign){
		this.certsign = certsign;
	}
	
	public String getCertsign(){
		return certsign;
	}
	
	public void setUsertype(BigDecimal usertype){
		this.usertype = usertype;
	}
	
	public BigDecimal getUsertype(){
		return usertype;
	}
	
	public void setAdminuserid(String adminuserid){
		this.adminuserid = adminuserid;
	}
	
	public String getAdminuserid(){
		return adminuserid;
	}
	
	public void setUpdateusername(String updateusername){
		this.updateusername = updateusername;
	}
	
	public String getUpdateusername(){
		return updateusername;
	}
	
	public void setUpdateuserposition(String updateuserposition){
		this.updateuserposition = updateuserposition;
	}
	
	public String getUpdateuserposition(){
		return updateuserposition;
	}
	
	public void setUpdateuserdepartment(String updateuserdepartment){
		this.updateuserdepartment = updateuserdepartment;
	}
	
	public String getUpdateuserdepartment(){
		return updateuserdepartment;
	}
	
	public void setUpdateuserphone(String updateuserphone){
		this.updateuserphone = updateuserphone;
	}
	
	public String getUpdateuserphone(){
		return updateuserphone;
	}
	
	public void setUpdateusertel(String updateusertel){
		this.updateusertel = updateusertel;
	}
	
	public String getUpdateusertel(){
		return updateusertel;
	}

}