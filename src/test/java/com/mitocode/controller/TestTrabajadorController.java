package com.mitocode.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.TrabajadorDTO;
import com.mitocode.model.Contrato;
import com.mitocode.model.Empresa;
import com.mitocode.model.Trabajador;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestTrabajadorController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	TrabajadorController trab_controller;

	@Test
	//@Transactional
	public void testRegistrarTrabajador() throws Exception {
		
		/*TrabajadorDTO trabDTO = data.nuevoTrabajadorDTO();
		
		ResponseWrapper resp = trab_controller.registrar(trabDTO);
		Trabajador resp_fin = (Trabajador) resp.getDefaultObj();

		assertEquals(trabDTO.getTrabajador(), resp_fin);*/
	}
	
	@Test
	//@Transactional
	public void testModificarTrabajador() throws Exception {
		
		/*TrabajadorDTO trabDTO = data.nuevoTrabajadorDTO();
		trabDTO.getTrabajador().setIdTrabajador(1);
		trabDTO.getContrato().setIdContrato(1);
		trabDTO.getTrabajador().setNombres("Luis");
		
		ResponseWrapper resp = trab_controller.registrar(trabDTO);
		Trabajador resp_fin = (Trabajador) resp.getDefaultObj();

		assertEquals("Luis", resp_fin.getNombres());*/
	}
	
	@Test
	//@Transactional
	public void testListarPorEmpresaTrabajador() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		ResponseWrapper resp = trab_controller.listarPorEmpresa(emp);

		assertEquals(1, resp.getAaData().size());
	}
	
	@Test
	//@Transactional
	public void CrearContratoTrabajador() throws Exception {
		
		TrabajadorDTO trbDTO = data.nuevoTrabajadorDTO();
		Contrato resp = trab_controller.crearContrato(trbDTO);

		assertEquals(trbDTO.getContrato(), resp);
	}
	
	@Test
	//@Transactional
	public void CrearTrabTrabajador() throws Exception {
		
		TrabajadorDTO trbDTO = data.nuevoTrabajadorDTO();
		Trabajador resp = trab_controller.crearTrabajador(trbDTO);

		assertEquals(trbDTO.getTrabajador(), resp);
	}

}
