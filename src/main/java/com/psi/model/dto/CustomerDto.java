package com.psi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
	private Long id;
	
	private String name;

	@Override
	public String toString() {
		return "CustomerDto [id=" + id + ", name=" + name + "]";
	}
	
}
