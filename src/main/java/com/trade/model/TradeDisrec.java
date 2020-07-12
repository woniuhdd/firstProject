package com.trade.model;

import java.math.BigDecimal;
import java.util.Date;

public class TradeDisrec {
	
	//alias
	public static final String TABLE_ALIAS = "配送与收货表(未完成)";
	
	//columns START
	/**
	 * @Fields disrecid:配送与收货ID
	 */
	private String disrecid;
	
	/**
	 * @Fields purchaseorderdetailid:订单明细ID
	 */
	private String purchaseorderdetailid;
	
	/**
	 * @Fields discount:配送数量
	 */
	private Long discount;
	
	/**
	 * @Fields disamount:配送金额
	 */
	private BigDecimal disamount;
	
	/**
	 * @Fields distime:配送时间
	 */
	private Date distime;
	
	/**
	 * @Fields reccount:入库数量
	 */
	private Long reccount;
	
	/**
	 * @Fields recamount:入库金额
	 */
	private BigDecimal recamount;
	
	/**
	 * @Fields rectime:入库时间
	 */
	private Date rectime;
	
	/**
	 * @Fields batch:批号
	 */
	private String batch;
	
	/**
	 * @Fields disrecstatus:状态 (0已配送1已入库2未到货3拒收)
	 */
	private String disrecstatus;
	
	/**
	 * @Fields rereson:拒收原因
	 */
	private String rereson;
	
	/**
	 * @Fields eduptime:最后更新时间
	 */
	private Date eduptime;
	
	/**
	 * @Fields discheckscoreid:配送考核评分表唯一标识
	 */
	private String discheckscoreid;
	
	/**
	 * @Fields differencereason:差异入库原因
	 */
	private Object differencereason;
	
	/**
	 * @Fields remnantinventory:剩余库存
	 */
	private Long remnantinventory;
	
	/**
	 * @Fields returnstatus:退货状态0未在退货流程1退货流程中
	 */
	private Boolean returnstatus;
	
	/**
	 * @Fields invoiceserialid:发票表主键
	 */
	private String invoiceserialid;
	
	/**
	 * @Fields invoiceadddate:发票明细添加时间
	 */
	private Date invoiceadddate;
	
	/**
	 * @Fields flag:flag
	 */
	private String flag;
	
	/**
	 * @Fields flaglog:flaglog
	 */
	private String flaglog;
	
	/**
	 * @Fields flagtime:flagtime
	 */
	private Date flagtime;
	
	//columns END

	public TradeDisrec(){
	}

	public TradeDisrec(String disrecid){
		this.disrecid = disrecid;
	}

	
	public void setDisrecid(String disrecid){
		this.disrecid = disrecid;
	}
	
	public String getDisrecid(){
		return disrecid;
	}
	
	public void setPurchaseorderdetailid(String purchaseorderdetailid){
		this.purchaseorderdetailid = purchaseorderdetailid;
	}
	
	public String getPurchaseorderdetailid(){
		return purchaseorderdetailid;
	}
	
	public void setDiscount(Long discount){
		this.discount = discount;
	}
	
	public Long getDiscount(){
		return discount;
	}
	
	public void setDisamount(BigDecimal disamount){
		this.disamount = disamount;
	}
	
	public BigDecimal getDisamount(){
		return disamount;
	}
	
	public void setDistime(Date distime){
		this.distime = distime;
	}
	
	public Date getDistime(){
		return distime;
	}
	
	public void setReccount(Long reccount){
		this.reccount = reccount;
	}
	
	public Long getReccount(){
		return reccount;
	}
	
	public void setRecamount(BigDecimal recamount){
		this.recamount = recamount;
	}
	
	public BigDecimal getRecamount(){
		return recamount;
	}
	
	public void setRectime(Date rectime){
		this.rectime = rectime;
	}
	
	public Date getRectime(){
		return rectime;
	}
	
	public void setBatch(String batch){
		this.batch = batch;
	}
	
	public String getBatch(){
		return batch;
	}
	
	public void setDisrecstatus(String disrecstatus){
		this.disrecstatus = disrecstatus;
	}
	
	public String getDisrecstatus(){
		return disrecstatus;
	}
	
	public void setRereson(String rereson){
		this.rereson = rereson;
	}
	
	public String getRereson(){
		return rereson;
	}
	
	public void setEduptime(Date eduptime){
		this.eduptime = eduptime;
	}
	
	public Date getEduptime(){
		return eduptime;
	}
	
	public void setDischeckscoreid(String discheckscoreid){
		this.discheckscoreid = discheckscoreid;
	}
	
	public String getDischeckscoreid(){
		return discheckscoreid;
	}
	
	public void setDifferencereason(Object differencereason){
		this.differencereason = differencereason;
	}
	
	public Object getDifferencereason(){
		return differencereason;
	}
	
	public void setRemnantinventory(Long remnantinventory){
		this.remnantinventory = remnantinventory;
	}
	
	public Long getRemnantinventory(){
		return remnantinventory;
	}
	
	public void setReturnstatus(Boolean returnstatus){
		this.returnstatus = returnstatus;
	}
	
	public Boolean getReturnstatus(){
		return returnstatus;
	}
	
	public void setInvoiceserialid(String invoiceserialid){
		this.invoiceserialid = invoiceserialid;
	}
	
	public String getInvoiceserialid(){
		return invoiceserialid;
	}
	
	public void setInvoiceadddate(Date invoiceadddate){
		this.invoiceadddate = invoiceadddate;
	}
	
	public Date getInvoiceadddate(){
		return invoiceadddate;
	}
	
	public void setFlag(String flag){
		this.flag = flag;
	}
	
	public String getFlag(){
		return flag;
	}
	
	public void setFlaglog(String flaglog){
		this.flaglog = flaglog;
	}
	
	public String getFlaglog(){
		return flaglog;
	}
	
	public void setFlagtime(Date flagtime){
		this.flagtime = flagtime;
	}
	
	public Date getFlagtime(){
		return flagtime;
	}

}