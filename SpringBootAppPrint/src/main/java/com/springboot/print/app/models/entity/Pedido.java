package com.springboot.print.app.models.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor//Te genera los constructores gracias a la dependencia de Lombok
@Builder 
@Data // Permite la creacion de geters y seters con la dependencia lombok
@Entity
@DynamicUpdate
@Table(name="pedidos")
public class Pedido {
	
	
	public Pedido() {
		
	}
	//LLave primaria autoincremental de uno en uno
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@ApiModelProperty(notes="Identificador unico")  //Aparecen en la descripcion de parametros de swagger como un requisito para utilizarlo por metodos CRUD
		private Long id;
		
		
		private String descripcionImpresion;
		
		
		@NotBlank
		private String archivo;
		
		
		@NotBlank
		private String tipoImpresion;
		
		
		@NotBlank
		private String metodoPago;
		
		
		@NotNull
		private Double precioTotal;
		
		//Muchos pedidos, un cliente
		@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL) 
		private Cliente cliente;
		
		
		

}
