package com.mitocode.test.unit.service.impl;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;
import com.mitocode.model.Empresa;

/*import com.mitocode.model.Cargo;
import com.mitocode.repo.CargoRepo;
import com.mitocode.service.impl.CargoServiceImpl;*/
import com.mitocode.util.DataDuroComplementos;

public class CargoServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();

	/*@InjectMocks
	CargoServiceImpl service;
	
	@Mock
	CargoRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testRegistrarCargo() throws Exception {
		Cargo car = data.nuevoCargo();
		car.getCategoria().setIdCategoria(1);
		
		when(repo.save(car)).thenReturn(car);
		Cargo resp = service.registrar(car);
		
		assertEquals(car, resp);
	}
	
	@Test
	public void testModificarCargo() throws Exception {
		Cargo car = data.nuevoCargo();
		car.getCategoria().setIdCategoria(1);
		car.setDescripcion("Doctor");
		when(repo.save(car)).thenReturn(car);
		Cargo resp = service.modificar(car);
		
		assertEquals(car, resp);
	}
	
	@Test
	public void testEliminarCargo() throws Exception {
		Cargo car = data.nuevoCargo();
		car.setIdCargo(1);
		when(repo.existsById(car.getIdCargo())).thenReturn(false);
		Boolean resp = service.eliminar(car.getIdCargo());
		
		assertEquals(false, resp);
	}
	
	@Test
	public void testListarXEmpresaCargo() throws Exception {
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		Cargo cargo = data.nuevoCargo();
		ArrayList<Cargo> lscargo = new ArrayList<Cargo>();
		lscargo.add(cargo);
		lscargo.add(cargo);
		
		when(repo.listarCargoXEmpresa(emp.getIdEmpresa())).thenReturn(lscargo);
		List<Cargo> lsresp = service.listarCargoXEmpresa(emp);
		
		assertEquals(lscargo.size(), lsresp.size());
	}*/

}
