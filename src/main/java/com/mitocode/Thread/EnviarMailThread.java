package com.mitocode.Thread;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitocode.model.Trabajador;
import com.mitocode.model.Usuario;
import com.mitocode.service.EnviarMailService;
import com.mitocode.service.impl.EnviarMailServiceImpl;

public class EnviarMailThread extends Thread{
	
	private Trabajador trabajador;
	private Usuario usuario;
	private Integer estado;
	EnviarMailService mailService;
	
	public EnviarMailThread(EnviarMailService mailService) {
		super();
		this.mailService = mailService;
	}

	public EnviarMailThread(EnviarMailService mailService, Trabajador trabajador, Usuario usuario, Integer estado) {
		super();
		this.mailService = mailService;
		this.trabajador = trabajador;
		this.usuario = usuario;
		this.estado = estado;
	}

	@Override
	public void run() {
		
		if (estado == 1) {
			enviarMailOk();
		}
		
	}
	
	private void enviarMailOk () {
		String asunto = "Credenciales aplicación PLANTEC";
		String mensaje = "Bienvenido " + this.trabajador.getNombres().toUpperCase() + "\n\n"
				+ "Puede acceder a la aplicación móvil de la empresa con las siguientes credenciales." + "\n\nUsuario: "
				+ this.usuario.getUsername() + "\nContraseña: 12345";
		mailService.enviarMail(this.trabajador.getCorreo(), asunto, mensaje);
	}
	
	
	/*@Override
	public void run() {
		
		if (estado == 1) {
			enviarMailOk();
		}
		
	}
	
	private void enviarMailOk () {
		String asunto = "Credenciales aplicación PLANTEC";
		String mensaje = "Bienvenido " + this.trabajador.getNombres().toUpperCase() + "\n\n"
				+ "Puede acceder a la aplicación móvil de la empresa con las siguientes credenciales." + "\n\nUsuario: "
				+ this.usuario.getUsername() + "\nContraseña: 12345";
		mailService.enviarMail(this.trabajador.getCorreo(), asunto, mensaje);
	}*/

}
