package com.mitocode.test.unit.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.ContratoController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Contrato;
import com.mitocode.model.Empresa;
import com.mitocode.model.Trabajador;
import com.mitocode.service.impl.ContratoServiceImpl;
import com.mitocode.service.impl.EncargadoPlanillaServiceImpl;
import com.mitocode.service.impl.TrabajadorServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestContratoController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	ContratoController controller;
	
	@Mock
	ContratoController controller_service;
	
	@Mock
	ContratoServiceImpl contrato_service;
	
	@Mock
	TrabajadorServiceImpl trabajador_service;
	
	@Mock
	EncargadoPlanillaServiceImpl encPlan_service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void generarContrato() throws Exception{
		
		Contrato contrato = data.nuevoContrato();
		Trabajador trabajador = contrato.getTrabajador();
		Empresa empresa = contrato.getTrabajador().getEmpresa();
		
		when(encPlan_service.encontrar(1)).thenReturn(data.nuevoEncargadoPlanilla());
		doNothing().when(controller_service).crearDocumento(contrato,trabajador, empresa, data.nuevoEncargadoPlanilla());
		ResponseWrapper resp = controller.generarContrato(contrato, result);
		Files.delete(Paths.get("src/main/resources/Contrato/Modelo").resolve("Contrato_"+trabajador.getNroDoc()
		+"_"+trabajador.getApePater()+"_"+trabajador.getNombres()+".docx").toAbsolutePath());
		assertEquals(resp.getMsg(), "Documento creado");
	}
	
	@Test
	public void descargarWord() throws Exception{
	    MockMultipartFile file = new MockMultipartFile("file", "orig", null, "mocktext para testing".getBytes());
	    Path rutaArchivo = Paths.get("src/main/resources/Contrato/Modelo").resolve("test.docx").toAbsolutePath();
		Files.copy(file.getInputStream(), rutaArchivo);
		Resource recurso = new UrlResource(rutaArchivo.toUri());
		ResponseEntity<Resource> resp = controller.descargarWord("test.docx");
	    Files.delete(Paths.get("src/main/resources/Contrato/Modelo").resolve("test.docx").toAbsolutePath());
		assertEquals(resp.getBody(), recurso);
	}
	
	@Test
	public void subirWordContratoNoPertenece() throws Exception{
		Trabajador trabajador = data.nuevoTrabajador();
	    MockMultipartFile file = new MockMultipartFile("file", "Contrato_NONUMBER_"+trabajador.getApePater()+"_"
	    												+trabajador.getNombres()
	    												, null, "mocktext para testing".getBytes());
	    when(trabajador_service.encontrarTrab(1)).thenReturn(trabajador);
	    ResponseWrapper resp = controller.subirWord(file, 1);
	    assertEquals((int)resp.getEstado(), 2);
	    assertEquals(resp.getMsg(), "El contrato no pertenece al trabajador");
	    
	}
	
	@Test
	public void subirWordContratoCorrecto() throws Exception{
		Trabajador trabajador = data.nuevoTrabajador();
	    MockMultipartFile file = new MockMultipartFile("file", "Contrato_"+trabajador.getNroDoc()
	    												+"_"+trabajador.getApePater()+"_"
	    												+trabajador.getNombres()+".docx"
	    												, null, "mocktext para testing".getBytes());
	    when(trabajador_service.encontrarTrab(1)).thenReturn(trabajador);
	    ResponseWrapper resp = controller.subirWord(file, 1);
	    Files.delete(Paths.get("src/main/resources/Contrato/ContratoActual").resolve("Contrato_"+trabajador.getNroDoc()
		+"_"+trabajador.getApePater()+"_"
		+trabajador.getNombres()+".docx").toAbsolutePath());
	    assertEquals((int)resp.getEstado(), 1);
	    assertEquals(resp.getMsg(), "Contrato guardado correctamente");
	    
	}
}
