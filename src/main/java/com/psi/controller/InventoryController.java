package com.psi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.psi.repository.ProductRepository;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("inventories", productRepository.queryInventory());
		return "inventory";
	}
	
}