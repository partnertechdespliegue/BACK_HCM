package com.mitocode.test.unit.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.SuspencionController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Suspencion;
import com.mitocode.model.Trabajador;
import com.mitocode.service.impl.SuspencionServiceImpl;
import com.mitocode.service.impl.TrabajadorServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestSuspencionController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	SuspencionController controller;
	
	@Mock
	SuspencionServiceImpl service;
	
	@Mock
	TrabajadorServiceImpl service_trab;
	
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testSubirArchivo() throws Exception {
		
		Trabajador trab = data.nuevoTrabajador();
		Suspencion susp = new Suspencion();
		Timestamp hoy = new Timestamp(new Date().getTime());
		susp.setIdSuspencion(1);
		susp.setFechaRegistro(hoy);
		susp.setNombreArchivo("");
		susp.setTrabajador(trab);
		
		when(service_trab.encontrarTrab(1)).thenReturn(trab);
		when(service.encontrarSusp(trab)).thenReturn(susp);
		
		MockMultipartFile file = new MockMultipartFile("file", "Felicidad.pdf",null, "mocktext para testing".getBytes());

				
		ResponseWrapper resp = controller.subirArchivo(file, 1);
		
		//File borrar = new File("src/main/resources/DocumentosRHE/Suspenciones/SUSP_72868572_05_03_2020.pdf");
		//borrar.delete();
		
		assertEquals((int)resp.getEstado(), 2);
	    assertEquals("La suspención aún esta vigente para el año 2020", resp.getMsg());
		
	}
	
	
	@Test
	public void testSubirArchivoAntesTiempo() throws Exception {
		
		Trabajador trab = data.nuevoTrabajador();
		Suspencion susp = new Suspencion();
		Timestamp hoy = new Timestamp(new Date().getTime());
		susp.setIdSuspencion(1);
		susp.setFechaRegistro(hoy);
		susp.setNombreArchivo("SUSP_72868572_05_03_2020.pdf");
		susp.setTrabajador(trab);
		
		when(service_trab.encontrarTrab(1)).thenReturn(trab);
		when(service.encontrarSusp(trab)).thenReturn(susp);
		
		MockMultipartFile file = new MockMultipartFile("file", "Feliz.pdf",null, "mocktext para testing".getBytes());

				
		ResponseWrapper resp = controller.subirArchivo(file, 1);
		
		assertEquals(2, (int)resp.getEstado());
	    assertEquals("La suspención aún esta vigente para el año 2020", resp.getMsg());
		
	}

}
