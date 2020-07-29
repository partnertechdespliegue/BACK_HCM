package com.mitocode.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Eps;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEpsServiceImpl {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	EpsServiceImpl eps_service;

	@Test
	@Transactional
	public void testListarEps() throws Exception {
		
		List<Eps> lseps = eps_service.listar();
		
		assertEquals(6, lseps.size());
	}

}
