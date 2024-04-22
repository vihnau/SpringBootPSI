package com.psi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
	private Long id;// 商品序號

	private String name;// 商品名稱

	private Integer cost;// 商品成本

	private Integer price;// 商品定價

	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", name=" + name + ", cost=" + cost + ", price=" + price + "]";
	}
	
}
