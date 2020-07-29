package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.SituacionController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestSituacionController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	SituacionController situa_controller;
	
	@Test
	public void testListarSituacion() throws Exception {
		
		ResponseWrapper lssitua = situa_controller.listar();
		
		assertEquals(lssitua.getAaData().size(), 1);
	}

}
