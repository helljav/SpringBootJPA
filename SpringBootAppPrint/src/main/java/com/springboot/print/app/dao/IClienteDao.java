package com.springboot.print.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.print.app.models.entity.Cliente;



public interface IClienteDao extends CrudRepository<Cliente, Long>{
	
	public Cliente findByUsername(String username);
	

}
