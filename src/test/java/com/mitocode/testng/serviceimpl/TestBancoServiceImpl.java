package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Banco;
import com.mitocode.model.Empresa;
import com.mitocode.service.impl.BancoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestBancoServiceImpl extends AbstractTestNGSpringContextTests{
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	BancoServiceImpl banco_service;

	@Test
	@Transactional
	public void testListarBanco() throws Exception {
		
		List<Banco> lsbanco = banco_service.listar();
		
		assertEquals(7, lsbanco.size());
	}

}
