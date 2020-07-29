package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.CentroCosto;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCentroCostoRepo {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	CentroCostoRepo centrocosto_repo;

	@Test
	@Transactional
	public void testSaveCentroCosto() throws Exception {
		
		CentroCosto ceco = data.nuevoCentroCosto();
		CentroCosto ceco_rest = centrocosto_repo.save(ceco);
		
		assertEquals(ceco, ceco_rest);
	}
	
	@Test
	@Transactional
	public void testFindAllCentroCosto() throws Exception {
		List<CentroCosto> ceco = centrocosto_repo.findAll();
		assertEquals(1, ceco.size());
	}
	
	@Test
	@Transactional
	public void testExistsByIdCentroCosto() throws Exception {
		CentroCosto ceco = data.nuevoCentroCosto();
		ceco.setIdCentroCosto(1);
		centrocosto_repo.deleteById(ceco.getIdCentroCosto());
		
		Boolean resp = centrocosto_repo.existsById(ceco.getIdCentroCosto());
		
		assertEquals(false, resp);
	}
	
	@Test
	@Transactional
	public void testFindByEmpresaaCentroCosto() throws Exception {
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		List<CentroCosto> resp = centrocosto_repo.findByEmpresa(emp);
		
		assertEquals(1, resp.size());
	}
}
