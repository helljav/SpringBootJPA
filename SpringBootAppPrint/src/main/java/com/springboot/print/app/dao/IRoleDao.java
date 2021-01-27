package com.springboot.print.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.print.app.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long> {

}
