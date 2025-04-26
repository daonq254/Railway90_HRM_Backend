package com.vti.controller;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDto;
import com.vti.entity.Account;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForUpdating;
import com.vti.service.IAccountService;

@RestController
@RequestMapping(value = "api/v1/accounts")
@CrossOrigin("*")
public class AccountController {
	@Autowired
	private IAccountService accountService;

// Lấy danh sách Account
	@GetMapping()
	public ResponseEntity<?> getAllAccounts(Pageable pageable,
			@RequestParam(name = "search", required = false) String search) {
//		System.out.println("pageable:  " + pageable);
//		System.out.println("search: " + search);
//		Lấy danh sách Account đang lưu ở DB
		Page<Account> accountPages = accountService.getAllAccount(pageable, search);
//		Chuyển PageAcocunt ==> PageAccountDTO
		Page<AccountDto> accountDtoPage;

		accountDtoPage = accountPages.map(new Function<Account, AccountDto>() {

			@Override
			public AccountDto apply(Account account) {
				AccountDto accountDto = new AccountDto();
				accountDto.setId(account.getId());
				accountDto.setEmail(account.getEmail());
				accountDto.setFullname(account.getFullname());
				accountDto.setUsername(account.getUsername());
				accountDto.setDepartment(account.getDepartment().getName());
				accountDto.setPosition(account.getPosition().getName().toString());
				accountDto.setCreateDate(account.getCreateDate());
				return accountDto;
			}
		});
//		
//		Chuyển đổi danh sách account ==> dto
//		List<AccountDto> listAccountDtos = new ArrayList<>();
//		for (Account account : listAccounts) {
//			AccountDto accountDto = new AccountDto();
//			accountDto.setId(account.getId());
//			accountDto.setEmail(account.getEmail());
//			accountDto.setFullname(account.getFullname());
//			accountDto.setUsername(account.getUsername());
//			accountDto.setDepartment(account.getDepartment().getName());
//			accountDto.setPosition(account.getPosition().getName().toString());
//			accountDto.setCreateDate(account.getCreateDate());
//
//			listAccountDtos.add(accountDto);
//		}
		return new ResponseEntity<>(accountDtoPage, HttpStatus.OK);
	}

//	Tạo mới Account
	@PostMapping()
	public ResponseEntity<?> createAccounts(@RequestBody AccountFormForCreating accountFormForCreating) {
		System.out.println("Create Account");
		System.out.println("accountFormForCreating: " + accountFormForCreating);
		Account accountNew = accountService.createAccount(accountFormForCreating);
// Chuyển đổi về AccountDTO
		AccountDto accountDTONew = new AccountDto();
		accountDTONew.setId(accountNew.getId());
		accountDTONew.setEmail(accountNew.getEmail());
		accountDTONew.setFullname(accountNew.getFullname());
		accountDTONew.setUsername(accountNew.getUsername());
		accountDTONew.setDepartment(accountNew.getDepartment().getName());
		accountDTONew.setPosition(accountNew.getPosition().getName().toString());
		accountDTONew.setCreateDate(accountNew.getCreateDate());

		return new ResponseEntity<>(accountDTONew, HttpStatus.OK);
	}

//	Xóa Account
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteAccounts(@PathVariable(name = "id") short id) {
		System.out.println("ID: " + id);
		accountService.deleteAccount(id);
		return new ResponseEntity<>("Delete Success!!", HttpStatus.OK);
	}

//	Update Account
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateAccounts(@PathVariable(name = "id") short id,
			@RequestBody AccountFormForUpdating form) {
//		System.out.println("ID: " + id);
//		System.out.println("AccountFormForUpdating: " + form);
//		accountService.deleteAccount(id);
		accountService.updateAccount(id, form);
		return new ResponseEntity<>("Update Success!!", HttpStatus.OK);
	}

}
