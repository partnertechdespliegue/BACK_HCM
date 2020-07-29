package com.mitocode.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
//import com.mitocode.model.Categoria;
import com.mitocode.model.Empresa;
//import com.mitocode.repo.CategoriaRepo;
import com.mitocode.util.DataDuroComplementos;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCategoriaServiceImpl {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	/*@Autowired
	CategoriaServiceImpl categoria_service;

	@InjectMocks
	CategoriaServiceImpl service;
	
	@Mock
	CategoriaRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	@Transactional
	public void testRegistrarCategoria() throws Exception {
		Categoria cat = data.nuevoCategoria();
		cat.getEmpresa().setIdEmpresa(1);
		
		when(repo.save(cat)).thenReturn(cat);
		Categoria resp =service.registrar(cat);
				
		assertEquals(cat, resp);
	}
	
	
	/*
	@Test
	@Transactional
	public void testRegistrarCategoria() throws Exception {
		Categoria cat = data.nuevoCategoria();
		cat.getEmpresa().setIdEmpresa(1);
		
		Categoria resp = categoria_service.registrar(cat);
				
		assertEquals(cat.getDescripcion(), resp.getDescripcion());
	}
	
	@Test
	@Transactional
	public void testModificarCategoria() throws Exception {
		Categoria cat = data.nuevoCategoria();
		cat.getEmpresa().setIdEmpresa(1);
		cat.setDescripcion("Obrero");
		
		Categoria resp = categoria_service.registrar(cat);
				
		assertEquals("Obrero", resp.getDescripcion());
	}
	
	@Test
	@Transactional
	public void testEliminarCategoria() throws Exception {
		Categoria cat = data.nuevoCategoria();
		cat.setIdCategoria(1);
		
		Boolean resp = categoria_service.eliminar(cat.getIdCategoria());
		
		assertEquals(false, resp);
	}
	
	@Test
	@Transactional
	public void testListarXEmpresaCategoria() throws Exception {
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		List<Categoria> resp = categoria_service.listarXEmpresa(emp);
		
		assertEquals(1, resp.size());
	}*/
}
