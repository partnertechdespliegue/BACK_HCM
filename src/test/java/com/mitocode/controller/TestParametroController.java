package com.mitocode.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.model.Parametro;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestParametroController {
	
	DataDuroComplementos data = new DataDuroComplementos();

	/*@Autowired
	ParametroController parametro_controller;

	@Test
	//@Transactional
	public void testRegistrarParametro() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();

		empDTO.getEmpresa().setIdEmpresa(1);
		ResponseWrapper resp = parametro_controller.registrar(empDTO);
		
		assertEquals(empDTO.getParametro(), resp.getDefaultObj());
	}

	@Test
	@Transactional
	public void testEliminarParametro() throws Exception {
	
		Parametro par = data.nuevoParametro();
		par.setIdParametro(1);
		ResponseWrapper resp = parametro_controller.eliminar(par);
		
		assertEquals(false, resp.getDefaultObj());
	}
	
	@Test
	//@Transactional
	public void testListarParametro() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = parametro_controller.listarPorEmpresa(emp);
		
		assertEquals(10, resp.getAaData().size());
	}
	
	@Test
	//@Transactional
	public void testModificarParametro() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.getParametro().setValor("15");
		ResponseWrapper resp = parametro_controller.modficar(empDTO);
		Parametro par = (Parametro) resp.getDefaultObj();
		
		assertEquals("15", par.getValor());
	}*/
}
