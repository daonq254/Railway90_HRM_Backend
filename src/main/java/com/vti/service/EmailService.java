package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.vti.entity.Account;
import com.vti.entity.RegistrationUserToken;

@Component
public class EmailService implements IEmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IRegistrationUserTokenService registrationUserTokenService;

	@Override
	public void sendRegistrationUserConfirm(String email) {

// Tìm ra token của tài khoản đăng ký

//		Email  ==> Account  ==> Token tương ứng
		Account account = accountService.getAccountByEmail(email);

//		 Account  ==> Token tương ứng
		RegistrationUserToken registrationUserToken = registrationUserTokenService.getByAccount(account);

//		Tìm ra token của Account vừa đăng ký
		String token = registrationUserToken.getToken();
//		Tạo đường link gửi về cho người dùng

		String content = "Bạn đã đăng ký thành công. Hãy click vào đường link bên dưới để kích hoạt tài khoản: "
				+ "http://localhost:8080/api/v1/accountsRegister/activeUser?token=" + token;

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("system.vti.academy@gmail.com");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("Xác Nhận Đăng Ký Account");
		simpleMailMessage.setText(content);

		mailSender.send(simpleMailMessage);

	}

}
