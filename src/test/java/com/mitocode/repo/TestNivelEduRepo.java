package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import com.mitocode.model.NivelEdu;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestNivelEduRepo {

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
