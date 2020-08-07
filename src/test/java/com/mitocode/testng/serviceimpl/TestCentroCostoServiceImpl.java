package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.service.impl.CentroCostoServiceImpl;

import com.mitocode.model.CentroCosto;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestCentroCostoServiceImpl extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	CentroCostoServiceImpl centCos_service;

	@Test
	@Transactional
	public void testRegistrarCentroCosto() throws Exception {
		
		CentroCosto ceco = data.nuevoCentroCosto();
		ceco.getEmpresa().setIdEmpresa(1);
		
		CentroCosto resp = centCos_service.registrar(ceco);
		
		assertEquals(ceco.getDescripcion(), resp.getDescripcion());
	}
	
	@Test
	@Transactional
	public void testModificarCentroCosto() throws Exception {
		
		CentroCosto ceco = data.nuevoCentroCosto();
		ceco.setIdCentroCosto(1);
		ceco.getEmpresa().setIdEmpresa(1);
		ceco.setDescripcion("ALMACEN");
		
		CentroCosto resp = centCos_service.modificar(ceco);
		
		assertEquals("ALMACEN", resp.getDescripcion());
	}
	
	@Test
	@Transactional
	public void testEliminarCentroCosto() throws Exception {
		
		CentroCosto ceco = data.nuevoCentroCosto();
		ceco.setIdCentroCosto(1);
		
		Boolean resp = centCos_service.eliminar(ceco.getIdCentroCosto());
		
		assertTrue(resp);
	}
	
	@Test
	@Transactional
	public void testListarPorEmpresaCentroCosto() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		List<CentroCosto> resp = centCos_service.listarPorEmpresa(emp);
		
		assertEquals(1 , resp.size());
	}

}
