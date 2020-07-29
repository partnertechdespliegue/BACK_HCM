package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//import com.mitocode.model.Categoria;
import com.mitocode.model.Empresa;
import com.mitocode.model.Parametro;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCategoriaRepo {

	DataDuroComplementos data = new DataDuroComplementos();

	/*@Autowired
	CategoriaRepo categoria_repo;
	
	@Test
	@Transactional
	public void testSaveCategoria() throws Exception {
		Categoria cate = data.nuevoCategoria();
		cate.setIdCategoria(1);
		//cate.getEmpresa().setIdEmpresa(1);
		Categoria resp = categoria_repo.save(cate);
		assertEquals(cate.getIdCategoria(), resp.getIdCategoria());
	}
	
	@Test
	@Transactional
	public void testExistsByIdCategoria() throws Exception {
		
		Categoria cat = data.nuevoCategoria();
		cat.setIdCategoria(1);
		categoria_repo.deleteById(cat.getIdCategoria());
		
		Boolean resp = categoria_repo.existsById(cat.getIdCategoria());
		assertEquals(false, resp);
	}
	
	@Test
	@Transactional
	public void testFindByEmpresaCategoria() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		List<Categoria> resp = categoria_repo.findByEmpresa(emp);
		assertEquals(1, resp.size());
	}*/	

}
