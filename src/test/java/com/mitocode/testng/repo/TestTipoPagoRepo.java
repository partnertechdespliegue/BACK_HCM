package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.TipoPago;
import com.mitocode.repo.TipoPagoRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestTipoPagoRepo extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoPagoRepo tipPago_repo;
	
	@Test
	@Transactional
	public void testFindAllTipoPago() throws Exception {
		List<TipoPago> lstipPago = tipPago_repo.findAll();
		assertEquals(3, lstipPago.size());
	}

}
