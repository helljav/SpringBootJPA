package com.springboot.print.app.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.print.app.dao.IRoleDao;
import com.springboot.print.app.models.entity.Cliente;
import com.springboot.print.app.models.entity.Role;
import com.springboot.print.app.service.IClienteService;
import com.springboot.print.app.service.IRoleService;
import com.springboot.print.app.service.UploadFileServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ClienteController {
	@Autowired
	private UploadFileServiceImpl uploadFileService;

	@Autowired
	private IClienteService clienteService;
	
	
	@Autowired
	private IRoleService roleService ;
	
	@Autowired
	private MessageSource messageSource;
	
	//Con esto encriptamos las  contraseñas
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	// Este objeto va a ser el que se va a mandar por el form añadido a travez del
		// modelo
		@RequestMapping(value = "/nuevoUsuario")
		public String crear(Map<String, Object> model , Locale locale) {
			Cliente cliente = new Cliente();
			model.put("cliente", cliente);
			model.put("titulo", messageSource.getMessage("text.cliente.form.titulo.crear", null, locale));
			log.info(cliente.toString());
			return "newUser";
		}
		
		
		
		@RequestMapping(value = "/nuevoUsuario", method = RequestMethod.POST)
		public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
				@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status, Locale locale) {
			
			
			if (result.hasErrors()) {
				model.addAttribute("titulo", messageSource.getMessage("text.cliente.form.titulo", null, locale));
				log.info(result.getAllErrors().toString());
				return "newUser";
			}
			
			//SI EXISTE UNA FOTO ANTERIOR LA BORRAMOS
			if (!foto.isEmpty()) {

				if (cliente.getId() != null && cliente.getId() > 0 && cliente.getPhoto() != null
						&& cliente.getPhoto().length() > 0) {
					uploadFileService.delete(cliente.getPhoto());
				}
				String uniqueFilename = null;
				try {
					//GUARDAMOS LA NUEVA FOTO
					uniqueFilename = uploadFileService.copy(foto);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cliente.setPhoto(uniqueFilename);
			}
			String mensajeFalsh = (cliente.getId() != null) ? "Cliente editado con exito!" : "Cliente creado con exito!";
			
			if(cliente.getId() == null) {
				//ENCRIPTAMOS LA CONTRASEÑA
				cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
				
				//Le damos el rol de usuario
				Role role = new Role();
				role.setAuthority("ROLE_USER");
				
				List<Role> roles = new ArrayList();
				roles.add(role);
				
				cliente.setRoles(roles);
				
				//Lo habilitamos
				cliente.setEnabled(true);
				
			}
						
			
			//GUARDAMOS AL NUEVO CLIENTE	
			try {
				clienteService.save(cliente);
				status.setComplete();
				flash.addFlashAttribute("success", mensajeFalsh);

				return "redirect:/login";
				
			} catch (Exception e) {
				
				//SI NO SE PUEDE GUARDAR ENVIAMOS UN MENSAJE DE ERROR
				model.addAttribute("error", "No se pudo crear la cuenta. El correo o nombre de usuario ya existen");
				if(cliente.getPhoto()!=null) {
					uploadFileService.delete(cliente.getPhoto());
				}				
				log.info(result.getAllErrors().toString());
				return "newUser";
			}
		
			

		}
	


}
