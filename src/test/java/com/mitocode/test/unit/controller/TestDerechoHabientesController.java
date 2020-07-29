package com.mitocode.test.unit.controller;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.DerechoHabientesController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.TrabajadorDTO;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.DerechoHabientes;
import com.mitocode.model.Trabajador;
import com.mitocode.service.impl.DerechoHabientesServiceImpl;
import com.mitocode.service.impl.TrabajadorServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestDerechoHabientesController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	DerechoHabientesController controller;
	
	@Mock
	DerechoHabientesServiceImpl derecho_service;
	
	@Mock
	TrabajadorServiceImpl trabajador_service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarDerechoHabientesCorrecto() throws Exception{
		TrabajadorDTO trabajadordto = data.nuevoTrabajadorDTO();
		DerechoHabientes derecho = data.nuevoDerechoHabiente();
		when(derecho_service.registrar(trabajadordto.getDerechoHabientes())).thenReturn(derecho);
		ResponseWrapper resp = controller.registrar(trabajadordto, result);
		assertEquals((DerechoHabientes)resp.getDefaultObj(), derecho);	
	}
	
	@Test
	public void registrarDerechoHabientesInCorrecto() throws Exception{
		TrabajadorDTO trabajadordto = data.nuevoTrabajadorDTO();
		when(result.hasErrors()).thenReturn(true);
		assertThrows(ExceptionResponse.class,()->{
			controller.registrar(trabajadordto, result);
		});	
	}
	
	@Test
	public void subirArchivoYaExiste()throws Exception{
		DerechoHabientes derechoH = data.nuevoDerechoHabiente();
	    MockMultipartFile file = new MockMultipartFile("file","orig", null, "mocktext para testing".getBytes());
	    when(derecho_service.encontrarDH(1)).thenReturn(derechoH);
	    ResponseWrapper resp = controller.subirArchivo(file, 1);
	    assertEquals((int)resp.getEstado(), 2);
	    assertEquals(resp.getMsg(), "Ya hay un archivo registrado");
	}
	
	@Test
	public void subirArchivoCorrectoCY() throws Exception{
		Trabajador trabajador = data.nuevoTrabajador();
		DerechoHabientes derechoH = data.nuevoDerechoHabiente();
		derechoH.setNombreArchivo(null);
	    MockMultipartFile file = new MockMultipartFile("file", "test.docx"
	    												, null, "mocktext para testing".getBytes());
	    when(derecho_service.encontrarDH(1)).thenReturn(derechoH);
	    when(trabajador_service.encontrarTrab(1)).thenReturn(trabajador);
	    ResponseWrapper resp = controller.subirArchivo(file, 1);
	    Files.delete(Paths.get("src/main/resources/DerechoHabiente/CY").resolve(trabajador.getNroDoc() + "_DH_" + derechoH.getApellido() + "_"
				+ derechoH.getNombre()+".docx").toAbsolutePath());
	    assertEquals((int)resp.getEstado(), 1);
	    assertEquals(resp.getMsg(), "Archivo registrado");
	}
	
	@Test
	public void subirArchivoCorrectoHIC() throws Exception{
		Trabajador trabajador = data.nuevoTrabajador();
		DerechoHabientes derechoH = data.nuevoDerechoHabiente();
		derechoH.setNombreArchivo(null);
		derechoH.setIdTipoDerechoHabiente(2);
	    MockMultipartFile file = new MockMultipartFile("file", "test.docx"
	    												, null, "mocktext para testing".getBytes());
	    when(derecho_service.encontrarDH(1)).thenReturn(derechoH);
	    when(trabajador_service.encontrarTrab(1)).thenReturn(trabajador);
	    ResponseWrapper resp = controller.subirArchivo(file, 1);
	    Files.delete(Paths.get("src/main/resources/DerechoHabiente/HIC").resolve(trabajador.getNroDoc() + "_DH_" + derechoH.getApellido() + "_"
				+ derechoH.getNombre()+".docx").toAbsolutePath());
	    assertEquals((int)resp.getEstado(), 1);
	    assertEquals(resp.getMsg(), "Archivo registrado");
	}
	
	@Test
	public void subirArchivoCorrectoMG() throws Exception{
		Trabajador trabajador = data.nuevoTrabajador();
		DerechoHabientes derechoH = data.nuevoDerechoHabiente();
		derechoH.setNombreArchivo(null);
		derechoH.setIdTipoDerechoHabiente(3);
	    MockMultipartFile file = new MockMultipartFile("file", "test.docx"
	    												, null, "mocktext para testing".getBytes());
	    when(derecho_service.encontrarDH(1)).thenReturn(derechoH);
	    when(trabajador_service.encontrarTrab(1)).thenReturn(trabajador);
	    ResponseWrapper resp = controller.subirArchivo(file, 1);
	    Files.delete(Paths.get("src/main/resources/DerechoHabiente/MG").resolve(trabajador.getNroDoc() + "_DH_" + derechoH.getApellido() + "_"
				+ derechoH.getNombre()+".docx").toAbsolutePath());
	    assertEquals((int)resp.getEstado(), 1);
	    assertEquals(resp.getMsg(), "Archivo registrado");
	}
	
}
