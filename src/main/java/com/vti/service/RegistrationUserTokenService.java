package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Account;
import com.vti.entity.RegistrationUserToken;
import com.vti.repository.IRegistrationUserTokenRepository;

@Service
public class RegistrationUserTokenService implements IRegistrationUserTokenService {

	@Autowired
	private IRegistrationUserTokenRepository registrationUserTokenRepository;

	@Override
	public RegistrationUserToken getByAccount(Account account) {
		// TODO Auto-generated method stub
		return registrationUserTokenRepository.findByAccount(account);
	}

}
