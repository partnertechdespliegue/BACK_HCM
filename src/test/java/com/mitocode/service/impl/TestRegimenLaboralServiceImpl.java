package com.mitocode.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.RegimenLaboral;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRegimenLaboralServiceImpl {

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
