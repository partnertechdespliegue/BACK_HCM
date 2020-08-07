package com.mitocode.junit.serviceimpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mitocode.model.DerechoHabientes;
import com.mitocode.model.Trabajador;
import com.mitocode.repo.DerechoHabientesRepo;
import com.mitocode.service.impl.DerechoHabientesServiceImpl;
import com.mitocode.util.Constantes;
import com.mitocode.util.DataDuroComplementos;

public class DerechoHabientesServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	DerechoHabientesServiceImpl service;
	
	@Mock
	DerechoHabientesRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }


	@Test
	public void testRegistrar() {
		DerechoHabientes derHab = data.nuevoDerechoHabiente();
		when(repo.save(derHab)).thenReturn(derHab);
		DerechoHabientes resp = service.registrar(derHab);
		
		assertEquals(resp, derHab);
	}

	@Test
	public void testModificar() {
		DerechoHabientes derHab = data.nuevoDerechoHabiente();
		derHab.setIdDerechoHabiente(1);
		derHab.setNombre("FULANO");
		when(repo.save(derHab)).thenReturn(derHab);
		DerechoHabientes resp = service.modificar(derHab);
		
		assertEquals(resp, derHab);
	}

	/*@Test
	public void testListarXTrab() {
		
		Trabajador trab = data.nuevoTrabajador();
		trab.setIdTrabajador(1);
		DerechoHabientes derHab = data.nuevoDerechoHabiente();
		derHab.setIdDerechoHabiente(1);
		List<DerechoHabientes> lsderHab = new ArrayList<DerechoHabientes>();
		lsderHab.add(derHab);
		lsderHab.add(derHab);
		when(repo.findByEstadoAndTrabajador(Constantes.ConsActivo, trab)).thenReturn(lsderHab);
		List<DerechoHabientes> lsresp = service.listarXTrab(trab);
		
		assertEquals(lsresp, lsderHab);
	}*/

	@Test
	public void testEncontrarDH() {
		DerechoHabientes derHab = data.nuevoDerechoHabiente();
		derHab.setIdDerechoHabiente(1);
		when(repo.findByIdDerechoHabiente(derHab.getIdDerechoHabiente())).thenReturn(derHab);
		DerechoHabientes resp = service.encontrarDH(derHab.getIdDerechoHabiente());
		
		assertEquals(resp, derHab);
	}

}
