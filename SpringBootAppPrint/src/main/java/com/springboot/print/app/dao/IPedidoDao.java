package com.springboot.print.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.print.app.models.entity.Pedido;

public interface IPedidoDao extends PagingAndSortingRepository<Pedido, Long> {

}
