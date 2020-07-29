package com.mitocode.test.unit.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.RheController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Rhe;
import com.mitocode.model.Trabajador;
import com.mitocode.service.impl.AnoMesServiceImpl;
import com.mitocode.service.impl.RheServiceImpl;
import com.mitocode.service.impl.TrabajadorServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestRheController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	RheController controller;
	
	@Mock
	RheServiceImpl service;
	
	@Mock
	TrabajadorServiceImpl service_trab;
	
	@Mock
	AnoMesServiceImpl service_anoMes;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

	
	@Test
	public void testRegistrarCorrecto() throws Exception {
		Trabajador trab = data.nuevoTrabajador();
		trab.setIdTrabajador(1);
		trab.setNroDoc("12345678912");
		PdoAno pdoAno = data.nuevoPdoAno();
		PdoMes pdoMes = data.nuevoPdoMes();
		
		Rhe rhe = data.nuevoRhe();
		rhe.setTrabajador(trab);
		rhe.setPdoAno(pdoAno);
		rhe.setPdoMes(pdoMes);
		rhe.setIdRhe(1);
		
		MockMultipartFile file = new MockMultipartFile("file", trab.getNroDoc()+"-R01-E001-7.pdf",null, "mocktext para testing".getBytes());
		
		when(service_trab.encontrarTrab(1)).thenReturn(trab);
		when(service_anoMes.encontrarAno(8)).thenReturn(pdoAno);
		when(service_anoMes.encontrarMes(1)).thenReturn(pdoMes);
		when(service.registrar(Matchers.<Rhe>any())).thenReturn(rhe);
		
		ResponseWrapper resp = controller.registrar(file, 1, 8, 1);
		
		File borrar = new File("src/main/resources/DocumentosRHE/RHE/HRE_E001-7_"+trab.getNroDoc()+"_ENE_2022.pdf");
		borrar.delete();
		
		assertEquals((int)resp.getEstado(), 1);
	    assertEquals(resp.getMsg(), "RHE registrado correctamente");
		
	}
	
	@Test
	public void testErrorRegistrarNoPerteneceRHE() throws Exception {
		Trabajador trab = data.nuevoTrabajador();
		trab.setIdTrabajador(1);
		trab.setNroDoc("12345678912");
		PdoAno pdoAno = data.nuevoPdoAno();
		PdoMes pdoMes = data.nuevoPdoMes();
		
		Rhe rhe = data.nuevoRhe();
		rhe.setTrabajador(trab);
		rhe.setPdoAno(pdoAno);
		rhe.setPdoMes(pdoMes);
		rhe.setIdRhe(1);
		
		MockMultipartFile file = new MockMultipartFile("file", "98765432198"+"-R01-E001-1.pdf",null, "mocktext para testing".getBytes());
		
		when(service_trab.encontrarTrab(1)).thenReturn(trab);
		when(service_anoMes.encontrarAno(8)).thenReturn(pdoAno);
		when(service_anoMes.encontrarMes(1)).thenReturn(pdoMes);
		when(service.registrar(Matchers.<Rhe>any())).thenReturn(rhe);
		
		ResponseWrapper resp = controller.registrar(file, 1, 8, 1);
		
		assertEquals((int)resp.getEstado(), 2);
	    assertEquals(resp.getMsg(), "Este recibo por honorario no pertenece al trabajador");
		
	}
	
	@Test
	public void testErrorRegistradoAnteriormente() throws Exception {
		Trabajador trab = data.nuevoTrabajador();
		trab.setIdTrabajador(1);
		trab.setNroDoc("10726986130");
		PdoAno pdoAno = data.nuevoPdoAno();
		PdoMes pdoMes = data.nuevoPdoMes();
		
		Rhe rhe = data.nuevoRhe();
		rhe.setNombreArchivo("HRE_E001-7_10726986130-R01-E001-7.pdf");
		rhe.setTrabajador(trab);
		rhe.setPdoAno(pdoAno);
		rhe.setPdoMes(pdoMes);
		rhe.setIdRhe(1);
		
		List<Rhe> lsrhe = new ArrayList<Rhe>();
		lsrhe.add(rhe);
		
		MockMultipartFile file = new MockMultipartFile("file", "10726986130-R01-E001-7.pdf",null, "mocktext para testing".getBytes());
		
		when(service_trab.encontrarTrab(1)).thenReturn(trab);
		when(service_anoMes.encontrarAno(8)).thenReturn(pdoAno);
		when(service_anoMes.encontrarMes(1)).thenReturn(pdoMes);
		when(service.registrar(Matchers.<Rhe>any())).thenReturn(rhe);
		when(service.encontrarXTrab(trab)).thenReturn(lsrhe);
		
		ResponseWrapper resp = controller.registrar(file, 1, 8, 1);
		
		File borrar = new File("src/main/resources/DocumentosRHE/RHE/HRE_E001-7_10726986130-R01-E001-7.pdf");
		if (borrar.exists()) {
			borrar.delete();
		}
		
		assertEquals((int)resp.getEstado(), 2);
	    assertEquals(resp.getMsg(), "Este recibo por honorario ya ha sido registrado anteriormente");
		
	}

	@Test
	public void testEliminar() throws Exception {
		Trabajador trab = data.nuevoTrabajador();
		trab.setIdTrabajador(1);
		trab.setNroDoc("10726986130");
		PdoAno pdoAno = data.nuevoPdoAno();
		PdoMes pdoMes = data.nuevoPdoMes();
		
		Rhe rhe = data.nuevoRhe();
		rhe.setNombreArchivo("HRE_E001-7_10726986130-R01-E001-7.pdf");
		rhe.setTrabajador(trab);
		rhe.setPdoAno(pdoAno);
		rhe.setPdoMes(pdoMes);
		rhe.setIdRhe(1);
		
		when(service.encontrarRhe(1)).thenReturn(rhe);
		when(service.eliminar(1)).thenReturn(true);
		
		ResponseWrapper resp = controller.eliminar(1);
		
		assertEquals((int)resp.getEstado(), 1);
	    assertEquals(resp.getMsg(), "RHE eliminado correctamente");
	}

	@Test
	public void testDescargarRhe() throws Exception {
		
		MockMultipartFile file = new MockMultipartFile("file", "HRE_E001-7_10726986130_ENE_2023.pdf", null, "mocktext para testing".getBytes());
	    Path rutaArchivo = Paths.get("src/main/resources/DocumentosRHE/RHE").resolve("HRE_E001-7_10726986130_ENE_2023.pdf").toAbsolutePath();
		Files.copy(file.getInputStream(), rutaArchivo);
		Resource recurso = new UrlResource(rutaArchivo.toUri());

		ResponseEntity<Resource> resp = controller.descargarRhe("HRE_E001-7_10726986130_ENE_2023.pdf");
		
		File borrar = new File("src/main/resources/DocumentosRHE/RHE/HRE_E001-7_10726986130_ENE_2023.pdf");
		borrar.delete();
		
		assertEquals(recurso, resp.getBody()); 
	}

}
