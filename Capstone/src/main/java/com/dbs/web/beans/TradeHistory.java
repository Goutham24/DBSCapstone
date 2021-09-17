package com.dbs.web.beans;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class TradeHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tradeid;
	@OneToOne
	@JoinColumn(name="buyerorder_id")
	private BuyOrder bid;
	public TradeHistory(BuyOrder bid, SellOrder sid, LocalDate tradedate, double tradeamount) {
		super();
		this.bid = bid;
		this.sid = sid;
		this.tradedate = tradedate;
		this.tradeamount = tradeamount;
	}
	@OneToOne
	@JoinColumn(name="sellerorder_id")
	private SellOrder sid;
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
	public BuyOrder getBid() {
		return bid;
	}
	public void setBid(BuyOrder bid) {
		this.bid = bid;
	}
	public SellOrder getSid() {
		return sid;
	}
	public void setSid(SellOrder sid) {
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
