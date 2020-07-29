package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.DepartamentoController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestDepartamentoController extends AbstractTestNGSpringContextTests {

DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	DepartamentoController depa_controller;

	@Test
	public void testListarDepartamento() throws Exception {
		
		ResponseWrapper lsdepa = depa_controller.listar();
		
		assertEquals(lsdepa.getAaData().size(), 25);
	}

}
