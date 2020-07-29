package com.mitocode.test.unit.controller;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.CentroCostoController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.CentroCosto;
import com.mitocode.service.impl.CentroCostoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestCentroCostosController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	CentroCostoController controller;
	
	@Mock
	CentroCostoServiceImpl service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarCentroCostoInCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.insertar(empresadto, result);
		});
	}
	
	@Test
	public void registrarCentroCostoCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		CentroCosto centrocosto = empresadto.getCentroCosto();
		when(service.registrar(empresadto.getCentroCosto())).thenReturn(centrocosto);
		ResponseWrapper resp = controller.insertar(empresadto, result);
		assertEquals((CentroCosto) resp.getDefaultObj(), centrocosto);
	}
	
}
