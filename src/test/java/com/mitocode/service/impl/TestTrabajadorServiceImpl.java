package com.mitocode.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Trabajador;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTrabajadorServiceImpl {

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
