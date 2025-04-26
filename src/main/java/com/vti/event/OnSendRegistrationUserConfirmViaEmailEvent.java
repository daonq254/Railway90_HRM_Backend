package com.vti.event;

import org.springframework.context.ApplicationEvent;

public class OnSendRegistrationUserConfirmViaEmailEvent extends ApplicationEvent {

	private String email;

	public OnSendRegistrationUserConfirmViaEmailEvent(String email) {
		super(email);
		this.email = email;
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
