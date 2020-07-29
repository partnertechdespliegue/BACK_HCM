package com.mitocode.test.unit.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mitocode.model.Descuentos;
import com.mitocode.repo.DescuentosRepo;
import com.mitocode.service.impl.DescuentosServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class DescuentosServiceImplTest {
	
DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	DescuentosServiceImpl service;
	
	@Mock
	DescuentosRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testRegistrar() {
		//Descuentos dsct = data.nue
	}

	@Test
	public void testModificar() {
		fail("Not yet implemented");
	}

	@Test
	public void testLeer() {
		fail("Not yet implemented");
	}

	@Test
	public void testListar() {
		fail("Not yet implemented");
	}

	@Test
	public void testEliminar() {
		fail("Not yet implemented");
	}

	@Test
	public void testDarBaja() {
		Descuentos dsct = data.nuevoDescuento();
		dsct.setIdDescuentos(1);
		when(repo.save(dsct)).thenReturn(dsct);
		
		Descuentos resp = service.darBaja(dsct);
		
		assertEquals(dsct.getEstado(), resp.getEstado());
	}

	@Test
	public void testActivar() {
		fail("Not yet implemented");
	}

	@Test
	public void testListarXEmpresa() {
		fail("Not yet implemented");
	}

	@Test
	public void testListarXEmpresaInac() {
		fail("Not yet implemented");
	}

}
