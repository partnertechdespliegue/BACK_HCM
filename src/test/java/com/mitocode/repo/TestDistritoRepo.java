package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Distrito;
import com.mitocode.model.Provincia;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDistritoRepo {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	DistritoRepo dist_repo;

	@Test
	@Transactional
	public void testListarPorProvinciaEmpresa() throws Exception {
		
		Provincia prov = data.nuevoProvincia();
		//prov.setIdProvincia(1);
		
		List<Distrito> lsdist = dist_repo.findByProvincia(prov);
		
		assertEquals(41, lsdist.size());
	}


}
