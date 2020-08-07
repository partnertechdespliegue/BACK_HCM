package com.mitocode.testng.serviceimpl;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Empresa;
import com.mitocode.model.Parametro;
import com.mitocode.service.impl.ParametroServiceImpl;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestParametroServiceImpl extends AbstractTestNGSpringContextTests {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	ParametroServiceImpl parametro_service;

	@Test
	@Transactional
	public void testModificarParametro() throws Exception {
	
		Parametro par = data.nuevoParametro();
		par.setIdParametro(1);
		par.setValor("10");
		par.getEmpresa().setIdEmpresa(1);
		Parametro resp = parametro_service.modificar(par);
		
		assertEquals(par.getIdParametro(), resp.getIdParametro());
	}
	
	@Test
	@Transactional
	public void testbuscarPorCodigoAndEmpresaParametro() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		List<Parametro> resp = parametro_service.listarPorEmpresaActivo(emp);
		
		assertEquals(1, resp.size());
	}

}
