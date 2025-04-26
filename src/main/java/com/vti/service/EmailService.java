package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService implements IEmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendRegistrationUserConfirm(String email) {
		String content = "Bạn đã đăng ký thành công.";

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("system.vti.academy@gmail.com");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("Xác Nhận Đăng Ký Account");
		simpleMailMessage.setText(content);

		mailSender.send(simpleMailMessage);

	}

}
