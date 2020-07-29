package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.RegimenLaboralController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestRegimenLaboralController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	RegimenLaboralController regLab_controller;
	
	@Test
	public void testListarRegimenLaboral() throws Exception {
		ResponseWrapper lsregLab = regLab_controller.listar();
		
		assertEquals(lsregLab.getAaData().size(), 22);
	}

}
