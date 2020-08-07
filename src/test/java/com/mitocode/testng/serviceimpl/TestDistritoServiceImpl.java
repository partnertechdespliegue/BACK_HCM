package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Distrito;
import com.mitocode.model.Provincia;
import com.mitocode.service.impl.DistritoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestDistritoServiceImpl extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	DistritoServiceImpl dist_service;
	
	@Test
	@Transactional
	public void testlistarPorProvinciaDistrito() throws Exception {
		
		Provincia prov = data.nuevoProvincia();
		List<Distrito> lsdist = dist_service.listarPorProvincia(prov);
		
		assertEquals(41, lsdist.size());
	}
}
