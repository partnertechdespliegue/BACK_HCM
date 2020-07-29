package com.mitocode.test.unit.controller;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import com.mitocode.controller.AdelantoSueldoController;
import com.mitocode.dto.AdelantoSueldoDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.AdelantoSueldo;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.service.impl.AdelantoSueldoServiceImpl;
import com.mitocode.service.impl.AnoMesServiceImpl;
import com.mitocode.service.impl.CuotaAdelantoServiceImpl;
import com.mitocode.service.impl.TrabajadorServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestAdelantoSueldoController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	AdelantoSueldoController controller;
	
	@Mock
	AdelantoSueldoController controller_service;
	
	@Mock
	AdelantoSueldoServiceImpl adelanto_service;
	
	@Mock
	CuotaAdelantoServiceImpl cuota_service;
	
	@Mock
	AnoMesServiceImpl anomes_service;
	
	@Mock
	TrabajadorServiceImpl trab_service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void registrarAdeSueldoCorrecto() throws Exception{
		AdelantoSueldoDTO adelSueld = data.nuevoAdelantoSueldoDTO();
		PdoAno pdoAno = data.nuevoPdoAno();
		pdoAno.setDescripcion(2021);
		PdoMes pdoMes = data.nuevoPdoMes();
		pdoMes.setIdPdoMes(12);
		ResponseWrapper aux = new ResponseWrapper();
		aux.setEstado(1);
		aux.setMsg("Adelanto sueldo registrado correctamente");
		aux.setDefaultObj(adelSueld.getAdelantoSueldo());
		when(anomes_service.encontrarAno(Matchers.<Integer>any())).thenReturn(pdoAno);
		when(anomes_service.encontrarMes(Matchers.<Integer>any())).thenReturn(pdoMes);
		when(adelanto_service.registrar(Matchers.<AdelantoSueldo>any())).thenReturn(adelSueld.getAdelantoSueldo());
		when(controller_service.registarCuoAde(Matchers.<AdelantoSueldo>any(),Matchers.<PdoAno>any(),Matchers.<PdoMes>any())).thenReturn(aux);
		
		ResponseWrapper resp = controller.registarAdeSue(adelSueld, result);
		assertEquals(resp.getDefaultObj(), aux.getDefaultObj());	
		assertEquals(resp.getEstado(), aux.getEstado());
	}
	
	@Test
	public void registrarAdeSueldoErrorAñoAnterior() throws Exception{
		AdelantoSueldoDTO adelSueld = data.nuevoAdelantoSueldoDTO();
		PdoAno pdoAno = data.nuevoPdoAno();
		pdoAno.setDescripcion(2015);
		PdoMes pdoMes = data.nuevoPdoMes();
		pdoMes.setIdPdoMes(12);
		when(anomes_service.encontrarAno(Matchers.<Integer>any())).thenReturn(pdoAno);
		
		ResponseWrapper resp = controller.registarAdeSue(adelSueld, result);
		assertEquals(resp.getMsg(),"Seleccione un periodo actual o posterior");	
	}
	
	@Test
	public void registrarAdeSueldoErrorMesAnterior() throws Exception{
		AdelantoSueldoDTO adelSueld = data.nuevoAdelantoSueldoDTO();
		PdoAno pdoAno = data.nuevoPdoAno();
		pdoAno.setDescripcion(2020);
		PdoMes pdoMes = data.nuevoPdoMes();
		pdoMes.setIdPdoMes(1);
		when(anomes_service.encontrarAno(Matchers.<Integer>any())).thenReturn(pdoAno);
		when(anomes_service.encontrarMes(Matchers.<Integer>any())).thenReturn(pdoMes);
		ResponseWrapper resp = controller.registarAdeSue(adelSueld, result);
		assertEquals(resp.getMsg(),"Seleccione un periodo actual o posterior");	
	}
	
	@Test
	public void subirArchivoCorrecto() throws Exception{
	    MockMultipartFile file = new MockMultipartFile("file", "orig", null, "mocktext para testing".getBytes());
	    AdelantoSueldo adel = data.nuevoAdelantoSueldo();
	    adel.setIdAdelantoSueldo(1);
	    when(adelanto_service.econtrarAdeSueldo(1)).thenReturn(adel);
	    when(controller_service.RutearArchivo(adel,adel.getTrabajador(), file)).thenReturn("rutaArchivo");
	    when(adelanto_service.modificar(Matchers.<AdelantoSueldo>any())).thenReturn(adel);
	    ResponseWrapper resp = controller.subirArchivo(file, 1);
	    Files.delete(Paths.get("src/main/resources/Adelanto").resolve("ACU_ADEL_1_72868572_01_01_1970.docx").toAbsolutePath());
	    assertEquals((AdelantoSueldo) resp.getDefaultObj(), adel);
	}
	
	
	
	
	
}
