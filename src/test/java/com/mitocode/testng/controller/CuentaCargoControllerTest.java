package com.mitocode.testng.controller;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.controller.CuentaCargoController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Banco;
import com.mitocode.model.CuentaCargo;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class CuentaCargoControllerTest extends AbstractTransactionalTestNGSpringContextTests{
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	CuentaCargoController controller;

  @Test
  public void listarEncargadoPlanTest() throws Exception {
	  
	  Empresa emp = data.nuevaEmpresa();
	  emp.setIdEmpresa(1);
	  ResponseWrapper resp = controller.listar(emp);
	  
	  assertTrue(resp.getAaData().size()>0);
  }
  
  
  @Test
  public void registrarTest() throws Exception {
	  
	  /*EmpresaDTO empDTO = new EmpresaDTO();
	  CuentaCargo cueCargo = data.nuevaCuentaCargo();
	  Empresa emp = data.nuevaEmpresa();
	  emp.setIdEmpresa(1);
	  Banco banco = data.nuevoBancoSueldo();
	  
	  empDTO.setCuentaCargo(cueCargo);
	  empDTO.setEmpresa(emp);
	  empDTO.setBanco(banco);
	  
	  ResponseWrapper resp = controller.registrar(empDTO);
	  
	  assertTrue(resp.getEstado()==1);*/
  }
  
  
 /* @Test
  public void modificarTest() throws Exception {
	  CuentaCargo cueCargo = data.nuevaCuentaCargo();
	  cueCargo.setIdCuentaCargo(4);
	  cueCargo.setDescripcion("Cargo Prueba");
	  
	  ResponseWrapper response = controller.modificar(cueCargo);
	  System.out.println(response);
	  
	  assertTrue(response.getEstado()==1);
  }*/
  
  @Test
  public void eliminarTest() throws Exception {
	  ResponseWrapper resp = controller.eliminar(4);
	  
	  assertTrue(resp.getEstado()==1);
  }
}
