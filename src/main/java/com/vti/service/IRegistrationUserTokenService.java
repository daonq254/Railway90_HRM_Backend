package com.vti.service;

import com.vti.entity.Account;
import com.vti.entity.RegistrationUserToken;

public interface IRegistrationUserTokenService {

	RegistrationUserToken getByAccount(Account account);

}
