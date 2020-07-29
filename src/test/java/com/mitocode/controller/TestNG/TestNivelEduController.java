package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.NivelEduController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestNivelEduController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	NivelEduController nivEd_controller;
	
	@Test
	//@Transactional
	public void testListarNivelEdu() throws Exception {
		
		ResponseWrapper lsnivEd = nivEd_controller.listar();
		
		assertEquals(lsnivEd.getAaData().size(), 21);
	}

}
