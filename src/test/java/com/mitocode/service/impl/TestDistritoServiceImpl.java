package com.mitocode.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Distrito;
import com.mitocode.model.Provincia;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDistritoServiceImpl {

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
