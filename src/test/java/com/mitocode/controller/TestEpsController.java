package com.mitocode.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestEpsController {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	EpsController eps_controller;
	
	@Test
	//@Transactional
	public void testListarEps() throws Exception {
		Empresa empresa = data.nuevaEmpresa();
		ResponseWrapper lseps = eps_controller.listarPorEmpresa(empresa);
		
		assertEquals(6, lseps.getAaData().size());
	}

}
