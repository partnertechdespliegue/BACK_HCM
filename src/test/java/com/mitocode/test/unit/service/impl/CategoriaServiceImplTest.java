package com.mitocode.test.unit.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.mitocode.model.Empresa;

/*import com.mitocode.model.Categoria;
import com.mitocode.repo.CategoriaRepo;
import com.mitocode.service.impl.CategoriaServiceImpl;*/
import com.mitocode.util.DataDuroComplementos;


public class CategoriaServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();

	/*@InjectMocks
	CategoriaServiceImpl service;
	
	@Mock
	CategoriaRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testRegistrarCategoria() throws Exception {
		Categoria cat = data.nuevoCategoria();
		cat.getEmpresa().setIdEmpresa(1);
		
		when(repo.save(cat)).thenReturn(cat);
		Categoria resp =service.registrar(cat);
				
		assertEquals(cat, resp);
	}
	
	
	@Test
	public void testModificarCategoria() throws Exception {
		Categoria cat = data.nuevoCategoria();
		cat.getEmpresa().setIdEmpresa(1);
		cat.setDescripcion("Obrero");
		when(repo.save(cat)).thenReturn(cat);
		
		Categoria resp = service.modificar(cat);
				
		assertEquals(cat, resp);
	}
	
	@Test
	public void testEliminarCategoria() throws Exception {
		Categoria cat = data.nuevoCategoria();
		cat.setIdCategoria(1);
		
		when(repo.existsById(cat.getIdCategoria())).thenReturn(false);
		Boolean resp = service.eliminar(cat.getIdCategoria());
		
		assertEquals(false, resp);
	}
	
	@Test
	@Transactional
	public void testListarXEmpresaCategoria() throws Exception {
		Categoria cat = data.nuevoCategoria();
		cat.setIdCategoria(1);
		ArrayList<Categoria> lscat = new ArrayList<Categoria>();
		lscat.add(cat);
		lscat.add(cat);
		when(repo.findByEmpresa(cat.getEmpresa())).thenReturn(lscat);
		
		List<Categoria> resp = service.listarXEmpresa(cat.getEmpresa());
		
		assertEquals(lscat.size(), resp.size());
	}*/
}
