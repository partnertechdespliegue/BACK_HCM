package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.TipoZona;
import com.mitocode.service.impl.TipoZonaServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestTipoZonaServiceImpl extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoZonaServiceImpl tipZona_service;
	
	@Test
	@Transactional
	public void testListarTipoZona() throws Exception {
		List<TipoZona> lstipZona = tipZona_service.listar();
		
		assertEquals(12, lstipZona.size());
	}

}
