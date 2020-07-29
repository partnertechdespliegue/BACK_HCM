package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.controller.TipoZonaController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestTipoZonaController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoZonaController tipZona_controller;
	
	@Test
	public void testListarTipoZona() throws Exception {
		ResponseWrapper lstipZona = tipZona_controller.listar();
		
		assertEquals(lstipZona.getAaData().size(), 12);
	}

}
