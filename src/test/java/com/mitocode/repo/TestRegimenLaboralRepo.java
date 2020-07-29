package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import com.mitocode.model.RegimenLaboral;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRegimenLaboralRepo {

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
