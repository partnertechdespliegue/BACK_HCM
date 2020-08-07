package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Departamento;
import com.mitocode.model.Provincia;
import com.mitocode.service.impl.ProvinciaServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestProvinciaServiceImpl extends AbstractTestNGSpringContextTests {

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
