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

import com.mitocode.controller.AfpController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Afp;
import com.mitocode.service.impl.AfpServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestAfpController {
	
DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	AfpController controller;
	
	@Mock
	AfpServiceImpl service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarAfpCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(service.registrar(empresadto.getAfp())).thenReturn(empresadto.getAfp());
		ResponseWrapper resp = controller.insertar(empresadto, result);
		assertEquals((Afp) resp.getDefaultObj(), empresadto.getAfp());
	}
	
	
	@Test
	public void registrarAfpInCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.insertar(empresadto, result);
		});
	}
	
	@Test
	public void modificarAfpCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(service.modificar(empresadto.getAfp())).thenReturn(empresadto.getAfp());
		ResponseWrapper resp = controller.modificar(empresadto, result);
		assertEquals((Afp) resp.getDefaultObj(), empresadto.getAfp());
	}
	
	
	@Test
	public void modificarAfpInCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.modificar(empresadto, result);
		});
	}
	
	
	
}
