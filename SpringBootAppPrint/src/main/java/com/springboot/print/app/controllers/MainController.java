package com.springboot.print.app.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


/*Controlador web de capa de presentación*/
@Controller
@Slf4j
public class MainController {
	/*Raíz de la página regresa index.html*/
	@GetMapping("/")
	public String redireciona() {
		log.info("*****En Index******");
		return "redirect:/home";
	}
	
	
	@GetMapping("/home")
	public String home () {
		return "home";
	}
	
	
	
	

}
