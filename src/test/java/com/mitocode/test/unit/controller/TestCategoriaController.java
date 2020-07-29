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

//import com.mitocode.controller.CategoriaController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
/*import com.mitocode.model.Categoria;
import com.mitocode.service.impl.CategoriaServiceImpl;*/
import com.mitocode.util.DataDuroComplementos;

public class TestCategoriaController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	/*@InjectMocks
	CategoriaController controller;
	
	@Mock
	CategoriaServiceImpl service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarAñoMesInCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.insertar(empresadto, result);
		});
	}
	
	@Test
	public void registrarAñoCorrecto() throws Exception{
		EmpresaDTO empresadto = data.nuevaEmpresaDTO();
		Categoria categoria = empresadto.getCategoria();
		when(service.registrar(empresadto.getCategoria())).thenReturn(categoria);
		ResponseWrapper resp = controller.insertar(empresadto, result);
		assertEquals((Categoria) resp.getDefaultObj(), categoria);
	}*/
}
