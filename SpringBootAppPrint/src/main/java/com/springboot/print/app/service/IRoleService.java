package com.springboot.print.app.service;

import java.util.List;

import com.springboot.print.app.models.entity.Role;

public interface IRoleService {
	public List<Role> findAll();
	public void save(Role role);
	public Role findOne(Long id);
	public void delete(Long id);

}
