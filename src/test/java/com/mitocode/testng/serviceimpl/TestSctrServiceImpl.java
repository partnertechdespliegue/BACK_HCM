package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Sctr;
import com.mitocode.service.impl.SctrServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestSctrServiceImpl extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	SctrServiceImpl sctr_service;
	
	@Test
	@Transactional
	public void testListarSctr() throws Exception {
		
		List<Sctr> lssctr = sctr_service.listar();
		
		assertEquals(5, lssctr.size());
	}

}
