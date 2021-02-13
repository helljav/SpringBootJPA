package com.springboot.print.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.print.app.models.entity.Pedido;

public interface IPedidoService {
	public List<Pedido> findAll();
	public Page<Pedido> findAll(Pageable pageable);
	public void save (Pedido pedido);
	public Pedido findOne(Long id);
	public void delete(Long id);

}
