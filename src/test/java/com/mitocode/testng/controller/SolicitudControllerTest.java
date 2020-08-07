package com.mitocode.testng.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.validation.BindingResult;
import org.testng.annotations.Test;

import com.mitocode.controller.SolicitudController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class SolicitudControllerTest extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	SolicitudController controller;
	
	@Autowired (required = false)
	BindingResult result;

	@Test
	public void listarSolicitudxEmpresaTest() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = controller.listarSolicitudxEmpresa(emp);
		
		assertTrue(resp.getAaData().size() >= 0);
		Integer ok = 1;
		assertEquals(resp.getEstado(), ok);
	}
	
	
	@Test(expectedExceptions = ExceptionResponse.class)
	public void listarSolicitudxEmpresaTestError() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		//emp.setIdEmpresa(1);
		ResponseWrapper resp = controller.listarSolicitudxEmpresa(emp);
	}
}
