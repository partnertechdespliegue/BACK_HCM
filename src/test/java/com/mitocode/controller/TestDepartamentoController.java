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
import com.mitocode.service.impl.DepartamentoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestDepartamentoController {

DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	DepartamentoController depa_controller;

	@Test
	//@Transactional
	public void testListarDepartamento() throws Exception {
		
		ResponseWrapper lsdepa = depa_controller.listar();
		
		assertEquals(25, lsdepa.getAaData().size());
	}

}
