package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.ProvinciaController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Departamento;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestProvinciaController extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	ProvinciaController prov_controller;

	@Test
	public void testListarPorDepartamentoProvincia() throws Exception {
		
		Departamento depa = data.nuevoDepartamento(); 
		depa.setIdDepartamento(14);
		
		ResponseWrapper lsprov = prov_controller.listarPorDepartamento(depa);
		
		assertEquals(lsprov.getAaData().size(), 10);
	}

}
