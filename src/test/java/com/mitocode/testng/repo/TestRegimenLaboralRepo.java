package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.RegimenLaboral;
import com.mitocode.repo.RegimenLaboralRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestRegimenLaboralRepo extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	RegimenLaboralRepo regLab_repo;
	
	@Test
	@Transactional
	public void testFindAllRegimenLaboral() throws Exception {
		
		List<RegimenLaboral> lsregLab = regLab_repo.findAll();
		
		assertEquals(22, lsregLab.size());
	}

}
