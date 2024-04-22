package com.psi.testing.create;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.psi.model.po.Department;
import com.psi.model.po.Employee;
import com.psi.repository.DepartmentRepository;
import com.psi.repository.EmployeeRepository;

@SpringBootTest
public class CreateEmployee {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	public void test() {
		Employee e1 = new Employee();
		e1.setName("張三");
		Employee e2 = new Employee();
		e2.setName("李四");
		Employee e3 = new Employee();
		e3.setName("王五");
		Employee e4 = new Employee();
		e4.setName("趙六");
		// 劉一 陳二 張三 李四 王五 趙六 孫七 周八 吳九 鄭十
		
		// 配置與部門的關聯
		Department dep1 = departmentRepository.findById(1L).get();
		Department dep2 = departmentRepository.findById(2L).get();
		Department dep3 = departmentRepository.findById(3L).get();//因為Long型別byid 給定id的型別Long
		
		e1.setDepartment(dep1);//儲存entityEmployee 員工部門 department關聯entityDepartment的Set<Employee>集合
		e2.setDepartment(dep2);
		e3.setDepartment(dep2);
		e4.setDepartment(dep3);
		
		// 儲存
		employeeRepository.save(e1);
		employeeRepository.save(e2);
		employeeRepository.save(e3);
		employeeRepository.save(e4);
		
		System.out.println("SAVE OK");
	}
}
