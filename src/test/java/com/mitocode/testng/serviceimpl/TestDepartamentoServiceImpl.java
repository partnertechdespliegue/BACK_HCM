package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Departamento;
import com.mitocode.service.impl.DepartamentoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestDepartamentoServiceImpl extends AbstractTestNGSpringContextTests{
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	DepartamentoServiceImpl depa_service;

	@Test
	@Transactional
	public void testListarDepartamento() throws Exception {
		
		List<Departamento> lsdepa = depa_service.listar();
		
		assertEquals(25, lsdepa.size());
	}

}
