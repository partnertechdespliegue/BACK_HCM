package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.validation.BindingResult;
import org.testng.annotations.Test;

import com.mitocode.controller.EmpresaController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestEmpresaController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	EmpresaController empresa_controller;
	
	@Autowired
	BindingResult result;
	
	@Test (priority = 0)
	public void testRegistrarEmpresa() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		ResponseWrapper resp = empresa_controller.registrar(empDTO, result);
		Empresa emp = (Empresa) resp.getDefaultObj();
		
		assertEquals(empDTO.getEmpresa(), emp);
	}
	
	@Test (priority = 1)
	public void testModificarEmpresa() throws Exception {
		
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.getRegTributario().setIdRegTrib(1);
		empDTO.getTipoEmpresa().setIdTipoEmp(1);
		empDTO.getEmpresa().setRazonSocial("Partner Tech");
		
		ResponseWrapper resp = empresa_controller.modificar(empDTO,result);
		Empresa e = (Empresa) resp.getDefaultObj();
	
		assertEquals(empDTO.getEmpresa().getRazonSocial(), e.getRazonSocial());
	}
	
	@Test (priority = 3)
	public void testEliminarEmpresa() throws Exception {

		ResponseWrapper resp = empresa_controller.eliminar(1);
		
		assertEquals(resp.getDefaultObj(), false);
	}
	
	
	@Test (priority = 2)
	public void testListarEmpresa() throws Exception {
		
		ResponseWrapper resp =  empresa_controller.listar();
		
		assertEquals(1, resp.getAaData().size());
	}
}
