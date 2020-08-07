package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Departamento;
import com.mitocode.repo.DepartamentoRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestDepartamentoRepo extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	DepartamentoRepo depa_repo;

	@Test
	@Transactional
	public void testFindAllDepartamento() throws Exception {
		
		List<Departamento> lsdepa = depa_repo.findAll();
		
		assertEquals(25, lsdepa.size());
	}

}
