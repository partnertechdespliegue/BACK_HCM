package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.PlanillaHistorico;
import com.mitocode.repo.PlanillaHistoricaRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestPlanillaHistoricaRepo extends AbstractTestNGSpringContextTests{

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
