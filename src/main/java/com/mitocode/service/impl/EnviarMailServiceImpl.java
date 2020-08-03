package com.mitocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.mitocode.service.EnviarMailService;

@Service
public class EnviarMailServiceImpl implements EnviarMailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void enviarMail(String receptor, String asunto, String mensaje) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("partner.despliegue@gmail.com");
			message.setTo(receptor);
			message.setSubject(asunto);
			message.setText(mensaje);
			javaMailSender.send(message);
		} catch (Exception e) {
			System.out.println("Enviar Mensaje " + e.getMessage());
		}
	}

}
