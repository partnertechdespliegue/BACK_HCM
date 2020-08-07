package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Ocupacion;
import com.mitocode.service.impl.OcupacionServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestOcupacionServiceImpl extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	OcupacionServiceImpl ocup_service;
	
	@Test
	@Transactional
	public void testListarrOcupacion() throws Exception {
		
		List<Ocupacion> lsocup = ocup_service.listar();
		
		assertEquals(4737, lsocup.size());
	}

}
