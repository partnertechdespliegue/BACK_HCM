package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.validation.BindingResult;
import org.testng.annotations.Test;

import com.mitocode.controller.AfpController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Afp;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestAfpController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	AfpController afp_controller;
	
	@Autowired
	BindingResult result;
	
	@Test (priority = 0)
	public void testRegistrarAfp() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		ResponseWrapper resp = afp_controller.insertar(empDTO,result);
		
		assertEquals(resp.getDefaultObj(), empDTO.getAfp());
	}
	
	@Test (priority = 2)
	public void testEliminarAfp() throws Exception {
	
		Integer id = 1;
		ResponseWrapper resp = afp_controller.eliminar(id);
		assertEquals(resp.getDefaultObj(), false);
	}
	
	@Test (priority = 3)
	public void testListarAfp() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = afp_controller.listarXempresa(emp);
		
		assertTrue(resp.getAaData().size() > 0);
	}
	
	@Test (priority = 1)
	public void testModificarAfp() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.getAfp().setDescripcion("Segura");
		ResponseWrapper resp = afp_controller.modificar(empDTO,result);
		Afp afp = (Afp) resp.getDefaultObj();
		
		assertEquals(afp.getDescripcion(), "Segura");
	}

}
