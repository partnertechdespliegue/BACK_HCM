package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Afp;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAfpRepo {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	AfpRepo afp_repo;

	@Test
	@Transactional
	public void testSaveAfp () throws Exception {
		Afp afp = data.nuevoAfp();
		afp.setIdAfp(1);
		Afp resp = afp_repo.save(afp);
		assertEquals(afp.getIdAfp(), resp.getIdAfp());
	}
	
	@Test
	@Transactional
	public void testFindByEmpresaAfp () throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		List<Afp> resp = afp_repo.findByEmpresa(emp);
		
		assertEquals(1, resp.size());
	}
	
	@Test
	@Transactional
	public void testExistsByIdAfp () throws Exception {
		
		Afp afp = new Afp();
		afp.setIdAfp(1);
		afp_repo.deleteById(afp.getIdAfp());
		Boolean resp = afp_repo.existsById(afp.getIdAfp());
		
		assertEquals(false, resp);
	}
	

}
