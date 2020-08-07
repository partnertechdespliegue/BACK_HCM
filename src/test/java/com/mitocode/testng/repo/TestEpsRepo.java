package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Eps;
import com.mitocode.repo.EpsRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestEpsRepo extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	EpsRepo eps_repo;
	
	@Test
	@Transactional
	public void testFindAllEps() throws Exception {
		
		List<Eps> lseps = eps_repo.findAll();
		
		assertEquals(6, lseps.size());
	}
}
