package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.TipoDoc;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTipoDocRepo {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoDocRepo tipDoc_repo;

	@Test
	@Transactional
	public void testFindAllTipoDoc() throws Exception {
		
		List<TipoDoc> lstipDoc = tipDoc_repo.findAll();
		
		assertEquals(3, lstipDoc.size());
	}

}
