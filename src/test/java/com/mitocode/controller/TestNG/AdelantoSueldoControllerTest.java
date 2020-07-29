package com.mitocode.controller.TestNG;

import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.validation.BindingResult;
import org.testng.annotations.Test;

import com.mitocode.controller.AdelantoSueldoController;
import com.mitocode.dto.AdelantoSueldoDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.AdelantoSueldo;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Trabajador;
import com.mitocode.service.AdelantoSueldoService;
import com.mitocode.service.AnoMesService;
import com.mitocode.service.TrabajadorService;

@SpringBootTest
public class AdelantoSueldoControllerTest extends AbstractTransactionalTestNGSpringContextTests {
	
	@Autowired
	AdelantoSueldoController controller;
	
	@Autowired
	TrabajadorService service_trab;
	
	@Autowired
	AnoMesService service_am;
	
	@Autowired
	AdelantoSueldoService service_as;
	
	@Autowired
	BindingResult result;

  @Test(priority = 0)
  @Transactional
  public void registrarTest() throws Exception{
	  
	  AdelantoSueldoDTO adeSueDTO = new AdelantoSueldoDTO();
	  
	  PdoAno pdoAno = service_am.encontrarAno(10);
	  PdoMes pdoMes = service_am.encontrarMes(11);
	  Trabajador trabajador =service_trab.encontrarTrab(4);
	  AdelantoSueldo adeSue = new AdelantoSueldo();
	  adeSue.setMontoTotal(2000.0);
	  adeSue.setNroCuotas(12);

	  adeSueDTO.setTrabajador(trabajador);
	  adeSueDTO.setAdelantoSueldo(adeSue);
	  adeSueDTO.setPdoAno(pdoAno);
	  adeSueDTO.setPdoMes(pdoMes);
    
	 ResponseWrapper resp = controller.registarAdeSue(adeSueDTO,result);
	 
	 assertTrue(1==resp.getEstado());
  }
  /*
  @Test
  public void listarDuedaTest() throws Exception {
	 Trabajador trabajador =service_trab.encontrarTrab(5);
	 ResponseWrapper resp = controller.listarDeuda(trabajador);
	 
	 assertTrue(resp.getAaData().size()==1);
  }
  
  @Test
  @Transactional
  public void listarAdelantoSueldoTest() throws Exception {
	 Trabajador trabajador =service_trab.encontrarTrab(5);
	 ResponseWrapper resp = controller.listarAdelantoSueldo(trabajador);
	 
	 assertTrue(resp.getAaData().size()>0);
  }
  
  @Test
  public void listarCuotasTest() throws Exception {
	 AdelantoSueldo adeSueldo = service_as.econtrarAdeSueldo(1);
	 ResponseWrapper resp = controller.listarCuotas(adeSueldo);
	 
	 assertTrue(resp.getAaData().size()==12);
  }*/
}
