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

public class TestSctrController {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	SctrController sctr_controller;
	
	@Test
	//@Transactional
	public void testListarSctr() throws Exception {
		
		ResponseWrapper lssctr = sctr_controller.listar();
		
		assertEquals(5, lssctr.getAaData().size());
	}

}
