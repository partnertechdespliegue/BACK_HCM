package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestCargoController extends AbstractTestNGSpringContextTests {

	/*DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	CargoController cargo_controller;

	@Test (priority = 0)
	public void testRegistrarCargo() throws Exception {

		CategoriaDTO catDTO = data.nuevoCategoriaDTO();
		catDTO.getCategoria().setIdCategoria(1);
		ResponseWrapper resp = cargo_controller.registrar(catDTO);

		assertEquals(resp.getDefaultObj(), catDTO.getCargo());
	}

	@Test (priority = 1)
	public void testModificarCargo() throws Exception {

		CategoriaDTO catDTO = data.nuevoCategoriaDTO();
		catDTO.getCategoria().setIdCategoria(1);
		catDTO.getCargo().setDescripcion("PROGRAMADOR");
		catDTO.getCargo().setIdCargo(1);

		//ResponseWrapper resp = cargo_controller.modifcar(catDTO);
		//Cargo car = (Cargo) resp.getDefaultObj();

		//assertEquals(car.getDescripcion(), "PROGRAMADOR");
	}

	@Test (priority = 2)
	public void testEliminarCargo() throws Exception {

		Integer id = 2;
		ResponseWrapper resp = cargo_controller.eliminar(id);

		assertEquals(resp.getDefaultObj(),  false);
	}

	@Test (priority = 3)
	public void testListarXEmpresaCargo() throws Exception {

		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = cargo_controller.listarXEmpresa(emp);

		assertTrue(resp.getAaData().size() > 0);
	}*/
}
