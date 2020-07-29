package com.mitocode.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestCargoController {

	/*DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	CargoController cargo_controller;

	@Test
	// @Transactional
	public void testRegistrarCargo() throws Exception {

		CategoriaDTO catDTO = data.nuevoCategoriaDTO();
		catDTO.getCategoria().setIdCategoria(1);
		ResponseWrapper resp = cargo_controller.registrar(catDTO);

		assertEquals(catDTO.getCargo(), resp.getDefaultObj());
	}

	@Test
	// @Transactional
	public void testModificarCargo() throws Exception {

		CategoriaDTO catDTO = data.nuevoCategoriaDTO();
		catDTO.getCategoria().setIdCategoria(1);
		catDTO.getCargo().setDescripcion("PROGRAMADOR");
		catDTO.getCargo().setIdCargo(1);

		//ResponseWrapper resp = cargo_controller.modifcar(catDTO);
		//Cargo car = (Cargo) resp.getDefaultObj();

		//assertEquals("PROGRAMADOR", car.getDescripcion());
	}

	@Test
	@Transactional
	public void testEliminarCargo() throws Exception {

		Integer id = 2;
		ResponseWrapper resp = cargo_controller.eliminar(id);

		assertEquals(false, resp.getDefaultObj());
	}

	@Test
	// @Transactional
	public void testListarXEmpresaCargo() throws Exception {

		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);

		ResponseWrapper resp = cargo_controller.listarXEmpresa(emp);

		assertEquals(1, resp.getAaData().size());
	}*/

}
