package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Departamento;
import com.mitocode.model.Provincia;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProvinciaRepo {
	
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
