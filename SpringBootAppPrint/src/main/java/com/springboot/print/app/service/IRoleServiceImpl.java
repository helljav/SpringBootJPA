package com.springboot.print.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.print.app.dao.IRoleDao;
import com.springboot.print.app.models.entity.Role;

@Service
public class IRoleServiceImpl implements IRoleService {
	
	
	@Autowired
	private IRoleDao roleDao;

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return (List<Role>)roleDao.findAll();
	}

	@Override
	public void save(Role role) {
		// TODO Auto-generated method stub
		roleDao.save(role);
	}

	@Override
	public Role findOne(Long id) {
		// TODO Auto-generated method stub
		return roleDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		roleDao.deleteById(id);
		
	}

}
