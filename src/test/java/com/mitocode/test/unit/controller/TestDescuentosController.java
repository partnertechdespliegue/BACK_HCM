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

import com.mitocode.controller.DescuentosController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Descuentos;
import com.mitocode.service.impl.DescuentosServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestDescuentosController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	DescuentosController controller;
	
	@Mock
	DescuentosServiceImpl service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarDescuentosInCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.registrar(empresadto, result);
		});
	}
	
	@Test
	public void registrarDescuentosCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		Descuentos descuentos = data.nuevoDescuento();
		when(service.registrar(empresadto.getDescuentos())).thenReturn(descuentos);
		ResponseWrapper resp = controller.registrar(empresadto, result);
		assertEquals((Descuentos) resp.getDefaultObj(), descuentos);
	}
	
}
