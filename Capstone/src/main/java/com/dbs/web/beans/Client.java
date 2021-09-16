package com.dbs.web.beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Client {

	@Id
	private String clientid;
	private String clientname;
	@OneToOne
	private Custodian custodianid;
	private double transactionlimit;
	
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public Custodian getCustodianid() {
		return custodianid;
	}
	public void setCustodianid(Custodian custodianid) {
		this.custodianid = custodianid;
	}
	public double getTransactionlimit() {
		return transactionlimit;
	}
	public void setTransactionlimit(double transactionlimit) {
		this.transactionlimit = transactionlimit;
	}
	public Client() {
		// TODO Auto-generated constructor stub
	}

}
