package com.mitocode.repo;

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
public class TestEmpresa {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	EmpresaRepo empresa_repo;
	
	@Test
	@Transactional
	public void testSaveEmpresa() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		Empresa emp_resp = empresa_repo.save(emp);
		
		assertEquals(emp, emp_resp);
	}
	
	@Test
	@Transactional
	public void testSaveEmpresa2() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		Empresa emp_resp = empresa_repo.save(emp);
		assertNotNull(emp_resp);
	}
	
	@Test
	@Transactional
	public void testFindAllEmpresa() throws Exception {
		
		List <Empresa> emp_resp = empresa_repo.findAll();
		
		assertEquals(1, emp_resp.size());
	}
	
	@Test
	@Transactional
	public void testExistsByIdEmpresa() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		empresa_repo.deleteById(emp.getIdEmpresa());
		
		Boolean emp_res = empresa_repo.existsById(emp.getIdEmpresa());
		
		assertEquals(false, emp_res);
	}
	
	@Test
	@Transactional
	public void testFindByRucEmpresa() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		Empresa emp_res = empresa_repo.findByRuc(emp.getRuc());
		
		assertEquals(emp.getIdEmpresa(), emp_res.getIdEmpresa());
	}
	
	@Test
	@Transactional
	public void testFindByRazonSocialEmpresa() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		Empresa emp_res = empresa_repo.findByRazonSocial(emp.getRazonSocial());
		
		assertEquals(emp.getIdEmpresa(), emp_res.getIdEmpresa());
	}
	
	

}
