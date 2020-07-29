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
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDepartamentoRepo {
	
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
