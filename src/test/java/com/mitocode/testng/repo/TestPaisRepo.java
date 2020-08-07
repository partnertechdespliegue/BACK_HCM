package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Pais;
import com.mitocode.repo.PaisRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestPaisRepo extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	PaisRepo pais_repo;
	
	@Test
	@Transactional
	public void testFindAllPais() throws Exception {
		
		List<Pais> lspais = pais_repo.findAll();
		
		assertEquals(243, lspais.size());
	}

}
