package com.mitocode.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestCategoriaController {

	DataDuroComplementos data = new DataDuroComplementos();

	/*@Autowired
	CategoriaController categoria_controller;
	
	@Test
	//@Transactional
	public void testRegistrarCategoria() throws Exception {
	
		/*EmpresaDTO empDTO = data.nuevaEmpresaDTO();

		empDTO.getEmpresa().setIdEmpresa(1);
		ResponseWrapper resp = categoria_controller.insertar(empDTO);
		
		assertEquals(empDTO.getCategoria(), resp.getDefaultObj());
	}
	
	@Test
	@Transactional
	public void testEliminarCategoria() throws Exception {
	
		Integer id = 1;
		ResponseWrapper resp = categoria_controller.eliminar(id);
		
		assertEquals(false, resp.getDefaultObj());
	}
	
	@Test
	//@Transactional
	public void testListarCategoria() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = categoria_controller.listar(emp);
		
		assertEquals(1, resp.getAaData().size());
	}
	
	/*@Test
	//@Transactional
	public void testActualizarCategoria() throws Exception {
		
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.setCategoria(data.nuevoCategoria());
		empDTO.getCategoria().setIdCategoria(2);
		empDTO.getCategoria().setDescripcion("Obrero");
		ResponseWrapper resp = categoria_controller.actualizar(empDTO);
		Categoria cat = (Categoria) resp.getDefaultObj();
		
		assertEquals("Obrero", cat.getDescripcion());
	}*/

}
