package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Situacion;
import com.mitocode.repo.SituacionRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestSituacionRepo extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	SituacionRepo situa_repo;
	
	@Test
	@Transactional
	public void testFindAllSituacion() throws Exception {
		
		List<Situacion> lssituacion =situa_repo.findAll();
		
		assertEquals(1, lssituacion.size());
	}

}
