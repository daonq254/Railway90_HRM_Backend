package com.vti.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.LoginDto;
import com.vti.entity.Account;
import com.vti.service.IAccountService;

@RestController
@RequestMapping(value = "api/v1/login")
@CrossOrigin("*")
public class LoginController {

	@Autowired
	private IAccountService accountService;

	@GetMapping()
	public ResponseEntity<?> login(Principal principal) {
		String usernameLoginString = principal.getName(); // usernameLoginString == Chi tiết Account
// Nếu login thành công, thì cần trả về phía giao diện 1 số thông tin: id, fullname
		Account accountLogin = accountService.getAccountByUsername(usernameLoginString);
		LoginDto accLoginDto = new LoginDto();
		accLoginDto.setId(accountLogin.getId());
		accLoginDto.setFullname(accountLogin.getFullname());
		return new ResponseEntity<>(accLoginDto, HttpStatus.OK);
	}
}
