package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.controller.ModuloController;
import com.mitocode.dto.ResponseWrapper;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestModuloController extends AbstractTestNGSpringContextTests {
	
	@Autowired
	ModuloController modulo_controller;
	
	@Test (priority = 0)
	public void testListarPerfiles() throws Exception {
	
		ResponseWrapper resp = modulo_controller.listar();
		
		assertTrue(resp.getAaData().size() > 0);
	}

}
