package com.mitocode.junit.serviceimpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mitocode.dto.PlanillaDTO;
import com.mitocode.model.Afp;
import com.mitocode.model.Empresa;
import com.mitocode.model.Parametro;
import com.mitocode.model.PdoMes;
import com.mitocode.model.PlanillaHistorico;
import com.mitocode.repo.AfpRepo;
import com.mitocode.repo.ParametroRepo;
import com.mitocode.repo.PdoMesRepo;
import com.mitocode.repo.PlanillaHistoricaRepo;
import com.mitocode.service.impl.PlanillaHistoricoServiceImpl;
import com.mitocode.util.Constantes;
import com.mitocode.util.DataDuroComplementos;

public class PlanillaHistoricoServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@InjectMocks
	PlanillaHistoricoServiceImpl service;

	@Mock
	PlanillaHistoricaRepo repo;
	
	@Mock
	ParametroRepo repo_para;
	
	@Mock
	PdoMesRepo repo_pmes;
	
	@Mock
	AfpRepo repo_afp;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRegistrar() {
		PlanillaHistorico planHi = data.nuevaPlanillaHistorico();
		when(repo.save(planHi)).thenReturn(planHi);
		PlanillaHistorico resp = service.registrar(planHi);
		
		assertEquals(planHi, resp);
	}


	@Test
	public void testCalculoMovTotal() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();		
		Parametro diaComp = data.diasComputables();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diaComp);
		
		double resp = service.calculoMovTotal(planDTO);
		assertEquals(130, resp, 0.01);
	}

	@Test
	public void testCalculoHorExtra25() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = service.calculoHorExtra25(planDTO);
		
		assertEquals(103.13, resp, 0.01);
	}

	@Test
	public void testCalculoHorExtra35() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double resp = service.calculoHorExtra35(planDTO);
		
		assertEquals(131.63, resp, 0.01);	
	}

	@Test
	public void testCalculoRemJorNorm() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro diaComp = data.diasComputables();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diaComp);
		double resp = service.calculoRemJorNorm(planDTO, false);
		
		assertEquals(806.0, resp, 0.01);	
	}
	
	@Test
	public void testCalculoRemJorNormCTSAGRAGIO() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		Parametro diaComp = data.diasComputables();
		Parametro salMinVital = data.salMinVital();
		Parametro ctsAgrario = data.ctsAgragio();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diaComp);
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODSALMINVIT, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(salMinVital);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSAGRARIO, Constantes.GRPGLOBAL)).thenReturn(ctsAgrario);
		double resp = service.calculoRemJorNorm(planDTO, true);
		
		assertEquals(953.93, resp, 0.01);	
	}

	@Test
	public void testCalculoAsigFam() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro salMinVital = data.salMinVital();
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODSALMINVIT, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(salMinVital);

		double resp = service.calculoAsigFam(planDTO);
		
		assertEquals(0.0, resp, 0.01);		
	}
	

	@Test
	public void testCalculoRemDiaFerdoLabo() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro diaComp = data.diasComputables();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diaComp);
		double resp = service.calculoRemDiaFerdoLabo(planDTO);
		
		assertEquals(62.00, resp, 0.01);
	}

	@Test
	public void testCalculoRemDiaVaca() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro diaComp = data.diasComputables();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diaComp);
		double resp = service.calculoRemDiaVaca(planDTO);
		
		assertEquals(93.0, resp, 0.01);	
	}

	@Test
	public void testCalculoRemVacaVend() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro diaComp = data.diasComputables();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diaComp);
		double resp = service.calculoRemVacaVend(planDTO);
		
		assertEquals(0, resp, 0.01);
	}

	@Test
	public void testCalculoRemFerdo() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro diaComp = data.diasComputables();
		PdoMes pdoMes = data.nuevoPdoMes();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diaComp);
		when(repo_pmes.getOne(pdoMes.getIdPdoMes())).thenReturn(pdoMes);
		double resp = service.calculoRemFerdo(planDTO);
		
		assertEquals(31.0, resp, 0.01);
	}

	@Test
	public void testCalculoDsctComSobFLuMix() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		PlanillaHistorico planH = data.nuevaPlanillaHistorico();
		
		Parametro onp = data.ONP();
		Afp afp = data.nuevoAfp();
		afp.setIdAfp(1);
		Double rem_hrs_extra = planH.getRemHoExt25() + planH.getRemHoExt35();
		
		double totComp= planH.getRemDiaFerdoLabo() + planH.getRemJorNorm() + planH.getAsigFam() + planH.getRemDiaVaca() + planH.getRemVacaVend() + rem_hrs_extra + planH.getRemFerdo();
		
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODONP, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(onp);
		when(repo_afp.getOne(afp.getIdAfp())).thenReturn(afp);
		double resp = service.calculoDsctComSobFLuMix(planDTO, totComp);
		
		assertEquals(0, resp, 0.01);
	}

	@Test
	public void testCalculoMonDiasInjusti() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro diaComp = data.diasComputables();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diaComp);
		Double resp = service.calculoMonDiasInjusti(planDTO);
		
		assertEquals(1.03, resp, 0.01);
	}

	@Test
	public void testCalculoMonTardanza() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro tipoTar = data.tipoTardanza();
		Parametro cantDia = data.cantDias();
		Parametro tipoRan = data.tipoRango();
		
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODTIPTARD, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(tipoTar);
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODTARCNTDIAS, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(cantDia);
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODTIPORANGO, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(tipoRan);
		
		Double resp = service.calculoMonTardanza(planDTO);
		
		assertEquals(0.0, resp, 0.01);
	}

	@Test
	public void testCalculoDsctComSobFLu() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		PlanillaHistorico planH = data.nuevaPlanillaHistorico();
		Double rem_hrs_extra = planH.getRemHoExt25() + planH.getRemHoExt35();
		double totComp= planH.getRemDiaFerdoLabo() + planH.getRemJorNorm() + planH.getAsigFam() + planH.getRemDiaVaca() + planH.getRemVacaVend() + rem_hrs_extra + planH.getRemFerdo();
		
		Parametro onp = data.ONP();
		Afp afp = data.nuevoAfp();
		afp.setIdAfp(1);
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODONP, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(onp);
		when(repo_afp.getOne(afp.getIdAfp())).thenReturn(afp);
		
		double resp = service.calculoDsctComSobFLu(planDTO, totComp);
		
		assertEquals(0.0, resp, 0.01);
	}

	@Test
	public void testCalculoDsctSctr() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Double resp = service.calculoDsctSctr(planDTO);
		
		assertEquals(null, resp);
	}

	/*@Test
	public void testCalculoRemGrat() throws Exception {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro gratPrd1Inicio = data.gratPrd1Inicio();
		Parametro gratPrd1Fin = data.gratPrd1Fin();
		Parametro gratPrd2Inicio = data.gratPrd2Inicio();
		Parametro gratPrd2Fin = data.gratPrd2Fin();
		
		PlanillaHistorico planH = data.nuevaPlanillaHistorico();
		planH.setIdPlanillaHistorico(1);
		
		when(repo_para.findByCodigoAndGrupo(Constantes.CODGRATIPERIODO1INI,	Constantes.GRPGLOBAL)).thenReturn(gratPrd1Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODGRATIPERIODO1FIN,	Constantes.GRPGLOBAL)).thenReturn(gratPrd1Fin);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODGRATIPERIODO2INI,	Constantes.GRPGLOBAL)).thenReturn(gratPrd2Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODGRATIPERIODO2FIN,	Constantes.GRPGLOBAL)).thenReturn(gratPrd2Fin);
		
		when(repo.obtenerPlanilla(planDTO.getPlanilla().getPdo_ano().getIdPdoAno(), 7)).thenReturn(planH);
		
		
		double resp = service.calculoRemGrat(planDTO, true);
		
		assertEquals(0, resp, 0.01);
	}*/

	@Test
	public void testCalculoCTSDefault() throws Exception {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro ctsPrd1Inicio = data.ctsPrd1Inicio();
		Parametro ctsPrd1Fin = data.ctsPrd1Fin();
		Parametro ctsPrd2Inicio = data.ctsPrd2Inicio();
		Parametro ctsPrd2Fin = data.ctsPrd2Fin();
		
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Fin);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Fin);
		
		Double resp = service.calculoCTSDefault(planDTO);
		
		assertEquals(0.0, resp , 0.01);
	}

	@Test
	public void testCalculoCTSConsCivil() throws Exception {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		PlanillaHistorico planH = data.nuevaPlanillaHistorico();
		
		Parametro ctsPrd1Inicio = data.ctsPrd1Inicio();
		Parametro ctsPrd1Fin = data.ctsPrd1Fin();
		Parametro ctsPrd2Inicio = data.ctsPrd2Inicio();
		Parametro ctsPrd2Fin = data.ctsPrd2Fin();
		
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Fin);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Fin);
		
		Double resp = service.calculoCTSConsCivil(planDTO, planH.getRemJorNorm());
		
		assertEquals(0.0, resp , 0.01);
	}

	@Test
	public void testCalculoCTSTrabHogar() throws Exception {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro ctsPrd1Inicio = data.ctsPrd1Inicio();
		Parametro ctsPrd1Fin = data.ctsPrd1Fin();
		Parametro ctsPrd2Inicio = data.ctsPrd2Inicio();
		Parametro ctsPrd2Fin = data.ctsPrd2Fin();
		
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Fin);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Fin);
		
		Double resp = service.calculoCTSTrabHogar(planDTO);
		
		assertEquals(0.0, resp , 0.01);
	}

	@Test
	public void testCalculoCTSPequeEmp() throws Exception {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro ctsPrd1Inicio = data.ctsPrd1Inicio();
		Parametro ctsPrd1Fin = data.ctsPrd1Fin();
		Parametro ctsPrd2Inicio = data.ctsPrd2Inicio();
		Parametro ctsPrd2Fin = data.ctsPrd2Fin();
		
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Fin);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Fin);
		
		Double resp = service.calculoCTSPequeEmp(planDTO);
		
		assertEquals(0.0, resp , 0.01);
	}

	@Test
	public void testCalculoCTSPesquero() throws Exception {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro ctsPrd1Inicio = data.ctsPrd1Inicio();
		Parametro ctsPrd1Fin = data.ctsPrd1Fin();
		Parametro ctsPrd2Inicio = data.ctsPrd2Inicio();
		Parametro ctsPrd2Fin = data.ctsPrd2Fin();
		Parametro ctsPesquero = data.ctsPesquero();
		
		PlanillaHistorico planH = data.nuevaPlanillaHistorico();
		
		
		Double total_comp = planH.getRemDiaFerdoLabo() + planH.getRemJorNorm() + planH.getAsigFam() + planH.getRemDiaVaca() + planH.getRemVacaVend()
				+ (planH.getRemHoExt25()+ planH.getRemHoExt35()) + planH.getRemFerdo();
		
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO1FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd1Fin);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2INI,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPERIODO2FIN,	Constantes.GRPGLOBAL)).thenReturn(ctsPrd2Fin);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODCTSPESQUERO,	Constantes.GRPGLOBAL)).thenReturn(ctsPesquero);
		
		Double resp = service.calculoCTSPesquero(planDTO, total_comp);
		
		assertEquals(0.0, resp , 0.01);
	}

	@Test
	public void testCalculo5taCategPorMes() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro uit = data.UIT();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODUIT, Constantes.GRPGLOBAL)).thenReturn(uit);
		Double resp = service.calculo5taCategPorMes(planDTO, 3, 0.0);
		
		assertEquals(0.0, resp , 0.01);
	}

	@Test
	public void testCalculo5taCateg() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro uit = data.UIT();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODUIT, Constantes.GRPGLOBAL)).thenReturn(uit);
		Double resp = service.calculo5taCateg(planDTO, 3);
		
		assertEquals(0.0, resp , 0.01);
	}

	@Test
	public void testCalculoEssaludVida() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro esSalud = data.esSalud();
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODESSALUDVIDA, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(esSalud);
		double resp = service.calculoEssaludVida(planDTO);
		
		assertEquals(0.09, resp, 0.01);
	}

	@Test
	public void testCalculoMonNocturno() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro diasComp = data.diasComputables();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diasComp);
		double resp = service.calculoMonNocturno(planDTO);
		
		assertEquals(282.09, resp, 0.01);
	}

	@Test
	public void testCalculoBonif29351() throws Exception {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro gratPrd1Inicio = data.gratPrd1Inicio();
		Parametro gratPrd1Fin = data.gratPrd1Fin();
		Parametro gratPrd2Inicio = data.gratPrd2Inicio();
		Parametro gratPrd2Fin = data.gratPrd2Fin();
		
		when(repo_para.findByCodigoAndGrupo(Constantes.CODGRATIPERIODO1INI,	Constantes.GRPGLOBAL)).thenReturn(gratPrd1Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODGRATIPERIODO1FIN,	Constantes.GRPGLOBAL)).thenReturn(gratPrd1Fin);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODGRATIPERIODO2INI,	Constantes.GRPGLOBAL)).thenReturn(gratPrd2Inicio);
		when(repo_para.findByCodigoAndGrupo(Constantes.CODGRATIPERIODO2FIN,	Constantes.GRPGLOBAL)).thenReturn(gratPrd2Fin);
		
		Parametro bonif29351 = data.bonif29351();
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODBONEXT29351, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(bonif29351);
		
		double resp = service.calculoBonif29351(planDTO);
		
		assertEquals(0, resp, 0.01);
	}

	@Test
	public void testCalculoAporEssalud() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		double a = planDTO.getContrato().getTrabajador().getEps().getAporte();
		
		
		Parametro esSalud = data.esSalud();
		
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODESSALUD, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(esSalud);
		
		double resp = service.calculoAporEssalud(planDTO, a);
		
		
		//PREGUNTAR PORQUÉ DEVUELVE NEGATIVO
		assertEquals(-19.91, resp, 0.01);	
	}

	@Test
	public void testCalculoAporEps() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		Parametro esSalud = data.esSalud();
		when(repo_para.findByCodigoAndEmpresa(Constantes.CODESSALUD, planDTO.getContrato().getTrabajador().getEmpresa())).thenReturn(esSalud);
		
		Double[] resp = service.calculoAporEps(planDTO);
		Double num = 0.09;
		
		assertEquals(num, resp[1]);	
	}

	@Test
	public void testCalculoRemDiasJusti() {
		PlanillaDTO planDTO = data.nuevoPlanillaDTO();
		
		Parametro diasComp = data.diasComputables();
		when(repo_para.findByCodigoAndGrupo(Constantes.CODDIASCOMPTBASE, Constantes.GRPGLOBAL)).thenReturn(diasComp);
		double resp = service.calculoRemDiasJusti(planDTO);
		
		assertEquals(31.0, resp, 0.01);
	}

}
