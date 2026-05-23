package com.example.rate_limiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Test APIs", description = "Testing Rate Limiter APIs")
public class ApiController {
	
	@Operation(summary = "Test API",
	           description = "Simple API to test rate limiting")
	@GetMapping("/test")
	public String testApi() {
		return "API Successfully Fetched";
	}

}
