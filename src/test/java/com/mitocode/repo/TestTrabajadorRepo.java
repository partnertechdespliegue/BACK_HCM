package com.mitocode.repo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Empresa;
import com.mitocode.model.Trabajador;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTrabajadorRepo {
	
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
