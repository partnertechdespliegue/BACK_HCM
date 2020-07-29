package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.TipoPago;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTipoPagoRepo {

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
