package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.controller.GenerarTxtController;
import com.mitocode.dto.CuentaCargoDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Contrato;
import com.mitocode.model.CuentaCargo;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
//@TestPropertySource(locations = "classpath:db-test.properties")
public class GenerarTxtControllerTest extends AbstractTransactionalTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	GenerarTxtController controller;

  /*@Test
  public void descargarTxtTest() throws Exception {
	  String nombre = "Pago_Enero_2020.txt";
	  controller.descargarTxt(nombre);
  }*/

  /*@Test
  public void generarGeneralTest() throws Exception {
	  CuentaCargoDTO cueCargoDTO = new CuentaCargoDTO();
	  Empresa emp = data.nuevaEmpresa();
	  emp.setIdEmpresa(1);
	  CuentaCargo cueCargo = data.nuevaCuentaCargo();
	  cueCargo.setIdCuentaCargo(3);
	  cueCargoDTO.setEmpresa(emp);
	  cueCargoDTO.setCuentaCargo(cueCargo);
	  cueCargoDTO.setDescripcion("Pago Enero 2020");
	  ResponseWrapper resp = controller.generarGeneral(cueCargoDTO);
	  Integer num = 1;
	  assertEquals(resp.getEstado(), num);
  }

  @Test
  public void generarPersonalTest() throws Exception {
	  CuentaCargoDTO cueCargoDTO = new CuentaCargoDTO();
	  Empresa emp = data.nuevaEmpresa();
	  emp.setIdEmpresa(1);
	  CuentaCargo cueCargo = data.nuevaCuentaCargo();
	  cueCargo.setIdCuentaCargo(3);
	  Contrato cont = data.nuevoContrato();
	  cont.setIdContrato(1);
	  cueCargoDTO.setEmpresa(emp);
	  cueCargoDTO.setCuentaCargo(cueCargo);
	  cueCargoDTO.setContrato(cont);
	  cueCargoDTO.setDescripcion("Pago Enero 2020");
	  ResponseWrapper resp = controller.generarPersonal(cueCargoDTO);
	  Integer num = 1;
	  assertEquals(resp.getEstado(), num);
  }*/
}
