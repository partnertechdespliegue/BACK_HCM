package com.mitocode.test.unit.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mitocode.model.CuentaCargo;
import com.mitocode.model.Empresa;
import com.mitocode.repo.CuentaCargoRepo;
import com.mitocode.service.impl.CuentaCargoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class CuentaCargoServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	CuentaCargoServiceImpl service;
	
	@Mock
	CuentaCargoRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testRegistrar() {
		CuentaCargo cueCar = data.nuevaCuentaCargo();
		when(repo.save(cueCar)).thenReturn(cueCar);
		CuentaCargo resp = service.registrar(cueCar);
		
		assertEquals(resp, cueCar);
	}

	@Test
	public void testModificar() {
		CuentaCargo cueCar = data.nuevaCuentaCargo();
		cueCar.setIdCuentaCargo(1);
		cueCar.setDescripcion("Cuenta Cargo Prueba");
		when(repo.save(cueCar)).thenReturn(cueCar);
		CuentaCargo resp = service.modificar(cueCar);
		
		assertEquals(cueCar, resp);
	}

	@Test
	public void testEliminar() {
		CuentaCargo cueCar = data.nuevaCuentaCargo();
		cueCar.setIdCuentaCargo(1);

		when(repo.existsById(cueCar.getIdCuentaCargo())).thenReturn(false);
		Boolean resp = service.eliminar(cueCar.getIdCuentaCargo());
		
		assertEquals(false, resp);
	}

	@Test
	public void testListarxEmpresa() {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		CuentaCargo cueCar = data.nuevaCuentaCargo();
		cueCar.setIdCuentaCargo(1);
		
		List<CuentaCargo> lscueCar = new ArrayList<CuentaCargo>();
		lscueCar.add(cueCar);
		lscueCar.add(cueCar);

		when(repo.existsById(cueCar.getIdCuentaCargo())).thenReturn(false);
		Boolean resp = service.eliminar(cueCar.getIdCuentaCargo());
		
		assertEquals(false, resp);
	}

	@Test
	public void testEncontrarCueCargo() {
		CuentaCargo cueCar = data.nuevaCuentaCargo();
		cueCar.setIdCuentaCargo(1);
		
		when(repo.findByIdCuentaCargo(cueCar.getIdCuentaCargo())).thenReturn(cueCar);
		CuentaCargo resp = service.encontrarCueCargo(cueCar);
		
		assertEquals(cueCar, resp);
	}

}
