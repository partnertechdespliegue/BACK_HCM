package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Ocupacion;
import com.mitocode.repo.OcupacionRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestOcupacionRepo extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	OcupacionRepo ocup_repo;
	
	@Test
	@Transactional
	public void testFindAllOcupacion() throws Exception {
		
		List<Ocupacion> lsocup = ocup_repo.findAll();
		
		assertEquals(4737, lsocup.size());
	}

}
