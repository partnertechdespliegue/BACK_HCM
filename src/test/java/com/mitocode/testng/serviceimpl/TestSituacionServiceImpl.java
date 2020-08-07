package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Situacion;
import com.mitocode.service.impl.SituacionServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestSituacionServiceImpl extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	SituacionServiceImpl situa_service;
	
	@Test
	@Transactional
	public void testListarSituacion() throws Exception {
		
		List<Situacion> lssituacion =situa_service.listar();
		
		assertEquals(1, lssituacion.size());
	}

}
