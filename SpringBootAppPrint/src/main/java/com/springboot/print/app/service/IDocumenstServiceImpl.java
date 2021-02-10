package com.springboot.print.app.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Qualifier("Documentos")
public class IDocumenstServiceImpl implements IUploadFileService{
	
	private final static String DOCUMENTS_FOLDER = "documentos";


	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathDocument = getPath(filename);
		log.info("path foto : " + pathDocument);
		Resource recurso = null;

		recurso = new UrlResource(pathDocument.toUri());

		if (!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error: no se encontro la foto");
		}

		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		// Creamos un nombre unico y aleatorio junto con el nombre del archivo
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		Path rootPath = getPath(uniqueFilename);

		log.info("rootPaht " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}

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

	@Override
	public void deleteAll() {
	FileSystemUtils.deleteRecursively(Paths.get(DOCUMENTS_FOLDER).toFile());
		
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(DOCUMENTS_FOLDER));
		
		
	}
	
	
	public Path getPath(String filename) {
		// Mandamos la direccion absoluta de donde sera el destino de los archivos
		return Paths.get(DOCUMENTS_FOLDER).resolve(filename).toAbsolutePath();
	}

}
