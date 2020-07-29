package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.TipoPagoController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestTipoPagoController extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoPagoController tipPago_controller;

	@Test
	public void testListarTipoPago() throws Exception {
		ResponseWrapper lstipPago = tipPago_controller.listar();
		
		assertEquals(lstipPago.getAaData().size(), 3);
	}

}
