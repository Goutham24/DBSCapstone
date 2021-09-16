package com.dbs.web.beans;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TradeHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tradeid;
	@OneToOne
	private Buyorder bid;
	@OneToOne
	private Sellorder sid;
	private LocalDate tradedate;
	private double tradeamount;
	public TradeHistory() {
		// TODO Auto-generated constructor stub
	}
	public int getTradeid() {
		return tradeid;
	}
	public void setTradeid(int tradeid) {
		this.tradeid = tradeid;
	}
	public Buyorder getBid() {
		return bid;
	}
	public void setBid(Buyorder bid) {
		this.bid = bid;
	}
	public Sellorder getSid() {
		return sid;
	}
	public void setSid(Sellorder sid) {
		this.sid = sid;
	}
	public LocalDate getTradedate() {
		return tradedate;
	}
	public void setTradedate(LocalDate tradedate) {
		this.tradedate = tradedate;
	}
	public double getTradeamount() {
		return tradeamount;
	}
	public void setTradeamount(double tradeamount) {
		this.tradeamount = tradeamount;
	}
	

}
