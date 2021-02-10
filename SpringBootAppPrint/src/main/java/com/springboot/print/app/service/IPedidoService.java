package com.springboot.print.app.service;

import java.util.List;

import com.springboot.print.app.models.entity.Pedido;

public interface IPedidoService {
	public List<Pedido> findAll();
	public void save (Pedido pedido);
	public Pedido findOne(Long id);
	public void delete(Long id);

}
