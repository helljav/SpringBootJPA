package com.springboot.print.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.print.app.handler.LoginSuccesHandler;
import com.springboot.print.app.service.JpaUserDetailsService;

//import com.bolsadeideas.springboot.app.auth.handler.LoginSuccesHandler;
//import com.bolsadeideas.springboot.app.models.service.JpaUserDetailsService;

/**
 * Esta clase permite la configuracion del Spring Security 
 * @author HP
 *
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //habilitamos el usuo de anotaciones en nuestros controladores
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private LoginSuccesHandler successHandler;
	//Con esto podemos codificar las contraseña de los usuarios
	
	/* Esta inyeccion de dependecia se utiliza para jdbc
	@Autowired
	private DataSource dataSource;
	*/
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	@Autowired
	private  JpaUserDetailsService userDetailService;
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		/*
		Ya no utilizaremos esta configuracion ya que estara vinculada con JDBC para controlar a nuestros usuarios y los roles
		//Con este password encoder, podemos crear los usuarios y cifrar sus contraseñas
		PasswordEncoder encoder =  this.passwordEncoder;
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		//Creamos nuestros usuarios
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		.withUser(users.username("andres").password("12345").roles("USER"));//El password de forma automatica se encripta
		*/
		
		
		/*Configuracion de los usuarios con jdbc
		
		builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("select username, password, enabled from users where username=?")//Consulta login
		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id)where u.username=?");//Consulta para tener los roles
		*/
		
		
		
		
		/* Configuracion con jpa
		 * 
		 */		
		builder.userDetailsService(userDetailService)
		.passwordEncoder(passwordEncoder);
	}
	
	

	//nuevo metodo para las autorizaciones http
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//Primero rutas publicas y despues estan las privadas
		http.authorizeRequests().antMatchers("/","/css/**","/js/**","/image/**","/home**","/locale","/api/clientes**").permitAll()
		//Estas son las rutas privadas, pero estan comentadas porque ahora las controlamos en los controladores a base de anotaciones 
		//.antMatchers("/ver/**").hasAnyRole("USER")
		//.antMatchers("/uploads/**").hasAnyRole("USER")
		//.antMatchers("/form/**").hasAnyRole("ADMIN")
		//.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		//.antMatchers("/factura/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
			.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
			.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
	
	
		
	

}
