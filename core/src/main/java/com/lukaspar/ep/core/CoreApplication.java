package com.lukaspar.ep.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@SpringBootApplication
public class CoreApplication {

	@GetMapping("/public")
	public String publicResource(){
		return "public resource:";
	}

	@GetMapping("/private")
	public String privateResource(@AuthenticationPrincipal UserDetails userPrincipal){
		return "private resource: " + userPrincipal.getUsername();
	}

	@GetMapping("/admin")
	public String admin(){
		return "private admin";
	}

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
