package com.springboot.print.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.print.app.dao.IPedidoDao;
import com.springboot.print.app.models.entity.Pedido;



@Service
public class IPedidoServiceImpl implements IPedidoService {
	
	@Autowired
	private IPedidoDao pedidoDao;
	
	

	@Override
	public List<Pedido> findAll() {
		// TODO Auto-generated method stub
		return (List<Pedido>)pedidoDao.findAll();
	}

	@Override
	public void save(Pedido pedido) {
		pedidoDao.save(pedido);
	}

	@Override
	public Pedido findOne(Long id) {
		// TODO Auto-generated method stub
		return pedidoDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		pedidoDao.deleteById(id);

	}

}
