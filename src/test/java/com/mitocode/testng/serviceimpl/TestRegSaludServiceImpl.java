package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.RegSalud;
import com.mitocode.service.impl.RegSaludServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestRegSaludServiceImpl extends AbstractTestNGSpringContextTests{

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
