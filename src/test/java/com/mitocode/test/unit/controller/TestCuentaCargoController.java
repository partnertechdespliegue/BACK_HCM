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

import com.mitocode.controller.CuentaCargoController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.CuentaCargo;
import com.mitocode.service.impl.CuentaCargoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestCuentaCargoController {
	
DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	CuentaCargoController controller;
	
	@Mock
	CuentaCargoServiceImpl service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarCuentaCargoCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		CuentaCargo cuenta = data.nuevaCuentaCargo();
		when(service.registrar(empresadto.getCuentaCargo())).thenReturn(cuenta);
		ResponseWrapper resp = controller.registrar(empresadto, result);
		assertEquals((CuentaCargo)resp.getDefaultObj(), cuenta);	
	}
	
	@Test
	public void registrarCuentaCargoInCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class,()->{
			controller.registrar(empresadto, result);
		});
			
	}
	
}
