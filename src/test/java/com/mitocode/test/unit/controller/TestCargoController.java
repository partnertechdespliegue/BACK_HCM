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

/*import com.mitocode.controller.CargoController;
import com.mitocode.dto.CategoriaDTO;*/
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
/*import com.mitocode.model.Cargo;
import com.mitocode.service.impl.CargoServiceImpl;*/
import com.mitocode.util.DataDuroComplementos;

public class TestCargoController {
	
	DataDuroComplementos data = new DataDuroComplementos();

	/*@InjectMocks
	CargoController controller;
	
	@Mock
	CargoServiceImpl service;
	
	@Mock
	BindingResult result;

	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarCargoInCorrecto() throws Exception{
		CategoriaDTO categoriadto = data.nuevoCategoriaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.registrar(categoriadto, result);
		});
	}
	
	@Test
	public void registrarCargoCorrecto() throws Exception{
		CategoriaDTO categoriadto = data.nuevoCategoriaDTO();
		Cargo cargo = categoriadto.getCargo();
		when(service.registrar(categoriadto.getCargo())).thenReturn(cargo);
		ResponseWrapper resp = controller.registrar(categoriadto, result);
		assertEquals((Cargo) resp.getDefaultObj(), cargo);
	}*/
	
}
