package com.sorg.mail;

public class User {
	
	private String name;
	private String mailId;
	private String event;
	private String message;
	private String companyName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", mailId=" + mailId + ", event=" + event + ", message=" + message
				+ ", companyName=" + companyName + "]";
	}

}
