package com.springboot.print.app.controllers;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.print.app.dao.IClienteDao;
import com.springboot.print.app.models.entity.Cliente;
import com.springboot.print.app.models.entity.Pedido;
import com.springboot.print.app.service.IClienteService;
import com.springboot.print.app.service.IDocumenstServiceImpl;
import com.springboot.print.app.service.IPedidoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PedidoController {
	
	@Autowired
	private ClienteController clienteController;
	
	@Autowired
	private IDocumenstServiceImpl documentService;
	
	@Autowired
	private IPedidoService pedidoService;

	
	
	
	
	@Secured("ROLE_USER")
	@RequestMapping(path="/imprimir")
	public String prueba(Map<String, Object> model) {
		Pedido pedido = new Pedido();
		model.put("pedido", pedido);
		return "pedido";
	}
	
	
	@Secured("ROLE_USER")
	@PostMapping(path="/crearPedido")
	public String crearPedido(@Valid Pedido pedido, BindingResult result, Model model,
			@RequestParam("archivo") MultipartFile documento,
			@RequestParam(name="dato", required = false) String pago,
			RedirectAttributes flash, SessionStatus status,
			Authentication auth) {
		
		log.info(pedido.toString());
		
		 if(documento.isEmpty() || pedido.getPrecioTotal() == 0 || pedido.getPrecioTotal() == null) {
			model.addAttribute("error", "Lo siento!!!. No se pudo leer el archivo");
			return "pedido";
		}
		 else {
			 if(pedido.getMetodoPago().equals("tarjeta")){
				 if(!pago.equals("COMPLETED")) {
					 model.addAttribute("error", "Lo siento!!!. No se pudo cargar el pedido en la base de datos, contactese con el encargado");
					 return "pedido";
				 }
				 
			 }
			 
			 log.info(pago);
			 String uniqueFilename = null;
			 //Guardamos el documento
			 try {
				uniqueFilename = documentService.copy(documento);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//Guardamos la direccion del archivo
			pedido.setArchivo(uniqueFilename);
			
			//Registramos el cliente en el pedido
			pedido.setCliente(clienteController.mostrarUsuarioByUsername(auth.getName()));
			
			//Guaradamos el pedido
			pedidoService.save(pedido);
			
			model.addAttribute("success", "Impresión solicitada!!");
		 }
		
		return "redirect:/imprimir";
	}

}
