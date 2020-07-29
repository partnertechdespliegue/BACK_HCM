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
import com.mitocode.model.Parametro;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestParametroServiceImpl {
	
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
