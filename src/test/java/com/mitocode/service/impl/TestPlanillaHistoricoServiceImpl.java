package com.mitocode.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.PlanillaDTO;
import com.mitocode.model.PlanillaHistorico;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPlanillaHistoricoServiceImpl {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	PlanillaHistoricoServiceImpl planH_service;
	
	@Test
	@Transactional
	public void testRegistrarPlanillaHistorico() throws Exception {
		
		PlanillaHistorico planHi = data.nuevaPlanillaHistorico();
		PlanillaHistorico resp = planH_service.registrar(planHi);
		
		assertEquals(planHi, resp);
	}
	
	@Test
	@Transactional
	public void testCalculoMovTotalPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = planH_service.calculoMovTotal(planDTO);
		
		assertEquals(150, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoHorExtra25PlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = planH_service.calculoHorExtra25(planDTO);
		
		assertEquals(103.13, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoHorExtra35PlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = planH_service.calculoHorExtra35(planDTO);
		
		assertEquals(10.97, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoRemJorNormPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = planH_service.calculoRemJorNorm(planDTO, false);
		
		assertEquals(929.99, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoAsigFamPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = planH_service.calculoAsigFam(planDTO);
		
		assertEquals(0.0, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoRemDiaFerdoLaboPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = planH_service.calculoRemDiaFerdoLabo(planDTO);
		
		assertEquals(71.53, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoRemDiaVacaPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = planH_service.calculoRemDiaVaca(planDTO);
		
		assertEquals(107.30, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoRemFerdoPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = planH_service.calculoRemFerdo(planDTO);
		
		assertEquals(35.77, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoDsctComSobFLuMixPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		PlanillaHistorico planH = data.nuevaPlanillaHistorico();
		Double rem_hrs_extra = planH.getRemHoExt25() + planH.getRemHoExt35();
		double totComp= planH.getRemDiaFerdoLabo() + planH.getRemJorNorm() + planH.getAsigFam() + planH.getRemDiaVaca() + planH.getRemVacaVend() + rem_hrs_extra + planH.getRemFerdo();
		double resp = planH_service.calculoDsctComSobFLuMix(planDTO, totComp);
		
		assertEquals(0, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoDsctComSobFLuPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		PlanillaHistorico planH = data.nuevaPlanillaHistorico();
		Double rem_hrs_extra = planH.getRemHoExt25() + planH.getRemHoExt35();
		double totComp= planH.getRemDiaFerdoLabo() + planH.getRemJorNorm() + planH.getAsigFam() + planH.getRemDiaVaca() + planH.getRemVacaVend() + rem_hrs_extra + planH.getRemFerdo();
		double resp = planH_service.calculoDsctComSobFLu(planDTO, totComp);
		
		assertEquals(38.75, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoRemGratPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		planDTO.getPlanilla().getPdo_mes().setIdPdoMes(12);
		double resp = planH_service.calculoRemGrat(planDTO, true);
		
		assertEquals(0, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoEssaludVidaPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = planH_service.calculoEssaludVida(planDTO);
		
		assertEquals(3 , resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoBonif29351PlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		planDTO.getPlanilla().getPdo_mes().setIdPdoMes(12);

		double resp = planH_service.calculoBonif29351(planDTO);
		
		assertEquals(0, resp, 0.01);		
	}
	
	@Test
	@Transactional
	public void testCalculoAporEssaludPlanillaHistorico() throws Exception {
		
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double a = planDTO.getContrato().getTrabajador().getEps().getAporte();
		double resp = planH_service.calculoAporEssalud(planDTO, a);
		assertEquals(0.09, resp, 0.01);		
	}


}
