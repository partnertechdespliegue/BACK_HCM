package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.DistritoController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Provincia;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestDistritoController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	DistritoController dist_controller;
	
	@Test
	public void testlistarPorProvinciaDistrito() throws Exception {
		
		Provincia prov = data.nuevoProvincia();
		prov.setIdProvincia(134);
		
		ResponseWrapper lsprov = dist_controller.listarPorProvincia(prov);
		
		assertEquals(lsprov.getAaData().size(), 41);
	}

}
