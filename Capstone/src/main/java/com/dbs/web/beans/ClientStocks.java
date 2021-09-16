package com.dbs.web.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClientStocks {

	@Id
	private Client clientid;
	@Id
	private Instrument instrumentid;
	private int quantity;
	public ClientStocks() {
		// TODO Auto-generated constructor stub
	}

}
