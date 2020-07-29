package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.controller.TrabajadorController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.TrabajadorDTO;
import com.mitocode.model.Contrato;
import com.mitocode.model.Empresa;
import com.mitocode.model.Trabajador;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestTrabajadorController extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	TrabajadorController trab_controller;

	@Test (priority = 0)
	public void testRegistrarTrabajador() throws Exception {
		
		TrabajadorDTO trabDTO = data.nuevoTrabajadorDTO();
		
		ResponseWrapper resp = trab_controller.registrar(trabDTO, null);
		Trabajador resp_fin = (Trabajador) resp.getDefaultObj();

		assertEquals(resp_fin, trabDTO.getTrabajador());
	}
	
	@Test (priority = 1)
	public void testModificarTrabajador() throws Exception {
		
		/*TrabajadorDTO trabDTO = data.nuevoTrabajadorDTO();
		trabDTO.getTrabajador().setIdTrabajador(1);
		trabDTO.getContrato().setIdContrato(1);
		trabDTO.getTrabajador().setNombres("Luis");
		
		ResponseWrapper resp = trab_controller.modificar(trabDTO);
		Trabajador resp_fin = (Trabajador) resp.getDefaultObj();

		assertEquals(resp_fin.getNombres(), "Luis");*/
	}
	
	@Test (priority = 2)
	public void testListarPorEmpresaTrabajador() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = trab_controller.listarPorEmpresa(emp);

		assertTrue(resp.getAaData().size() > 0);
	}
	
	@Test (priority = 3)
	public void CrearContratoTrabajador() throws Exception {
		
		TrabajadorDTO trbDTO = data.nuevoTrabajadorDTO();
		Contrato resp = trab_controller.crearContrato(trbDTO);

		assertEquals(resp, trbDTO.getContrato());
	}
	
	@Test (priority = 4)
	public void CrearTrabTrabajador() throws Exception {
		
		TrabajadorDTO trbDTO = data.nuevoTrabajadorDTO();
		Trabajador resp = trab_controller.crearTrabajador(trbDTO);

		assertEquals(resp, trbDTO.getTrabajador());
	}

}
