package com.vti.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;
import com.vti.entity.RegistrationUserToken;
import com.vti.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForCreatingRegister;
import com.vti.form.AccountFormForUpdating;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.repository.IPositionRepository;
import com.vti.repository.IRegistrationUserTokenRepository;
import com.vti.specification.AccountSpecification;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private IDepartmentRepository departmentRepository;

	@Autowired
	private IPositionRepository positionRepository;

//	Dùng để mã hóa Password
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IRegistrationUserTokenRepository registrationUserTokenRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Override
	public Page<Account> getAllAccount(Pageable pageable, String search) {
//		search
		Specification<Account> where = null;
		if (!StringUtils.isEmpty(search)) {
			AccountSpecification emailSpecification = new AccountSpecification("email", "like", search);
			AccountSpecification fullnameSpecification = new AccountSpecification("fullname", "like", search);
			AccountSpecification departmentSpecification = new AccountSpecification("department.name", "like", search);
			where = Specification.where(emailSpecification).or(fullnameSpecification).or(departmentSpecification);
		}

//		
		return accountRepository.findAll(where, pageable);
	}

	@Override
	public Account createAccount(AccountFormForCreating accountFormForCreating) {
//		Cần Account
		Account account = new Account();
		account.setEmail(accountFormForCreating.getEmail());
		account.setFullname(accountFormForCreating.getFullname());
		account.setUsername(accountFormForCreating.getUsername());
//		Department departmentId  ==> Tìm Department tương ứng
		Department department = departmentRepository.getById(accountFormForCreating.getDepartmentId());
//		Position
		Position position = positionRepository.getById(accountFormForCreating.getPositionId());

		account.setDepartment(department);
		account.setPosition(position);
//		
		Account accountNew = accountRepository.save(account);
		return accountNew;

	}

	@Override
	public void deleteAccount(short id) {
		accountRepository.deleteById(id);

	}

	@Override
	public void updateAccount(short id, AccountFormForUpdating form) {
		// Tìm ra Account cần update
		Account account = accountRepository.getById(id);
//		Cập nhật thông tin mới cho Account này
		account.setFullname(form.getFullname());

		Department department = departmentRepository.getById(form.getDepartmentId());
		account.setDepartment(department);

		Position position = positionRepository.getById(form.getPositionId());
		account.setPosition(position);
//		Lưu lại Account vào DB
		accountRepository.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList("user"));

	}

	@Override
	public Account getAccountByUsername(String usernameLoginString) {

		return accountRepository.findByUsername(usernameLoginString);
	}

	@Override
	public void createAccountRegister(AccountFormForCreatingRegister form) {
//		Lưu dữ liệu Acocunt vào DB
		Account account = new Account();
		Department department = departmentRepository.getById(form.getDepartmentId());
		Position position = positionRepository.getById(form.getPositionId());
		account.setEmail(form.getEmail());
		account.setUsername(form.getUsername());
		account.setFullname(form.getFullname());
		account.setDepartment(department);
		account.setPosition(position);
		account.setPassword(passwordEncoder.encode(form.getPassword())); // 123456

		accountRepository.save(account);

//		Tạo Token và lưu trữ token vào DB
		String token = UUID.randomUUID().toString();
		// Tạo ra 1 chuỗi ngẫu nhiên, gồm 36 ký tự, trong đó có 4 ký tự gạch ngang, và
		// chuỗi này đảm bảo không trùng lặp trên hệ thống
		RegistrationUserToken registrationUserToken = new RegistrationUserToken(token, account);
		registrationUserTokenRepository.save(registrationUserToken);

//		Gửi thông tin xác nhận qua Email: dao.nq254@gmail.com
		eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(account.getEmail()));

	}

}
