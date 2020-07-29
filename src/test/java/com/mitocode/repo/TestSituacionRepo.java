package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Situacion;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSituacionRepo {

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
