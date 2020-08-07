package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.TipoZona;
import com.mitocode.repo.TipoZonaRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestTipoZonaRepo extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoZonaRepo tipZona_repo;
	
	@Test
	@Transactional
	public void testFindAllTipoZona() throws Exception {
		List<TipoZona> lstipZona = tipZona_repo.findAll();
		
		assertEquals(12, lstipZona.size());
	}

}
