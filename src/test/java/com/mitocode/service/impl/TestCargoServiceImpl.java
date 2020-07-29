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
public class TestCargoServiceImpl {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	/*@Autowired
	CargoServiceImpl cargo_service;

	@Test
	@Transactional
	public void testRegistrarCargo() throws Exception {
		Cargo car = data.nuevoCargo();
		car.getCategoria().setIdCategoria(1);
		
		Cargo resp = cargo_service.registrar(car);
		
		assertEquals(car.getIdCargo(), resp.getIdCargo());
	}
	
	@Test
	@Transactional
	public void testModificarCargo() throws Exception {
		Cargo car = data.nuevoCargo();
		car.getCategoria().setIdCategoria(1);
		car.setDescripcion("Doctor");
		
		Cargo resp = cargo_service.registrar(car);
		
		assertEquals("Doctor", resp.getDescripcion());
	}
	
	@Test
	@Transactional
	public void testEliminarCargo() throws Exception {
		Cargo car = data.nuevoCargo();
		car.setIdCargo(1);
		
		Boolean resp = cargo_service.eliminar(car.getIdCargo());
		
		assertEquals(false, resp);
	}
	
	@Test
	@Transactional
	public void testListarXEmpresaCargo() throws Exception {
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		List<Cargo> resp = cargo_service.listarCargoXEmpresa(emp);
		
		assertEquals(1, resp.size());
	}*/

}
