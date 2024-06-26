package com.psi.testing.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.psi.model.dto.DepartmentDto;
import com.psi.model.dto.EmployeeDto;
import com.psi.model.po.Department;
import com.psi.model.po.Employee;
import com.psi.repository.DepartmentRepository;
import com.psi.repository.EmployeeRepository;
import com.psi.service.DepartmentService;
import com.psi.service.EmployeeService;

@SpringBootTest
public class CreateEmployee2 {
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private EmployeeService employeeService;
	
	//模擬controller的操作
	@Test
	public void test() {
		// 劉一 陳二 張三 李四 王五 趙六 孫七 周八 吳九 鄭十
		/*
		DepartmentDto dep1 = departmentService.getDepartmentById(1L);
		EmployeeDto e1 = new EmployeeDto();
		e1.setName("劉一");
		e1.setDepartment(dep1);
		
		//儲存
		employeeService.add(e1);
		*/
		
		EmployeeDto e2 = new EmployeeDto();
		e2.setName("陳二");
		
		//儲存
		employeeService.add(e2, 2L);
		
		System.out.println("ADD OK");
	}
}
