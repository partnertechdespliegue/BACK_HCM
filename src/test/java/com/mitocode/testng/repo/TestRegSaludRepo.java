package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.RegSalud;
import com.mitocode.repo.RegSaludRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestRegSaludRepo extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	RegSaludRepo regSal_repo;
	
	@Test
	@Transactional
	public void testFindAllRegSalud() throws Exception {
		
		List<RegSalud> lsregSal = regSal_repo.findAll();
		
		assertEquals(8, lsregSal.size());
		
	}

}
