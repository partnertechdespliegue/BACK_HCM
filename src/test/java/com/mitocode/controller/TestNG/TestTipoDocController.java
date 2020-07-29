package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.TipoDocController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestTipoDocController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoDocController tipDoc_controller;
	
	@Test
	public void testListarTipoDoc() throws Exception {
		ResponseWrapper lstipDoc = tipDoc_controller.listar();
		assertEquals(lstipDoc.getAaData().size(), 3);
	}

}
