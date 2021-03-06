package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.TipoPago;
import com.mitocode.service.impl.TipoPagoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestTipoPagoServiceImpl extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoPagoServiceImpl tipPago_service;
	
	@Test
	@Transactional
	public void testListarTipoPago() throws Exception {
		List<TipoPago> lstipPago = tipPago_service.listar();
		assertEquals(3, lstipPago.size());
	}

}
