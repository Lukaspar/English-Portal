package com.lukaspar.ep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@SpringBootApplication
public class CoreApplication {

	@GetMapping("/public")
	public String test(){
		return "public resource";
	}

	@GetMapping("/private")
	public String privateResource(){
		return "private resource";
	}

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
