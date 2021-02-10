package com.springboot.print.app.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Qualifier("Imagenes")
public class UploadFileServiceImpl implements IUploadFileService {

	private final static String UPLOADS_FOLDER = "uploads";

	// Este metodo nos sirve para obtener el recurso (el archivo)
	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		log.info("path foto : " + pathFoto);
		Resource recurso = null;

		recurso = new UrlResource(pathFoto.toUri());

		if (!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error: no se encontro la foto");
		}

		return recurso;
	}

	// En este metodo se escribe en el almacenamiento el archivo
	@Override
	public String copy(MultipartFile file) throws IOException {
		// Creamos un nombre unico y aleatorio junto con el nombre del archivo
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		Path rootPath = getPath(uniqueFilename);

		log.info("rootPaht " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}
	
	
	//Elimina el archivo de forma fisica
	@Override
	public boolean delete(String filename) {
		//Borramos la foto en el disco duro 
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		
		if(archivo.exists() && archivo.canRead()) {
			archivo.delete();			
			return true;
		}
		return false;
	}

	public Path getPath(String filename) {
		// Mandamos la direccion absoluta de donde sera el destino de los archivos
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
		
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
		
	}
}
