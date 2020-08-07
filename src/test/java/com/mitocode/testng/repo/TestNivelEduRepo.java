package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.NivelEdu;
import com.mitocode.repo.NivelEduRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestNivelEduRepo extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	NivelEduRepo nivEd_repo;
	
	@Test
	@Transactional
	public void testListarNivelEducativo() throws Exception {
		
		List<NivelEdu> lsnivEd = nivEd_repo.findAll();
		
		assertEquals(21, lsnivEd.size());
	}

}
