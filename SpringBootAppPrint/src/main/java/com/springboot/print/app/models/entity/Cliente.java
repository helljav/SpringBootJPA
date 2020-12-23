package com.springboot.print.app.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
public class Usuario {
	
	//Podriamos decir que esta es una identiada foranea para la base de datos ya que podemos tener multiples pedidos un cliente (cardinalidad multiple)
		@Builder.Default
		@OneToMany(targetEntity = Pedido.class,fetch = FetchType.LAZY,  cascade = CascadeType.MERGE) //Decimos que es de uno a muchos, con el Lazy le decimos que recorra su lista de alumnos y nos devuelba solo ese alumno y no todos de forma anticipada
		@JoinColumn(name = "usuarioId") // No crea tabla intermedia	
		private List <Pedido> pedidos = new ArrayList<>();
	
		
		//Debemos de hacer un metodo para agregar alumnos a la lista de alumnbos
		public boolean addPedido(Pedido pedido) {
			return pedidos.add(pedido);
		}
				
		public boolean removePedido(Pedido pedido) {
			return pedidos.remove(pedido);
		}

}

*/

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@DynamicUpdate
@Table(name="clientes")
public class Cliente {
	
	//LLave primaria autoincremental de uno en uno
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes="Identificador unico")  //Aparecen en la descripcion de parametros de swagger como un requisito para utilizarlo por metodos CRUD
	private Long id;	
	
	
	@ApiModelProperty(notes="Matricula del usuario")  //Aparecen en la descripcion de parametros de swagger como un requisito para utilizarlo por metodos CRUD
	private String matricula;
	
	@NotEmpty
	@Email
	@ApiModelProperty(notes="Email del usuario", required = true)
	private String email;
	
	@NotEmpty
	@ApiModelProperty(notes="Nombre_Cuenta_Usuario del usuario", required=true)
	private String username;
	
	@NotBlank
	@ApiModelProperty(notes="Contraseña_Cuenta_Usuario del usuario", required=true)
	private String password;
	
	@ApiModelProperty(notes="Fotografia del usurio")
	private String photo;
	
	
	private Boolean enabled;
	
	@OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private List<Role> roles;
	
	
	

}
