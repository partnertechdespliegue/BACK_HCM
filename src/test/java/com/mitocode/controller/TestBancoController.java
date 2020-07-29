package com.mitocode.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Banco;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestBancoController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	BancoController banco_controller;

	@Test
	//@Transactional
	public void testListarBanco() throws Exception {
		
		ResponseWrapper lsbanco = banco_controller.listar();
		assertEquals(7, lsbanco.getAaData().size());
	}

}
