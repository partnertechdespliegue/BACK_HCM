package com.mitocode.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.RegSalud;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRegSaludServiceImpl {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	RegSaludServiceImpl regSal_service;
	
	@Test
	@Transactional
	public void testListarRegSalud() throws Exception {
		
		List<RegSalud> lsregSal = regSal_service.listar();
		
		assertEquals(8, lsregSal.size());
		
	}

}
