package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.TipoContrato;
import com.mitocode.service.impl.TipoContratoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestTipoContratoServiceImpl extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoContratoServiceImpl tipCont_service;
	
	@Test
	@Transactional
	public void testListarTipoContrato() throws Exception {
		
		List<TipoContrato> lstipCont = tipCont_service.listar();
		
		assertEquals(22, lstipCont.size());
	}

}
