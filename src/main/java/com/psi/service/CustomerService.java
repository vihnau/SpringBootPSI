package com.psi.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.model.dto.CustomerDto;
import com.psi.model.po.Customer;
import com.psi.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//新增
	public void add(CustomerDto customerDto) {
		Customer customer = modelMapper.map(customerDto, Customer.class);
		customerRepository.save(customer);
	}
	//修改
	public void update(CustomerDto CustomerDto, Long id) {
		Optional<Customer> customerOpt= customerRepository.findById(id);
		if(customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			customer.setName(CustomerDto.getName());
			customerRepository.save(customer);
		}
	}
	//刪除
	public void delete(Long id) {
		Optional<Customer> customerOpt = customerRepository.findById(id);
		if(customerOpt.isPresent()) {
			customerRepository.deleteById(id);
		}
	}
	//單筆查詢
	public CustomerDto getCustomerDtoById(Long id) {
		Optional<Customer> customerOpt = customerRepository.findById(id);
		if(customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
			return customerDto;
		}
		return null;
	}
	//查詢全部
	public List<CustomerDto> findAll(){
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDto> customerDtos = customers.stream()
				.map(customer -> modelMapper.map(customer, CustomerDto.class))
				.toList();
		return customerDtos;
	}
}
