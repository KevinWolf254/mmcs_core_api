package com.proaktivio.pojo;

public class Report{

	private int code;
	private String title;
	private String message;
	public Report() {
		super();
	}
	public Report(int code, String title, String message) {
		super();
		this.code = code;
		this.title = title;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Report [code=" + code + ", title=" + title + ", message=" + message + "]";
	}	
}
