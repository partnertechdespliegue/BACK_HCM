package com.mitocode.testng.serviceimpl;


import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Afp;
import com.mitocode.model.Empresa;
import com.mitocode.service.impl.AfpServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestAfpServiceImpl extends AbstractTestNGSpringContextTests{
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	AfpServiceImpl afp_service;

	@Test
	@Transactional
	public void testRegistrarAfp() throws Exception {
		
		Afp afp = data.nuevoAfp();
		afp.getEmpresa().setIdEmpresa(1);
		
		Afp resp = afp_service.registrar(afp);
		
		assertEquals(afp.getDescripcion(), resp.getDescripcion());
	}
	
	@Test
	@Transactional
	public void testModificarAfp() throws Exception {
		
		Afp afp = data.nuevoAfp();
		afp.setIdAfp(1);
		afp.getEmpresa().setIdEmpresa(1);
		afp.setDescripcion("PRIMA SEGURO");
		
		Afp resp = afp_service.modificar(afp);
		
		assertEquals("PRIMA SEGURO", resp.getDescripcion());
	}
	
	@Test
	@Transactional
	public void testListarAfp() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		List<Afp> resp = afp_service.listarXEmpresa(emp);
		
		assertEquals(1, resp.size());
	}
	
	@Test
	@Transactional
	public void testEliminarAfp() throws Exception {
		
		Afp afp = data.nuevoAfp();
		afp.setIdAfp(1);
		
		Boolean resp = afp_service.eliminar(afp.getIdAfp());
		
		assertTrue(resp);
	}

}
