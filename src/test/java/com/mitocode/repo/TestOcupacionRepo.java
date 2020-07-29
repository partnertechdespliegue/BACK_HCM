package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Ocupacion;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestOcupacionRepo {

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
