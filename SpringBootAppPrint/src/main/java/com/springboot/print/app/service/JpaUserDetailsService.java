package com.springboot.print.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.print.app.dao.IClienteDao;
import com.springboot.print.app.models.entity.Cliente;

import lombok.extern.slf4j.Slf4j;

/**
 * Esta clase es el servicio de la utenticacion
 * No necesitamos de una interfaz por que ya la provee Spring Securty
 * @author HP
 *
 */
@Service("jpaUserDetailService")
@Slf4j
public class JpaUserDetailsService implements UserDetailsService{
	
	@Autowired
	private IClienteDao usuarioDao;
	

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Buscamos al usuario por su nobre de usuario
		Cliente cliente = usuarioDao.findByUsername(username);
		
		//Si no se encuentra informamos de ello
		if (cliente == null) {
			
			log.info("Error login: no existe el usuario");
			throw new UsernameNotFoundException("El usuario no existe en el sistema");
			
		}
		
		
		//Interfaz de Spring scurity para asignar los roles de los usuarios
		//Creamos una lista que contendran los roles del usuario encontrado con ayuda de Spring security
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		//Se llena la lista con un foreach
		for(com.springboot.print.app.models.entity.Role role: cliente.getRoles()) {
			//Un for por cada rol del usuario, creando la instancia completa de authorities recordando que Granted es una interfaz
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		
		//Si no se encontraron roles se manda a informar
		if(authorities.isEmpty()) {
			log.info("Error en el login: El usuario no tiene roles asignados");
		}
		
		
		
		
		return new User(cliente.getUsername(), cliente.getPassword(), cliente.getEnabled(), true, true, true, authorities);
	}

}
