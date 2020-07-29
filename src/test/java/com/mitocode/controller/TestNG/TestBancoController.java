package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.BancoController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestBancoController extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	BancoController banco_controller;

	@Test
	public void testListarBanco() throws Exception {
		
		ResponseWrapper lsbanco = banco_controller.listar();
		assertTrue(0 < lsbanco.getAaData().size());
	}

}
