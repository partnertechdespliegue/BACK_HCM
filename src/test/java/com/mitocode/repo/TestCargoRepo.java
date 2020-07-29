package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/*import com.mitocode.model.Cargo;
import com.mitocode.model.Categoria;*/
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCargoRepo {

	DataDuroComplementos data = new DataDuroComplementos();

	/*@Autowired
	CargoRepo cargo_repo;
	
	@Test
	@Transactional
	public void testSaveCargo() throws Exception {
		
		Cargo cargo = data.nuevoCargo();
		cargo.setIdCargo(1);
		cargo.getCategoria().setIdCategoria(1);
		
		Cargo resp = cargo_repo.save(cargo);
		assertEquals(cargo.getIdCargo(), resp.getIdCargo());
	}
	
	@Test
	@Transactional
	public void testExistsByIdCargo() throws Exception {
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		cargo_repo.deleteById(emp.getIdEmpresa());
		
		Boolean resp = cargo_repo.existsById(emp.getIdEmpresa());
		
		assertEquals(false, resp);
	}
	
	@Test
	@Transactional
	public void testListarCargoXEmpresaCargo() throws Exception {
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		List<Cargo> resp = cargo_repo.listarCargoXEmpresa(emp.getIdEmpresa());
		
		assertEquals(1, resp.size());
	}*/

}
