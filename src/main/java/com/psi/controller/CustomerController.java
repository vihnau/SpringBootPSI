package com.psi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.psi.model.dto.CustomerDto;
import com.psi.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/")
	public String index(@ModelAttribute CustomerDto customerDto, Model model) {
		List<CustomerDto> customerDtos = customerService.findAll();
		model.addAttribute("customerDtos", customerDtos);
		model.addAttribute("customerDto", customerDto);
		return "customer";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id")Long id, Model model) {
		CustomerDto customerDto = customerService.getCustomerDtoById(id);
		model.addAttribute("customerDto", customerDto);
		return "customer-edit";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id")Long id) {
		customerService.delete(id);
		return "redirect:/customer/";
	}
	
	@PostMapping("/")
	public String add(CustomerDto customerDto) {
		customerService.add(customerDto);
		return "redirect:/customer/";
	}
	@PutMapping("/{id}")
	public String update(@PathVariable("id")Long id, CustomerDto customerDto) {
		customerService.update(customerDto, id);
		return "redirect:/customer/";
	}
}
