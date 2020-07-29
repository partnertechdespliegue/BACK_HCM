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

import com.mitocode.controller.EncargadoPlanillaController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.EncargadoPlanilla;
import com.mitocode.service.impl.EncargadoPlanillaServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestEncargadoPlanillaController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	EncargadoPlanillaController controller;
	
	@Mock
	EncargadoPlanillaServiceImpl service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	/*@Test
	public void registrarEncargadoPlanillaInCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.registrar(empresadto, result);
		});
	}
	
	@Test
	public void registrarEncargadoPlanillaCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		EncargadoPlanilla encargado = data.nuevoEncargadoPlanilla();
		when(service.registrar(empresadto.getEncargadoPlanilla())).thenReturn(encargado);
		ResponseWrapper resp = controller.registrar(empresadto, result);
		assertEquals((EncargadoPlanilla) resp.getDefaultObj(),encargado);
	}*/	
	
}
