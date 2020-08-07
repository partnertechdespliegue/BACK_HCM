package com.mitocode.testng.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.ParametroController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.model.Parametro;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestParametroController extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	ParametroController parametro_controller;

	/*@Test (priority = 0)
	public void testRegistrarParametro() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();

		empDTO.getEmpresa().setIdEmpresa(1);
		ResponseWrapper resp = parametro_controller.registrar(empDTO);
		
		assertEquals(resp.getDefaultObj(), empDTO.getParametro());
	}

	@Test (priority = 2)
	public void testEliminarParametro() throws Exception {
	
		Parametro par = data.nuevoParametro();
		par.setIdParametro(3);
		ResponseWrapper resp = parametro_controller.eliminar(par);
		
		assertEquals(resp.getDefaultObj(), false);
	}
	
	@Test (priority = 3)
	public void testListarParametro() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = parametro_controller.listarPorEmpresa(emp);
		
		assertTrue(resp.getAaData().size() > 0);
	}
	
	@Test (priority = 1)
	public void testModificarParametro() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.getParametro().setValor("15");
		ResponseWrapper resp = parametro_controller.modficar(empDTO);
		Parametro par = (Parametro) resp.getDefaultObj();
		
		assertEquals(par.getValor(), "15");
	}*/
}
