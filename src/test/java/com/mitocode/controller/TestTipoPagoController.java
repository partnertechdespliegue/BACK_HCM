package com.mitocode.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestTipoPagoController {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoPagoController tipPago_controller;

	@Test
	//@Transactional
	public void testListarTipoPago() throws Exception {
		ResponseWrapper lstipPago = tipPago_controller.listar();
		
		assertEquals(3, lstipPago.getAaData().size());
	}

}
