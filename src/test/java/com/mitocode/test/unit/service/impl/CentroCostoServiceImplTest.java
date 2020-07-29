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

import com.mitocode.model.CentroCosto;
import com.mitocode.model.Empresa;
import com.mitocode.repo.CentroCostoRepo;
import com.mitocode.service.impl.CentroCostoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class CentroCostoServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	CentroCostoServiceImpl service;
	
	@Mock
	CentroCostoRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testRegistrar() {
		CentroCosto ceco = data.nuevoCentroCosto();
		ceco.getEmpresa().setIdEmpresa(1);
		
		when(repo.save(ceco)).thenReturn(ceco);
		CentroCosto resp = service.registrar(ceco);
		
		assertEquals(ceco, resp);
	}

	@Test
	public void testModificar() {
		CentroCosto ceco = data.nuevoCentroCosto();
		ceco.getEmpresa().setIdEmpresa(1);
		ceco.setDescripcion("CentroPrueba");
		
		when(repo.save(ceco)).thenReturn(ceco);
		CentroCosto resp = service.modificar(ceco);
		
		assertEquals(ceco, resp);
	}

	@Test
	public void testEliminar() {
		CentroCosto ceco = data.nuevoCentroCosto();
		ceco.getEmpresa().setIdEmpresa(1);
		
		when(repo.existsById(ceco.getIdCentroCosto())).thenReturn(false);
		
		Boolean resp = service.eliminar(ceco.getIdCentroCosto());
		
		assertEquals(false, resp);
	}

	@Test
	public void testListarPorEmpresa() {
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		CentroCosto ceco = data.nuevoCentroCosto();
		ceco.getEmpresa().setIdEmpresa(1);
		
		List<CentroCosto> lsceco = new ArrayList<CentroCosto>();
		lsceco.add(ceco);
		lsceco.add(ceco);
		
		when(repo.findByEmpresa(emp)).thenReturn(lsceco);
		
		List lsresp = service.listarPorEmpresa(emp);
		
		assertEquals(lsceco.size(), lsresp.size());
	}

}
