package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Sctr;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSctrRepo {

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
