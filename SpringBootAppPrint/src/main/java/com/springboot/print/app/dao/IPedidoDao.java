package com.springboot.print.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.print.app.models.entity.Pedido;

public interface IPedidoDao extends CrudRepository<Pedido, Long> {

}
