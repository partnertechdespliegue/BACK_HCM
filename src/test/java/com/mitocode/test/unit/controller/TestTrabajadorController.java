package com.mitocode.test.unit.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.TrabajadorController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.TrabajadorDTO;
import com.mitocode.model.Contrato;
import com.mitocode.model.Trabajador;
import com.mitocode.service.impl.ContratoServiceImpl;
import com.mitocode.service.impl.TrabajadorServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestTrabajadorController {

	DataDuroComplementos data = new DataDuroComplementos();

	@InjectMocks
	TrabajadorController controller;

	@Mock
	TrabajadorServiceImpl service;

	@Mock
	ContratoServiceImpl service_cont;

	@Mock
	BindingResult result;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRegistrar() throws Exception {
		TrabajadorDTO trabDTO = data.nuevoTrabajadorDTO();

		Trabajador trab = data.nuevoTrabajador();
		Contrato contr = data.nuevoContrato();

		when(service.registrar(Matchers.<Trabajador>any())).thenReturn(trab);
		when(service_cont.registrar(Matchers.<Contrato>any())).thenReturn(contr);

		ResponseWrapper resp = controller.registrar(trabDTO, result);

		assertEquals(trab, resp.getDefaultObj());
	}

	public void testModificar() throws Exception {
		TrabajadorDTO trabDTO = data.nuevoTrabajadorDTO();

		Trabajador trab = data.nuevoTrabajador();
		Contrato contr = data.nuevoContrato();

		when(service.modificar(Matchers.<Trabajador>any())).thenReturn(trab);
		when(service_cont.modificar(Matchers.<Contrato>any())).thenReturn(contr);

		ResponseWrapper resp = controller.modificar(trabDTO, result);

		assertEquals(trab, resp.getDefaultObj());
	}

	@Test
	public void testCrearTrabajador() {
		TrabajadorDTO trabDTO = data.nuevoTrabajadorDTO();
		Trabajador resp = controller.crearTrabajador(trabDTO);
		assertEquals(trabDTO.getTrabajador(), resp);
	}

	@Test
	public void testCrearContrato() {
		TrabajadorDTO trabDTO = data.nuevoTrabajadorDTO();
		Contrato resp = controller.crearContrato(trabDTO);
		assertEquals(trabDTO.getContrato(), resp);
	}

}
