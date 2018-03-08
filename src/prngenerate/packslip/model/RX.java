package prngenerate.packslip.model;

import javax.xml.bind.annotation.XmlElement;

public class RX {
	protected String rxnum=null;
	protected String descr = null;
	protected String qtyord = null;
	protected String qtyship = null;
	protected String copay = null;
	
	public String getCopay() {
		return copay;
	}
	public void setCopay(String copay) {
		this.copay = copay;
	}
	public String getRxnum() {
		return rxnum;
	}
	public void setRxnum(String rxnum) {
		this.rxnum = rxnum;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

	@XmlElement(name = "qty-ord")
	public String getQtyord() {
		return qtyord;
	}
	public void setQtyord(String qtyord) {
		this.qtyord = qtyord;
	}
	
	@XmlElement(name = "qty-ship")
	public String getQtyship() {
		return qtyship;
	}
	public void setQtyship(String qtyship) {
		this.qtyship = qtyship;
	}
}
