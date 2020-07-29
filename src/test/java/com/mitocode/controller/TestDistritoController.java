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
import com.mitocode.model.Empresa;
import com.mitocode.model.Provincia;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestDistritoController {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	DistritoController dist_controller;
	
	@Test
	//@Transactional
	public void testlistarPorProvinciaDistrito() throws Exception {
		
		Provincia prov = data.nuevoProvincia();
		
		ResponseWrapper lsprov = dist_controller.listarPorProvincia(prov);
		
		assertEquals(41, lsprov.getAaData().size());
	}

}
