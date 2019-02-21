package com.scraper;

public class AsnDet {

	private String name;
	private String reg;
	private String ip;
	private String asn;
	private String asnLink;
	private String country;

	public Integer getAsnNum() {
		return Integer.parseInt(asn.replace("AS", ""));
	}

	public String getAsnVal() {
		return asn.replace("AS", "");
	}

	public AsnDet() {

	}

	public AsnDet(String name, String reg, String ip, String asn, String asnLink, String country) {

		this.name = name;
		this.reg = reg;
		this.ip = ip;
		this.asn = asn;
		this.asnLink = asnLink;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAsn() {
		return asn;
	}

	public void setAsn(String asn) {
		this.asn = asn;
	}

	public String getAsnLink() {
		return asnLink;
	}

	public void setAsnLink(String asnLink) {
		this.asnLink = asnLink;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
