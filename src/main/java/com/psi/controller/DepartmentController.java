package com.psi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psi.model.dto.DepartmentDto;
import com.psi.model.dto.DepartmentPageDto;
import com.psi.service.DepartmentService;

@Controller
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
//	/*
//	@GetMapping("/")//分頁版
////	@ResponseBody
//	// url = localhost:8080/psi/department/?page=0&size=0
//	// page = 頁數&size=每頁筆數
//	// 例如: page=0&size=5 第1頁每頁5筆
//	// 例如: page=1&size=4 第2頁每頁4筆
//	// 例如: page=2&size=3 第3頁每頁3筆
//	public String index(@RequestParam(defaultValue = "0") int page,
//						@RequestParam(defaultValue = "10") int size,
//						Model model, @ModelAttribute DepartmentDto departmentDto) {
//		Pageable pageable = PageRequest.of(page, size);
//		DepartmentPageDto departmentPageDto = departmentService.findAllDepartments(pageable);// 得到該分頁的數據實體
//		model.addAttribute("departmentPageDTO",departmentPageDto);
//		return "department";
////		return departmentPageDTO.getDepartments().size()+"";
//	}
//	*/
	
	@GetMapping("/")//不分頁版
//	@ResponseBody
	public String index(@ModelAttribute DepartmentDto departmentDto ,Model model) {
		List<DepartmentDto> departmentDtos = departmentService.findAll();
		model.addAttribute("departmentDtos", departmentDtos);
		model.addAttribute("departmentDto", departmentDto);
		return "department";
	}
	
	//取得單筆
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id")Long id, Model model) {
		DepartmentDto departmentDto = departmentService.getDepartmentDtoById(id);
		model.addAttribute("departmentDto",departmentDto);
		return "department-edit";
	}
	
	//新增
	@PostMapping("/")
	public String add(DepartmentDto departmentDto) {
		departmentService.add(departmentDto);
		//新增完畢後重導到首頁
		return "redirect:/department/";
	}
	
	//修改
	@PutMapping("/{id}")
	public String update(DepartmentDto departmentDto, @PathVariable("id") Long id) {
		departmentService.update(departmentDto, id);
		//修改完畢後重導道首頁
		return "redirect:/department/";
	}
	
	//刪除
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id")Long id) {
		departmentService.delete(id);
		//刪除完畢後重導到首頁
		return "redirect:/department/";
	}
}
