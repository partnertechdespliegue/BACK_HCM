package com.mitocode.testng.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.model.Solicitud;
import com.mitocode.service.impl.SolicitudServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class SolicitudServiceImplTest extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	SolicitudServiceImpl service;

	@Test
	public void registrarTest() {
		
		Solicitud solicitud = data.nuevaSolicitud();
		service.registrar(solicitud);
		
		
	}
}
