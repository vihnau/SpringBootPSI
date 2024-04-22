package com.psi.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.psi.model.dto.EmployeeDto;
import com.psi.model.dto.EmployeePageDto;
import com.psi.model.po.Department;
import com.psi.model.po.Employee;
import com.psi.repository.DepartmentRepository;
import com.psi.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//新增 I
	public void add(EmployeeDto employeeDto) {
		//dto轉po
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		//儲存
		employeeRepository.save(employee);
	}
	
	//新增 II(換部門的感覺)
	public void add(EmployeeDto employeeDto, Long departmentId) {
		//dto轉po
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		if(employee.getDepartment()==null) {
			//根據departmentId來查找department物件
			Optional<Department> departmentOpt = departmentRepository.findById(departmentId);
			if(departmentOpt.isPresent()) {
				employee.setDepartment(departmentOpt.get());//配置指定部門
				employeeRepository.save(employee);
			}
		}
	}
	
	//修改
	public void update(EmployeeDto employeeDto, Long id) {
		//根據id查找是否有此員工
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		if(employeeOpt.isPresent()) {
			Employee employee = employeeOpt.get();//內含有id的員工資料
			employee.setName(employeeDto.getName());//更新員工姓名
			Long departmentId = employeeDto.getDepartment().getId();
			Optional<Department> departmentOpt = departmentRepository.findById(departmentId);
			if(departmentOpt.isPresent()) {
				employee.setDepartment(departmentOpt.get());//更新員工部門
				employeeRepository.save(employee);//因為有員工id的存在,所以save將自動變成更新模式
			}
		}
	}
	//刪除
	public void delete(Long id) {
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		if(employeeOpt.isPresent()) {
			employeeRepository.deleteById(id);//根據id刪除
//			employeeRepository.delete(employeeOpt.get());//根據實體紀錄刪除
		}
	}
	
	//查詢-單筆
	public EmployeeDto getEmployeeDtoById(Long id) {
		Optional<Employee> employeeOpt = employeeRepository.findById(id);
		if(employeeOpt.isPresent()) {
			Employee employee = employeeOpt.get();
			//po轉dto
			EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
			return employeeDto;
		}
		return null;
	}
	
	//多筆-分頁
	public EmployeePageDto findAllEmployees(Pageable pageable) {
		Page<Employee> empPage = employeeRepository.findAll(pageable);
		//page的po 轉 page的dto
		Page<EmployeeDto> dtoPage = empPage.map(employee->modelMapper.map(employee, EmployeeDto.class));
		return new EmployeePageDto(dtoPage);
	}
	
	//多筆-全部
	public List<EmployeeDto> findAll(){
		List<Employee> employees = employeeRepository.findAll();
		//po->dto
		return employees.stream().map(employee->modelMapper.map(employee, EmployeeDto.class)).toList();
	}
}
