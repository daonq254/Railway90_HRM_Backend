package com.vti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Department;
import com.vti.repository.IDepartmentRepository;

@Service
public class DepartmentService implements IDepartmentService {
	@Autowired
	private IDepartmentRepository departmentRepository;

	@Override
	public List<Department> getAllDepartments() {
//		Sử dụng Spring Data JPA
		List<Department> listDepartments = departmentRepository.findAll();

		return listDepartments;
	}

}
