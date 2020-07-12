package com.trade.model;

public class TradeInvoiceDis {
	
	//alias
	public static final String TABLE_ALIAS = "TradeInvoiceDis";
	
	//columns START
	/**
	 * @Fields id:id
	 */
	private String id;
	
	/**
	 * @Fields invoiceid:invoiceid
	 */
	private String invoiceid;
	
	/**
	 * @Fields disid:disid
	 */
	private String disid;
	
	//columns END

	public TradeInvoiceDis(){
	}

	public TradeInvoiceDis(String id){
		this.id = id;
	}

	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setInvoiceid(String invoiceid){
		this.invoiceid = invoiceid;
	}
	
	public String getInvoiceid(){
		return invoiceid;
	}
	
	public void setDisid(String disid){
		this.disid = disid;
	}
	
	public String getDisid(){
		return disid;
	}

}