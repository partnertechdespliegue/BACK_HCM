package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Empresa;
import com.mitocode.model.Trabajador;
import com.mitocode.repo.TrabajadorRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestTrabajadorRepo extends AbstractTestNGSpringContextTests{
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	TrabajadorRepo trab_repo;

	@Test
	@Transactional
	public void testSaveTrabajador() throws Exception {
		
		Trabajador trab = data.nuevoTrabajador();
		
		Trabajador resp = trab_repo.save(trab);

		assertEquals(trab, resp);
	}

}
