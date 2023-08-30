package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class CustumerControler {
	
	@GetMapping("/index")
	public String index() {
		return "Hello Word";
	}
	
	@GetMapping("/index2")
	public String index2() {
		return "Hello Word not SECURED...";
	}
}
