package com.proaktivio.pojo;

import com.proaktivio.models.Inventory;

public class InventoryReport extends Report {

	private Inventory inventory;

	public InventoryReport() {
		super();
	}
	public InventoryReport(int code, String title, String message, Inventory inventory) {
		super(code, title, message);
		this.inventory = inventory;
	}
	public InventoryReport(Report report, Inventory inventory) {
		super(report.getCode(), report.getTitle(), report.getMessage());
		this.inventory = inventory;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
