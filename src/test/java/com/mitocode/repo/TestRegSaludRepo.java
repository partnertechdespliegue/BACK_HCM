package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.RegSalud;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRegSaludRepo {

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
