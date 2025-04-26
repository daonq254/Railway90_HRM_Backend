package com.vti.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SendRegistrationUserConfirmViaEmailListener
		implements ApplicationListener<OnSendRegistrationUserConfirmViaEmailEvent> {

	@Override
	public void onApplicationEvent(OnSendRegistrationUserConfirmViaEmailEvent event) {
//		Xử lý Event
		String email = event.getEmail();
		System.out.println("Email Người nhận: " + email);
//		Nghiệp vụ gửi email xử lý ở đây
	}

}
