package com.mitocode.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.PlanillaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.PlanillaHistorico;
import com.mitocode.model.Trabajador;
import com.mitocode.service.TrabajadorService;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestPlanillaController {

	DataDuroComplementos data = new DataDuroComplementos();

	/*@Autowired
	PlanillaController plan_controller;
	
	@Test
	//@Transactional
	public void testGenerarPlanilla() throws Exception {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		planDTO.getPlanilla().getPdo_mes().setIdPdoMes(12);

		ResponseWrapper resp =plan_controller.generarPlanilla(planDTO);
		
		PlanillaHistorico respF = (PlanillaHistorico) resp.getDefaultObj();
		
		assertEquals(806.0, respF.getRemJorNorm(),0.01);
	}*/

}
