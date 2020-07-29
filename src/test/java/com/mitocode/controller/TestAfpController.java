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
import com.mitocode.model.Afp;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")

public class TestAfpController {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	AfpController afp_controller;
	
	@Test
	//@Transactional
	public void testRegistrarAfp() throws Exception {
	
		/*EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		ResponseWrapper resp = afp_controller.insertar(empDTO);
		
		assertEquals(empDTO.getAfp(), resp.getDefaultObj());*/
	}
	
	@Test
	@Transactional
	public void testEliminarAfp() throws Exception {
	
		Integer id = 2;
		ResponseWrapper resp = afp_controller.eliminar(id);
		assertEquals(false, resp.getDefaultObj());
	}
	
	@Test
	//@Transactional
	public void testListarAfp() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = afp_controller.listarXempresa(emp);
		
		assertEquals(1, resp.getAaData().size());
	}
	
	/*@Test
	//@Transactional
	public void testModificarAfp() throws Exception {
	
		EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.getAfp().setDescripcion("Segura");
		ResponseWrapper resp = afp_controller.modificar(empDTO);
		Afp afp = (Afp) resp.getDefaultObj();
		
		assertEquals("Segura", afp.getDescripcion());
	}*/

}
