package com.psi;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

//因為modelMapper是第三方進來的 在pom李下載後 所以要在config設定service才可以調用@autowired
@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	// 將 HTML Form(POST) + _method=PUT ->@PutMapping
	// 將 HTML Form(POST) + _method=PUT ->@DeleteMapping
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
		return filter;
	}
}
