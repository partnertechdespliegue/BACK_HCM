package com.mitocode.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Departamento;
import com.mitocode.model.Provincia;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestProvinciaController {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	ProvinciaController prov_controller;

	@Test
	//@Transactional
	public void testListarPorDepartamentoProvincia() throws Exception {
		
		Departamento depa = data.nuevoDepartamento(); 
		
		ResponseWrapper lsprov = prov_controller.listarPorDepartamento(depa);
		
		assertEquals(10, lsprov.getAaData().size());
	}

}
