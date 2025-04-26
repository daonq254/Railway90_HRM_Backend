package com.vti.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.vti.service.IEmailService;

@Component
public class SendRegistrationUserConfirmViaEmailListener
		implements ApplicationListener<OnSendRegistrationUserConfirmViaEmailEvent> {
	@Autowired
	public IEmailService emailService;

	@Override
	public void onApplicationEvent(OnSendRegistrationUserConfirmViaEmailEvent event) {
//		Xử lý Event
		String email = event.getEmail();
//		System.out.println("Email Người nhận: " + email);
//		Nghiệp vụ gửi email xử lý ở đây
		emailService.sendRegistrationUserConfirm(email);
	}

}
