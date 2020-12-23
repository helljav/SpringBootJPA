package com.springboot.print.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class SpringBootAppPrintApplication {
	
//	@Autowired
//	private IUploadFileService uploadFileService;
	
	//Con esto encriptamos las  contraseñas
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAppPrintApplication.class, args);
		
	}
	
	
	public void run(String... args) throws Exception {
//		//Borramos la carpeta de los archivos
//		uploadFileService.deleteAll();
//		
//		//Creamos la carpeta de los archivos
//		uploadFileService.init();
//		
		
		
		//Generamos nuestras contraseñas de ejemplo
		String password = "12345";
				
				
		// Que cree dos contraseñas encriptadas a partir de 12345
		for (int i = 0; i < 2; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
			
		}		
		
	}

}
