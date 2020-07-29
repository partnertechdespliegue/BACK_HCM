package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.TipoContratoController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestTipoContratoController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoContratoController tipCont_controller;
	
	@Test
	public void testListarTipoContrato() throws Exception {
		
		ResponseWrapper lstipCont = tipCont_controller.listar();
		
		assertEquals(lstipCont.getAaData().size(), 22);
	}

}
