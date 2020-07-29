package com.mitocode.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEmpresaServiceImpl {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	EmpresaServiceImpl empresa_service;
	
	@Test
	@Transactional
	public void testRegistrarEmpresa() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		
		Empresa resp = empresa_service.registrar(emp);
		/*emp.setIdEmpresa(1);
		List<Parametro> par = parametro_service.listarPorEmpresaActivo(emp);
		System.out.println(par);*/
		
		assertEquals(emp.getIdEmpresa(), resp.getIdEmpresa());
	}
	

	@Test
	@Transactional
	public void testModificarEmpresa() throws Exception {
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		emp.setRazonSocial("Partner SAC");
		
		Empresa resp = empresa_service.modificar(emp);
		
		assertEquals ( emp.getRazonSocial(), resp.getRazonSocial() );
	}
	
	@Test
	@Transactional
	public void testListarEmpresa() throws Exception {
			
		List<Empresa> resp = empresa_service.listar();
		
		assertEquals ( 1,resp.size());
	}
	
	@Test
	@Transactional
	public void testEliminarEmpresa() throws Exception {
			
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		Boolean resp = empresa_service.eliminar(emp.getIdEmpresa());
		
		assertEquals ( false, resp);
	}
	
	

}
