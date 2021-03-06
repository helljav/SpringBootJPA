package com.springboot.print.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;


@Entity
@Table(name="authorities",uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})}) //nivel de tablas, nivel de esquemas
@Data
public class Role  implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	
	private String authority;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
