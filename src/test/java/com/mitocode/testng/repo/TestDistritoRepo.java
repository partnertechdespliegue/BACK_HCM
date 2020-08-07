package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Distrito;
import com.mitocode.model.Provincia;
import com.mitocode.repo.DistritoRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestDistritoRepo extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	DistritoRepo dist_repo;

	@Test
	@Transactional
	public void testListarPorProvinciaEmpresa() throws Exception {
		
		Provincia prov = data.nuevoProvincia();
		//prov.setIdProvincia(1);
		
		List<Distrito> lsdist = dist_repo.findByProvincia(prov);
		
		assertEquals(41, lsdist.size());
	}


}
