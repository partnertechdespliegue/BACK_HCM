package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Departamento;
import com.mitocode.model.Provincia;
import com.mitocode.repo.ProvinciaRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestProvinciaRepo extends AbstractTestNGSpringContextTests{
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	ProvinciaRepo prov_repo;

	@Test
	@Transactional
	public void testFindByDepartamentoProvincia() throws Exception {
		
		Departamento depa = data.nuevoDepartamento(); 
		List<Provincia> lsprov = prov_repo.findByDepartamento(depa);
		
		assertEquals(10, lsprov.size());
	}

}
