package com.springboot.print.app.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springboot.print.app.models.entity.Cliente;



public interface IClienteDao extends CrudRepository<Cliente, Long>{
	

	List<Cliente> findByEmail(String email);
	
	public Cliente findByUsername(String username);
	

}
