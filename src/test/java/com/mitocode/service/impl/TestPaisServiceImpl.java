package com.mitocode.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Pais;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPaisServiceImpl {

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
