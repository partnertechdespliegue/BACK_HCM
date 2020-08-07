package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.RegimenLaboral;
import com.mitocode.service.impl.RegimenLaboralServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestRegimenLaboralServiceImpl extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	RegimenLaboralServiceImpl regLab_service;
	
	@Test
	@Transactional
	public void testListarRegimenLaboral() throws Exception {
		
		List<RegimenLaboral> lsregLab = regLab_service.listar();
		
		assertEquals(22, lsregLab.size());
	}

}
