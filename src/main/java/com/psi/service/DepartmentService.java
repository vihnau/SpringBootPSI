package com.psi.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psi.model.dto.DepartmentDto;
import com.psi.model.dto.DepartmentPageDto;
import com.psi.model.po.Department;
import com.psi.repository.DepartmentRepository;


@Service
public class DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired//必須要在 com/psi/ModelMapperConfig.java中配置
	private ModelMapper modelMapper;
	
	//这段代码实现了从数据库中查询部门数据，并将查询结果转换为 DTO（数据传输对象）对象，
	//并进行分页处理，最终返回给调用者。
	/**
	 * 它用於處理與部門相關的業務邏輯。主要功能是從 DepartmentRepository 獲取分頁的部門數據，然後將這些數據轉換為 DTO（數據傳輸對象）。
	 * 這種轉換過程有助於將數據層和表示層分離，提高了代碼的可維護性和靈活性。轉換後的 DTO 數據被包裝在 DepartmentPageDTO 中，
	 * 這個對象既包含了分頁資訊，也包含了部門數據的列表，方便前端展示和處理。
	 * */
	// 全部查詢分頁版
	public DepartmentPageDto findAllDepartments(Pageable pageable) {
		Page<Department> deptPage = departmentRepository.findAll(pageable);// 從資料庫獲取分頁的部門數據
//		Page<DepartmentDto> dtoPage = deptPage.map(this::convertToDTO);// 將部門實體數據轉換為 DTO
		Page<DepartmentDto> dtoPage = deptPage.map(department->modelMapper.map(department, DepartmentDto.class));// 將部門實體數據轉換為 DTO
		return new DepartmentPageDto(dtoPage);// 返回包含 DTO 的分頁對象
	}
	
	// 全部查詢不分頁板
	public List<DepartmentDto> findAll(){
		//從資料庫取得所有部門資訊
		List<Department> departments = departmentRepository.findAll();
		//將po轉dto後送出
		return departments.stream().map(department->modelMapper.map(department, DepartmentDto.class)).toList();
	}
	
	// 新增
	@Transactional
	public void add(DepartmentDto departmentDto) {
		//將dto轉po
		Department department = modelMapper.map(departmentDto, Department.class);
		departmentRepository.save(department);
	}
	
	// 修改
	@Transactional
	public void update(DepartmentDto departmentDto,Long id) {
		//根據id 找到要修改的 po
		Optional<Department> departmentOpt= departmentRepository.findById(id);
		//確認是否有找到紀錄
		if(departmentOpt.isPresent()) {
			Department department = departmentOpt.get();
			//將dto的資料入到po 
			department.setName(departmentDto.getName());
			//儲存
			departmentRepository.save(department);
		}
	}
	
	// 刪除
	@Transactional
	public void delete(Long id) {
		// 根據id 找到要刪除的id
		Optional<Department> departmentOpt = departmentRepository.findById(id);
		//確認是否有找到紀錄
		if(departmentOpt.isPresent()) {
			//刪除
			departmentRepository.deleteById(id);;
		}
	}
	
	// 單筆查詢
	public DepartmentDto getDepartmentDtoById(Long id) {
		//根據id找到要查詢的po
		Optional<Department> departmentOpt = departmentRepository.findById(id);
		if(departmentOpt.isPresent()) {
			Department department = departmentOpt.get();
			//po轉dto
			DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);
			return departmentDto;
		}
		return null;
	}
	
	/**
	 * 將部門實體數據轉換為 DTO
	 * */
//	private DepartmentDTO convertToDto(Department department) {
//		return modelMapper.map(department, DepartmentDTO.class);
//	}
	/*
	private DepartmentDto convertToDto(Department department) {
		DepartmentDto dto = new DepartmentDto();// 創建一個新的 DepartmentDto 物件
		dto.setId(department.getId());// 實體數據 department 的 id 配置給 dto 的 id
		dto.setName(department.getName());// 實體數據 department 的 name 配置給 dto 的 name
		return dto;
	}
	*/
	
}
