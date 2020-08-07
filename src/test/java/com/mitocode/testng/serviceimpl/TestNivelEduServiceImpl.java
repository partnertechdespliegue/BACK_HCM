package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.NivelEdu;
import com.mitocode.service.impl.NivelEduServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestNivelEduServiceImpl extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	NivelEduServiceImpl nivEd_service;
	
	@Test
	@Transactional
	public void testListarNivelEdu() throws Exception {
		
		List<NivelEdu> lsnivEd = nivEd_service.listar();
		
		assertEquals(21, lsnivEd.size());
	}

}
