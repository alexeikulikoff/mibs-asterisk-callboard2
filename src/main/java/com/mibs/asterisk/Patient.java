package com.mibs.asterisk;

public class Patient  implements java.io.Serializable {

	private static final long serialVersionUID = 2L;
	private String fname;
	private String sname;
	private String lname;
	private String phone;
	public Patient(String fname, String sname, String lname, String phone) {
		super();
		this.fname = fname;
		this.sname = sname;
		this.lname = lname;
		this.phone = phone;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFname() {
		return fname;
	}
	public String getSname() {
		return sname;
	}
	public String getLname() {
		return lname;
	}
	public String getPhone() {
		return phone;
	}
	@Override
	public String toString() {
		return "Patient [fname=" + fname + ", sname=" + sname + ", lname=" + lname + ", phone=" + phone + "]";
	}
	
	
	
}
