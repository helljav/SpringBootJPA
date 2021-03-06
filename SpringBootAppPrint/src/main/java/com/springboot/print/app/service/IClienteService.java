package com.springboot.print.app.service;

import java.util.List;

import com.springboot.print.app.models.entity.Cliente;


public interface IClienteService {
	public List<Cliente> findAll();
	public void save(Cliente cliente);
	public Cliente findOne(Long id);
	public void delete(Long id);
	public Cliente findByusername(String username);
}
