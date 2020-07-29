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
import com.mitocode.model.Parametro;
import com.mitocode.util.Constantes;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestParametroRepo {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	ParametroRepo parametro_repo;
	
	@Test
	@Transactional
	public void testSaveParmetro() throws Exception {
		Parametro par = data.nuevoParametro();
		par.setIdParametro(1);
		Parametro resp = parametro_repo.save(par);
		assertEquals( par.getIdParametro(), resp.getIdParametro());
	}
	
	@Test
	@Transactional
	public void testExistsByIdParametro() throws Exception {
		Parametro par = data.nuevoParametro();
		par.setIdParametro(1);
		parametro_repo.deleteById(par.getIdParametro());
		Boolean resp = parametro_repo.existsById(par.getIdParametro());
		assertEquals(false, resp);
	}
	
	@Test
	@Transactional
	public void testFindByEstadoAndEmpresaParametro() throws Exception {
		Parametro par = data.nuevoParametro();
		par.getEmpresa().setIdEmpresa(1);
		
		List<Parametro> resp = parametro_repo.findByEstadoAndEmpresa(par.getEstado(), par.getEmpresa());
		
		assertEquals(9, resp.size());
	}
	
	@Test
	@Transactional
	public void testFindByCodigoAndGrupoParametro() throws Exception {
		Parametro par  = parametro_repo.findByCodigoAndGrupo(Constantes.CODESSALUDVIDA,Constantes.GRPEMPRESA);
		
		assertEquals("ESSALUDVIDA", par.getCodigo());
	}
	
	@Test
	@Transactional
	public void testFindByCodigoAndEmpresaParametro() throws Exception {
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		Parametro par  = parametro_repo.findByCodigoAndEmpresa(Constantes.CODESSALUDVIDA, emp);
		
		assertEquals("ESSALUDVIDA", par.getCodigo());
	}


}
