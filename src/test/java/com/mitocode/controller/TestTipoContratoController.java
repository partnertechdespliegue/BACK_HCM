package com.mitocode.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestTipoContratoController {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoContratoController tipCont_controller;
	
	@Test
	//@Transactional
	public void testListarTipoContrato() throws Exception {
		
		ResponseWrapper lstipCont = tipCont_controller.listar();
		
		assertEquals(22, lstipCont.getAaData().size());
	}

}
