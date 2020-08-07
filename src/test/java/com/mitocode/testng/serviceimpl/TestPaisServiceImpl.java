package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Pais;
import com.mitocode.service.impl.PaisServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestPaisServiceImpl extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	PaisServiceImpl pais_service;
	
	@Test
	@Transactional
	public void testListarPais() throws Exception {
		
		List<Pais> lspais = pais_service.listar();
		
		assertEquals(243, lspais.size());
	}

}
