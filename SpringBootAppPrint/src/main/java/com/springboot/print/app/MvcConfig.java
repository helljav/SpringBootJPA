package com.springboot.print.app;

import java.nio.file.Paths;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import lombok.extern.slf4j.Slf4j;
/**
 * Creamos esta clase para configurar la ruta donde se almacenaran nuestros archivos (imagenes)
 * siendo esta una ruta fisica y el back lo pueda detectar
 */

@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {
	
	
	/*
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);//Los asteriscos pertenecen a un nombre variable del archivo
		log.info("MVCCONFIG sesurcePAth "+ resourcePath);
	}
	*/
	
	//Aqui podremos controlar el error 403(acceso denegado )
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	
	//Este metodo nos sirve para poder encriptar sus contraseñas
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}
	
	
	//Este metodo nos ayudara a resolver y administrar las localidades para el idioma
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localResolver = new SessionLocaleResolver();
		localResolver.setDefaultLocale(new Locale ("es","Es"));
		return localResolver;
	}
	
	//Este metodo sirve como un interceptor para poder cambiar el idioma
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");//Cada vez que se mande a llamar con protocolo http se cambiara el lenguje
		return localeInterceptor;
	}


	//Metodo para registrar el interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(localeChangeInterceptor());
	}

	
//	@Bean
//	public Jaxb2Marshaller jaxb2Marshaller() {
//		Jaxb2Marshaller marshaller =  new Jaxb2Marshaller();
//		marshaller.setClassesToBeBound(new Class[] {com.bolsadeideas.springboot.app.view.xml.ClienteList.class});
//		return marshaller;
//	}
	
	
	
	
}
