package com.mitocode.test.unit.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.AsistenciaController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Asistencia;
import com.mitocode.service.impl.AsistenciaServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestAsistenciaController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	AsistenciaController controller;
	
	@Mock
	AsistenciaServiceImpl service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void ListarCorrecto() throws Exception{
		Asistencia asistencia = data.nuevaAsistencia();
		List<Asistencia> lsAsist = new ArrayList<>();
		lsAsist.add(data.nuevaAsistencia());
		lsAsist.add(data.nuevaAsistencia());
		lsAsist.add(data.nuevaAsistencia());
		lsAsist.add(data.nuevaAsistencia());
		when(service.buscarPorTrabajador(asistencia.getTrabajador(), asistencia.getPdoAno(),
				asistencia.getPdoMes())).thenReturn(lsAsist);
		ResponseWrapper resp = controller.listarXTrabajadorXAnoXMes(asistencia, result);
		assertTrue(resp.getAaData().size()>0);
	}
	
	@Test
	public void ListarInCorrecto() throws Exception{
		Asistencia asistencia = data.nuevaAsistencia();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class, ()->{
			controller.listarXTrabajadorXAnoXMes(asistencia, result);
		});
	}
	
	@Test
	public void buscarFechaCorrecto() throws Exception{
		Asistencia asistencia = data.nuevaAsistencia();
		when(service.buscarPorFechaYTrabajador(asistencia.getFecha()
				, asistencia.getTrabajador())).thenReturn(true);
		ResponseWrapper resp = controller.buscarFecha(asistencia, result);
		assertTrue((boolean)resp.getDefaultObj());
	}
	
	@Test
	public void buscarFechaInCorrecto() throws Exception{
		Asistencia asistencia = data.nuevaAsistencia();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class,()->{
			controller.buscarFecha(asistencia, result);
		});
	}
	
	@Test
	public void registrarAsistenciaIncorrecto(){
		Asistencia asistencia = data.nuevaAsistencia();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class,()->{
			controller.registrar(asistencia, result);
		});
	}
	
	@Test
	public void modificarAsistenciaIncorrecto(){
		Asistencia asistencia = data.nuevaAsistencia();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class,()->{
			controller.modificar(asistencia, result);
		});
	}
	
	@Test
	public void eliminarAsistenciaOk() throws Exception{
		when(service.eliminar(1)).thenReturn(true);
		ResponseWrapper resp = controller.eliminar(1);
		assertTrue((boolean)resp.getDefaultObj()); 
	}
	
	@Test
	public void eliminarAsistenciaError() throws Exception{
		when(service.eliminar(1)).thenReturn(false);
		ResponseWrapper resp = controller.eliminar(1);
		assertFalse((boolean)resp.getDefaultObj()); 
	}
	
}
