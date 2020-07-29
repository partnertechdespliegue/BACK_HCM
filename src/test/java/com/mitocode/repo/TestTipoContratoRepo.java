package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.TipoContrato;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTipoContratoRepo {

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
