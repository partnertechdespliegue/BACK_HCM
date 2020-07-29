package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.TipoZona;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTipoZonaRepo {

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
