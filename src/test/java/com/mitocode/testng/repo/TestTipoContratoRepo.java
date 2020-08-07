package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.TipoContrato;
import com.mitocode.repo.TipoContratoRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestTipoContratoRepo extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoContratoRepo tipCont_repo;
	
	@Test
	@Transactional
	public void testFindAllTipoContrato() throws Exception {
		
		List<TipoContrato> lstipCont = tipCont_repo.findAll();
		
		assertEquals(22, lstipCont.size());
	}

}
