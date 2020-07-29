package com.mitocode.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Afp;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAfpServiceImpl {
	
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
		
		assertEquals(false, resp);
	}

}
