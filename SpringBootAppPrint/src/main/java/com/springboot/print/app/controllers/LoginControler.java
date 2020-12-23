package com.springboot.print.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginControler {
	
	
	//Rquired false es que no siempre se le mandara el error
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required = false) String error,  Model model,Principal principal, RedirectAttributes flash) {
		
		//Si ya se logeo no es necesrio volverlo hacer para acceder a otras partes de la pagina
		if(principal !=null) {
			flash.addFlashAttribute("info", "El usuario ya ha iniciado sesion");
			return "redirect:/";
		}
		
		if(error!=null) {
			model.addAttribute("error", "El usuario o contraseña son incorrectos");
		}
		return "login";
	}
	

}
