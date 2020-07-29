package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Pais;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPaisRepo {

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
