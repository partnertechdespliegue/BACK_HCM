package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.PlanillaController;
import com.mitocode.dto.PlanillaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.PlanillaHistorico;
import com.mitocode.model.Trabajador;
import com.mitocode.service.TrabajadorService;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
//@TestPropertySource(locations = "classpath:db-test.properties")
public class TestPlanillaController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	PlanillaController plan_controller;
	
	@Autowired
	TrabajadorService service_trab;
	
	/*@Test
	public void testGenerarPlanilla() throws Exception {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		planDTO.getPlanilla().getPdo_mes().setIdPdoMes(12);

		ResponseWrapper resp =plan_controller.generarPlanilla(planDTO);
		
		PlanillaHistorico respF = (PlanillaHistorico) resp.getDefaultObj();
		
		assertEquals(respF.getRemJorNorm(), 806.0, 0.01);
	}*/
	
	/*@Test
	public void pagarDeuda() throws Exception {
		
		Trabajador trab = service_trab.encontrarTrab(1);

		double resp =plan_controller.pagarDeuda(trab);
				
		assertEquals(resp, 50.0);
	}*/

}
