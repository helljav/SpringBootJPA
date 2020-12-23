package com.springboot.print.app.handler;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

/**
 * Esta clase nos permite una mayor personalizacion y configuracion al momento de ingresar sesion
 * y nos muestre una mensaje de que fue ingresado con exito 
 * @author HP
 *
 */
@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
    private LocaleResolver localeResolver;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//Administrador map para los mensajes flash
		SessionFlashMapManager flashManager = new SessionFlashMapManager();
		FlashMap flashMap = new FlashMap();
		
		Locale locale = localeResolver.resolveLocale(request);
		String mensaje = String.format(messageSource.getMessage("text.login.success", null, locale), authentication.getName());
		
		flashMap.put("success", mensaje);
		
		flashManager.saveOutputFlashMap(flashMap, request, response);
		
		if(authentication != null) {
			logger.info(mensaje);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
