package com.dbs.web.beans;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Custodian {

	@Id
	private String custodianid;
	private String custodianname;
	private String password;
	public Custodian() {
		// TODO Auto-generated constructor stub
	}
	public String getCustodianid() {
		return custodianid;
	}
	public void setCustodianid(String custodianid) {
		this.custodianid = custodianid;
	}
	public String getCustodianname() {
		return custodianname;
	}
	public void setCustodianname(String custodianname) {
		this.custodianname = custodianname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int hashCode() {
		return Objects.hash(custodianid, custodianname, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Custodian other = (Custodian) obj;
		return Objects.equals(custodianid, other.custodianid) && Objects.equals(custodianname, other.custodianname)
				&& Objects.equals(password, other.password);
	}


}
