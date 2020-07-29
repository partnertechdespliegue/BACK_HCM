package com.mitocode.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Departamento;
import com.mitocode.model.Provincia;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProvinciaServiceImpl {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	ProvinciaServiceImpl prov_service;
	
	@Test
	@Transactional
	public void testListarPorDepaProvincia() throws Exception {
		
		Departamento depa = data.nuevoDepartamento(); 
		List<Provincia> lsprov = prov_service.listarPorDepartamento(depa);
		
		assertEquals(10, lsprov.size());
	}
}
