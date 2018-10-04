package com.proaktivio.pojo;

import java.util.Set;

import com.proaktivio.models.CreditDisbursment;

public class Disbursement{

	private int total;
	private Set<CreditDisbursment> list;
	
	public Disbursement() {
		super();
	}
	public Disbursement(int size, Set<CreditDisbursment> list) {
		this.total = size;
		this.list = list;
	}
	public int getSize() {
		return total;
	}
	public void setSize(int size) {
		this.total = size;
	}
	public Set<CreditDisbursment> getList() {
		return list;
	}
	public void setList(Set<CreditDisbursment> list) {
		this.list = list;
	}
}
