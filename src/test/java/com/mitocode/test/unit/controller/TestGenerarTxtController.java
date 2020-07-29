package com.mitocode.test.unit.controller;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
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

import com.mitocode.controller.GenerarTxtController;
import com.mitocode.dto.CuentaCargoDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Contrato;
import com.mitocode.model.CuentaCargo;
import com.mitocode.model.Trabajador;
import com.mitocode.service.impl.ContratoServiceImpl;
import com.mitocode.service.impl.CuentaCargoServiceImpl;
import com.mitocode.service.impl.TrabajadorServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestGenerarTxtController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	GenerarTxtController controller;
	
	@Mock
	GenerarTxtController controller_service;
	
	@Mock
	ContratoServiceImpl contrato_service;

	@Mock
	TrabajadorServiceImpl trabajador_service;

	@Mock
	CuentaCargoServiceImpl cueCargo_service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void generarGeneral() throws Exception{
		CuentaCargoDTO cuentaCargoDTO = data.nuevaCuentaCargoDTO();
		cuentaCargoDTO.setDescripcion("test");
		CuentaCargo cuenta= data.nuevaCuentaCargo();
		List<Contrato> lsContrato = new ArrayList<>();
		lsContrato.add(data.nuevoContrato());
		lsContrato.add(data.nuevoContrato());
		lsContrato.add(data.nuevoContrato());
		MockMultipartFile mockfile = new MockMultipartFile("test", "orig", null, "mocktext para testing".getBytes());
	    Path rutaArchivo = Paths.get("src/main/resources/TxtGenerado/").resolve("test.txt").toAbsolutePath();
		Files.copy(mockfile.getInputStream(), rutaArchivo);
		File file = new File("src/main/resources/TxtGenerado/test.txt");

		when(contrato_service.listarPorEmpresayCuartaCat(cuentaCargoDTO.getEmpresa())).thenReturn(lsContrato);
		when(cueCargo_service.encontrarCueCargo(Matchers.<CuentaCargo>any())).thenReturn(cuenta);
		when(controller_service.crearTxtGeneral(cuentaCargoDTO, cuenta, lsContrato)).thenReturn(file);
		ResponseWrapper resp = controller.generarGeneral(cuentaCargoDTO, result);
		Files.delete(rutaArchivo);
		assertEquals((String)resp.getDefaultObj(),file.getName()); 
	}
	
	@Test
	public void generarPersonal() throws Exception{
		CuentaCargoDTO cuentaCargoDTO = data.nuevaCuentaCargoDTO();
		cuentaCargoDTO.setDescripcion("test");
		CuentaCargo cuenta= data.nuevaCuentaCargo();
		Contrato contrato = cuentaCargoDTO.getContrato();
		Trabajador trab = contrato.getTrabajador();
		List<Contrato> lsContrato = new ArrayList<>();
		lsContrato.add(data.nuevoContrato());
		lsContrato.add(data.nuevoContrato());
		lsContrato.add(data.nuevoContrato());
		MockMultipartFile mockfile = new MockMultipartFile("test", "orig", null, "mocktext para testing".getBytes());
	    Path rutaArchivo = Paths.get("src/main/resources/TxtGenerado/").resolve("test.txt").toAbsolutePath();
		Files.copy(mockfile.getInputStream(), rutaArchivo);
		File file = new File("src/main/resources/TxtGenerado/test_"+trab.getApePater()
								+"_"+trab.getApeMater()+"_"+trab.getNombres().replace(" ", "_")+".txt");

		when(contrato_service.listarPorEmpresayCuartaCat(cuentaCargoDTO.getEmpresa())).thenReturn(lsContrato);
		when(cueCargo_service.encontrarCueCargo(Matchers.<CuentaCargo>any())).thenReturn(cuenta);
		when(controller_service.crearTxtPersonal(cuentaCargoDTO, cuenta, lsContrato, trab, contrato)).thenReturn(file);
		ResponseWrapper resp = controller.generarPersonal(cuentaCargoDTO, result);
		Files.delete(rutaArchivo);
		assertEquals((String)resp.getDefaultObj(),file.getName()); 
	}
	
	@Test
	public void descargarTxt() throws Exception{
		 MockMultipartFile file = new MockMultipartFile("file", "orig", null, "mocktext para testing".getBytes());
		    Path rutaArchivo = Paths.get("src/main/resources/TxtGenerado").resolve("test.txt").toAbsolutePath();
			Files.copy(file.getInputStream(), rutaArchivo);
			Resource recurso = new UrlResource(rutaArchivo.toUri());
			ResponseEntity<Resource> resp = controller.descargarTxt("test.txt");
		    Files.delete(Paths.get("src/main/resources/TxtGenerado").resolve("test.txt").toAbsolutePath());
			assertEquals(resp.getBody(), recurso);
	}
}
