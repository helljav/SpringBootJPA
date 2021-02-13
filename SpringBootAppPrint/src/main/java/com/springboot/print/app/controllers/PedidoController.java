package com.springboot.print.app.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.print.app.paginator.PageRender;
import com.springboot.print.app.models.entity.Pedido;
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
	
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	
	
	
	
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
			log.info("Ruta?"+ documentService.getPath(pedido.getArchivo()).toString());
			
			flash.addAttribute("success", "Impresión solicitada!!");
			
		 }
		
		return "redirect:/imprimir";
	}
	
	
	
	@Secured("ROLE_ADMIN")
	@GetMapping(path = "/listarPedidosPage")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {
		
		
		if(authentication!=null) {
			logger.info("Hola usuario autenticado, tu user name es:".concat(authentication.getName()));
		}
		
		//Obtenemos la utenticacion del usuario
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(hasRole("ROLE_ADMIN")) {
			log.info("Hola".concat(auth.getName()).concat(" Tienes acceso"));
		}
		else {
			log.info("Hola".concat(auth.getName()).concat(" No tienes acceso"));
		}
		
		
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Pedido> pedidos = pedidoService.findAll(pageRequest);   
		
		PageRender<Pedido> pageRender = new PageRender<Pedido>("/listarPedidosPage", pedidos);
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("page", pageRender);
		model.addAttribute("nombreUsuario", auth.getName());
		return "pedidoAdmin";
	}
	
	
	
	
	@GetMapping("/descargaPDF/{idPedido}")
	public void handleRequest(HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("idPedido") Long idPedido) throws Exception {
		
		
		Pedido pedido = pedidoService.findOne(idPedido);
		//Validando si pedido existe en BD
		if(pedido != null) {
			
			try {
				

	            response.setContentType("application/pdf");
	            
	            //Facilitando la descarga asignando el nombre del archivo a descargar
	            response.setHeader("Content-Disposition", "attachment; filename=\""+ pedido.getArchivo()+ "\"");
	            
	            //Leyendo datos de archivo de la ruta donde se encuentra almacenada
	            InputStream archivo = new FileInputStream(documentService.getPath(pedido.getArchivo()).toString());
	            
	            //Extrae los bytes del archivo y guardando en response 
	            IOUtils.copy(archivo, response.getOutputStream());
	            
	            //Terminando de extraer bytes para su envio (descarga)
	            response.flushBuffer();
	            
	        } catch (IOException ex) {
	            log.info("Error de archivo, no fue posible la descarga");
	            throw ex;
	        }
			
		} else {
			log.info("EL pedido no existe en sistem a");
		}
    }
	
	
	
	
	
	
	//Metodo que nos servira para verificar si un usuario tiene los permisos necesarios
		public boolean hasRole(String role) {
			SecurityContext context = SecurityContextHolder.getContext();
			if(context == null) {
				return false;
			}
			
			Authentication auth = context.getAuthentication();
			
			if(auth == null) {
				return false;
			}
			
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
			
			for(GrantedAuthority authority: authorities) {
				if(role.equals(authority.getAuthority())){
					log.info("Hola".concat(auth.getName()).concat(" tu rol es: ").concat(authority.getAuthority()));
					return true;
				}
			}
			return false;
			
		}
		
		
		
		@RequestMapping(value = "/eliminar/{id}")
		@Secured("ROLE_ADMIN")
		public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {
			if (id > 0) {
				Pedido pedido = pedidoService.findOne(id);

				pedidoService.delete(id);
				flash.addFlashAttribute("success", "Pedido eliminado");

				if (documentService.delete(pedido.getArchivo())) {
					
				}

			}

			return "redirect:/listarPedidosPage";
		}

}
