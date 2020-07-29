package com.mitocode.controller;

import static org.junit.Assert.*;

import java.util.List;

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
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestEmpresaController {

	DataDuroComplementos data = new DataDuroComplementos();
	
	/*@Autowired
	EmpresaController empresa_controller;
	
	@Test
	//@Transactional
	public void testRegistrarEmpresa() throws Exception {
		
		/*EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		ResponseWrapper resp = empresa_controller.registrar(empDTO);
		Empresa emp = (Empresa) resp.getDefaultObj();
		
		assertEquals(empDTO.getEmpresa(), emp);
	}
	
	@Test
	//@Transactional
	public void testModificarEmpresa() throws Exception {
		
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.getRegTributario().setIdRegTrib(1);
		empDTO.getTipoEmpresa().setIdTipoEmp(1);
		empDTO.getEmpresa().setRazonSocial("Partner Tech");
		
		ResponseWrapper resp = empresa_controller.modificar(empDTO);
		Empresa e = (Empresa) resp.getDefaultObj();
	
		assertEquals(empDTO.getEmpresa().getRazonSocial(), e.getRazonSocial());
	}
	
	@Test
	@Transactional
	public void testEliminarEmpresa() throws Exception {
		
		ResponseWrapper resp = empresa_controller.eliminar(1);
		
		assertEquals(false, resp.getDefaultObj());
	}
	
	@Test
	//@Transactional
	public void testListarEmpresa() throws Exception {
		
		ResponseWrapper resp =  empresa_controller.listar();
		
		assertEquals(1, resp.getAaData().size());
	}*/
}
