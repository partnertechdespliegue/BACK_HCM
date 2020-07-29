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

import com.mitocode.controller.AnoMesController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.service.impl.AnoMesServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestAnoMesController {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@InjectMocks
	AnoMesController controller;
	
	@Mock
	AnoMesServiceImpl service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarAñoMesInCorrecto() throws Exception{
		PdoAno pdoAno = data.nuevoPdoAno();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.registrar(pdoAno, result);
		});
	}
	
	@Test
	public void registrarAñoCorrecto() throws Exception{
		PdoAno pdoAno = data.nuevoPdoAno();
		when(service.registrar(pdoAno)).thenReturn(pdoAno);
		when(service.buscarSiExistePorDesc(pdoAno.getEmpresa(),pdoAno)).thenReturn(null);
		ResponseWrapper resp = controller.registrar(pdoAno, result);
		assertEquals((PdoAno) resp.getDefaultObj(), pdoAno);
	}
	
	@Test
	public void modificarMesInCorrecto() throws Exception{
		PdoMes pdoMes = data.nuevoPdoMes();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.actualizarMes(pdoMes, result);
		});
	}
	
	@Test
	public void modificarAnoMesCorrecto() throws Exception{
		PdoMes pdoMes = data.nuevoPdoMes();
		when(service.ModificarMes(pdoMes)).thenReturn(pdoMes);
		ResponseWrapper resp = controller.actualizarMes(pdoMes, result);
		assertEquals((PdoMes) resp.getDefaultObj(), pdoMes);
	}	
}
