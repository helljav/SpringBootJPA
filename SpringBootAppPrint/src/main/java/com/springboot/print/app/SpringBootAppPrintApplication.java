package com.springboot.print.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.springboot.print.app.service.IUploadFileService;


@SpringBootApplication
public class SpringBootAppPrintApplication implements CommandLineRunner {
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	//Con esto encriptamos las  contraseñas
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		//Borramos la carpeta de los archivos
		uploadFileService.deleteAll();
		
		//Creamos la carpeta de los archivos
		uploadFileService.init();
	
		
		
		//Generamos nuestras contraseñas de ejemplo
		String password = "12345";
				
				
		// Que cree dos contraseñas encriptadas a partir de 12345
		for (int i = 0; i < 2; i++) {
			String bcryptPassword = passwordEncoder.encode(password);
			System.out.println(bcryptPassword);
			
		}		
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAppPrintApplication.class, args);
		
	}
	
	
	

}
