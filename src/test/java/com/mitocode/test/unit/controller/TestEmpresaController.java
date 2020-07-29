package com.mitocode.test.unit.controller;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.EmpresaController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Empresa;
import com.mitocode.service.impl.AnoMesServiceImpl;
import com.mitocode.service.impl.EmpresaServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestEmpresaController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	EmpresaController controller;
	
	@Mock
	EmpresaServiceImpl empresa_service;
	
	@Mock
	AnoMesServiceImpl anomes_service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarEmpresaCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		Empresa empresa = data.nuevaEmpresa();
		when(empresa_service.registrar(Matchers.<Empresa>any())).thenReturn(empresa);
		when(anomes_service.registrarAnoDefecto(empresa)).thenReturn(true);
		ResponseWrapper resp = controller.registrar(empresadto, result);
		assertEquals((Empresa)resp.getDefaultObj(), empresa);
	}
	
	@Test
	public void registrarEmpresaInCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class,()->{
			controller.registrar(empresadto, result);
		});
	}
	
	@Test
	public void modificarEmpresaCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		Empresa empresa = data.nuevaEmpresa();
		when(empresa_service.modificar(Matchers.<Empresa>any())).thenReturn(empresa);
		ResponseWrapper resp = controller.modificar(empresadto, result);
		assertEquals((Empresa)resp.getDefaultObj(), empresa);
	}
	
	@Test
	public void modificarEmpresaInCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class,()->{
			controller.modificar(empresadto, result);
		});
	}
	
}
