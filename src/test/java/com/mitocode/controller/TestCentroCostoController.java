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
import com.mitocode.model.CentroCosto;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestCentroCostoController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	/*@Autowired
	CentroCostoController ceCo_controller;


	@Test
	//@Transactional
	public void testRegistrarCentroCosto() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		
		ResponseWrapper resp = ceCo_controller.insertar(empDTO);
		
		assertEquals(empDTO.getCentroCosto(), resp.getDefaultObj());
	}
	
	@Test
	@Transactional
	public void testEliminarCentroCosto() throws Exception {
	
		Integer id = 1;
		ResponseWrapper resp = ceCo_controller.eliminar(id);
		
		assertEquals(false, resp.getDefaultObj());
	}
	
	@Test
	//@Transactional
	public void testListarCentroCosto() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		ResponseWrapper resp = ceCo_controller.listar(emp);
		
		assertEquals(1, resp.getAaData().size());
	}
	
	@Test
	//@Transactional
	public void testActualizarCentroCosto() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.getCentroCosto().setDescripcion("CentroCostoB");
		
		//ResponseWrapper resp = ceCo_controller.actualizar(empDTO);
		//CentroCosto ceCo = (CentroCosto) resp.getDefaultObj();
		
		//assertEquals("CentroCostoB", ceCo.getDescripcion());
	}*/

}
