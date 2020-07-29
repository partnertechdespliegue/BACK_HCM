package com.mitocode.repo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.PlanillaHistorico;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPlanillaHistoricaRepo {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	PlanillaHistoricaRepo planH_repo;
	
	@Test
	@Transactional
	public void testSavePlanillaHistorica() throws Exception {
		PlanillaHistorico planHi = data.nuevaPlanillaHistorico();		
		PlanillaHistorico resp = planH_repo.save(planHi);
		
		assertEquals(planHi, resp);
	}

}
