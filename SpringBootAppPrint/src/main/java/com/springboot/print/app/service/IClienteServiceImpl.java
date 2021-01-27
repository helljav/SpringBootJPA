package com.springboot.print.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.print.app.dao.IClienteDao;
import com.springboot.print.app.models.entity.Cliente;

@Service
public class IClienteServiceImpl implements IClienteService {
	
	@Autowired
	private IClienteDao clienteDao;
	
	@Transactional(readOnly=true)//Le decimos al metodo que es solo de lectura
	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}
	
	@Transactional//De esta manera ya esta de escritura
	@Override
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	
	@Override
	public Cliente findOne(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

}
