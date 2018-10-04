package com.proaktivio.pojo;

public class SmsValidationReport extends Report {

	private boolean valid;

	public SmsValidationReport() {
		super();
	}

	public SmsValidationReport(int code, String title, 
			String message, boolean valid) {
		super(code, title, message);
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
