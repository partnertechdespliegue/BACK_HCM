package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.controller.EpsController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestEpsController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	EpsController eps_controller;
	
	@Test
	public void testListarEps() throws Exception {
		
		Empresa empresa = data.nuevaEmpresa();
		ResponseWrapper lseps = eps_controller.listarPorEmpresa(empresa);
		
		assertEquals(lseps.getAaData().size(), 6);
	}

}
