package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.CentroCostoController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.CentroCosto;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestCentroCostoController extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	CentroCostoController ceCo_controller;


	/*@Test (priority = 0)
	public void testRegistrarCentroCosto() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		
		ResponseWrapper resp = ceCo_controller.insertar(empDTO);
		
		assertEquals(resp.getDefaultObj(), empDTO.getCentroCosto());
	}*/
	
	@Test (priority = 2)
	public void testEliminarCentroCosto() throws Exception {
	
		Integer id = 2;
		ResponseWrapper resp = ceCo_controller.eliminar(id);
		
		assertEquals(resp.getDefaultObj(), false);
	}
	
	@Test (priority = 3)
	public void testListarCentroCosto() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		ResponseWrapper resp = ceCo_controller.listar(emp);
		
		assertTrue(resp.getAaData().size() > 0);
	}
	
	@Test (priority = 1)
	public void testActualizarCentroCosto() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.getCentroCosto().setDescripcion("CentroCostoB");
		
		//ResponseWrapper resp = ceCo_controller.actualizar(empDTO);
		//CentroCosto ceCo = (CentroCosto) resp.getDefaultObj();
		
		//assertEquals(ceCo.getDescripcion(), "CentroCostoB");
	}

}
