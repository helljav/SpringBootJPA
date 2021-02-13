package com.springboot.print.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.print.app.dao.IPedidoDao;
import com.springboot.print.app.models.entity.Pedido;



@Service
public class IPedidoServiceImpl implements IPedidoService {
	
	@Autowired
	private IPedidoDao pedidoDao;
	
	
	@Transactional(readOnly=true)//Le decimos al metodo que es solo de lectura
	@Override
	public List<Pedido> findAll() {
		// TODO Auto-generated method stub
		return (List<Pedido>) pedidoDao.findAll();
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

	@Override
	public Page<Pedido> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return (Page<Pedido>) pedidoDao.findAll(pageable);
	}

}
