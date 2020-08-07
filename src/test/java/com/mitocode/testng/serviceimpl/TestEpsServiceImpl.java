package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Eps;
import com.mitocode.service.impl.EpsServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestEpsServiceImpl extends AbstractTestNGSpringContextTests {
	
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
