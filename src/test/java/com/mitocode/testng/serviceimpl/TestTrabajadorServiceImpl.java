package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Trabajador;
import com.mitocode.service.impl.TrabajadorServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestTrabajadorServiceImpl extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	TrabajadorServiceImpl trab_service;
	
	@Test
	@Transactional
	public void testRegistrarTrabajador() throws Exception {
		
		Trabajador trab = data.nuevoTrabajador();
		Trabajador resp = trab_service.registrar(trab);

		assertEquals(trab, resp);
	}
	
	@Test
	@Transactional
	public void testModificarTrabajador() throws Exception {
		
		Trabajador trab = data.nuevoTrabajador();
		trab.setIdTrabajador(1);
		trab.setNombres("Mauricio");
		Trabajador resp = trab_service.modificar(trab);

		assertEquals("Mauricio", resp.getNombres());
	}

}
