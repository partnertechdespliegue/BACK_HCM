package com.mitocode.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.TipoDoc;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTipoDocServiceImpl {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoDocServiceImpl tipDoc_service;
	
	@Test
	@Transactional
	public void testListarTipoDoc() throws Exception {
		List<TipoDoc> lstipDoc = tipDoc_service.listar();
		
		assertEquals(3, lstipDoc.size());
	}

}
