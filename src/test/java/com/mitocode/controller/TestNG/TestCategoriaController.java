package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestCategoriaController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	/*@Autowired
	CategoriaController categoria_controller;
	
	@Test (priority = 0)
	public void testRegistrarCategoria() throws Exception {
	
		/*EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		ResponseWrapper resp = categoria_controller.insertar(empDTO);
		
		assertEquals(resp.getDefaultObj(), empDTO.getCategoria());
	}
	
	@Test (priority = 2)
	public void testEliminarCategoria() throws Exception {
	
		Integer id = 2;
		ResponseWrapper resp = categoria_controller.eliminar(id);
		
		assertEquals(resp.getDefaultObj(), false);
	}
	
	@Test (priority = 3)
	public void testListarCategoria() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = categoria_controller.listar(emp);
		
		assertTrue(resp.getAaData().size() > 0);
	}
	
	@Test (priority = 1)
	public void testActualizarCategoria() throws Exception {
		
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.setCategoria(data.nuevoCategoria());
		empDTO.getCategoria().setIdCategoria(1);
		empDTO.getCategoria().setDescripcion("Obrero");
		//ResponseWrapper resp = categoria_controller.actualizar(empDTO);
		//Categoria cat = (Categoria) resp.getDefaultObj();
		
		//assertEquals(cat.getDescripcion(), "Obrero");
	}*/

}
