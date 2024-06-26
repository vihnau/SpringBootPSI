package com.psi.model.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//員工序號
	
	@Column(name = "name", unique = true, nullable = false, length = 50)
	private String name;//員工姓名
	
	@JoinColumn(name = "department_id")//外鍵
	@ManyToOne(optional = false)//多位員工可以指向單一部門,一定要選(員工一定要配置一個部門)
	private Department department;
}
