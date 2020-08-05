package com.mitocode.Thread;

import org.springframework.beans.factory.annotation.Autowired;

import com.mitocode.model.Trabajador;
import com.mitocode.model.Usuario;
import com.mitocode.service.EnviarMailService;
import com.mitocode.service.impl.EnviarMailServiceImpl;

public class EnviarMailThread extends Thread {

	private Trabajador trabajador;
	private Usuario usuario;
	private Integer estado;
	EnviarMailService mailService;
	private String msg;

	public EnviarMailThread(EnviarMailService mailService, Trabajador trabajador, String msg, Integer estado) {
		super();
		this.mailService = mailService;
		this.trabajador = trabajador;
		this.msg = msg;
		this.estado = estado;
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
		} else if (estado == 0) {
			enviarMailError();
		} else if (estado == 2) {
			enviarMailAntiguo();
			enviarMailNuevo();
		}

	}

	private void enviarMailOk() {
		String asunto = "REGISTRO - Credenciales aplicación PLANTEC";
		String mensaje = "Bienvenido " + this.trabajador.getNombres().toUpperCase() + "\n\n"
				+ "Puede acceder a la aplicación móvil de la empresa con las siguientes credenciales." + "\n\nUsuario: "
				+ this.usuario.getUsername() + "\nContraseña: 12345";
		mailService.enviarMail(this.trabajador.getCorreo(), asunto, mensaje);
	}

	private void enviarMailError() {
		String asunto = "ERROR - Problema registrando trabajador";
		String mensaje = "Hola " + trabajador.getNombres().toUpperCase()
				+ "\nHa ocurrido un error al querer registrarte en el sistema con el correo: " + trabajador.getCorreo()
				+ ". \nPor favor contáctese con el administrador de la aplicación. \n\n" + msg;
		mailService.enviarMail(this.trabajador.getCorreo(), asunto, mensaje);
	}

	private void enviarMailNuevo() {
		String asunto = "ACTUALIZACIÓN- Cambio de correo";
		String nombreCompleto = (trabajador.getNombres() + " " + trabajador.getApePater() + " "
				+ trabajador.getApeMater()).toUpperCase();
		String mensaje = "Hola " + nombreCompleto + ".\n" + "Se vinculo correctamente tu cuenta al nuevo correo: "
				+ trabajador.getCorreo() + "\n\nSaludos.";
		mailService.enviarMail(this.trabajador.getCorreo(), asunto, mensaje);
	}

	private void enviarMailAntiguo() {
		String asunto = "ACTUALIZACIÓN- Cambio de correo";
		String nombreCompleto = (trabajador.getNombres() + " " + trabajador.getApePater() + " "
				+ trabajador.getApeMater()).toUpperCase();
		String mensaje = "Hola " + " " + nombreCompleto + ".\n" + "Se dio de baja exitosamente al correo: "
				+ usuario.getEmail() + ", el nuevo correo vinculado es: " + trabajador.getCorreo() + ".\n"
				+ "Sí usted no solicitó este cambio, por favor contáctese con el administrador de la aplicación."
				+ "\n\nSaludos.";
		mailService.enviarMail(this.usuario.getEmail(), asunto, mensaje);
	}

}
