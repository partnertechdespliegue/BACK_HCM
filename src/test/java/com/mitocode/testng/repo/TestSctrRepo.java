package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Sctr;
import com.mitocode.repo.SctrRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestSctrRepo extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	SctrRepo sctr_repo;
	
	@Test
	@Transactional
	public void testFindAllSctr() throws Exception {
		
		List<Sctr> lssctr = sctr_repo.findAll();
		
		assertEquals(5, lssctr.size());
	}

}
