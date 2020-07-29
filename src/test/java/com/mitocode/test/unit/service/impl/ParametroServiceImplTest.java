package com.mitocode.test.unit.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mitocode.model.Parametro;
import com.mitocode.repo.ParametroRepo;
import com.mitocode.service.impl.ParametroServiceImpl;
import com.mitocode.util.Constantes;
import com.mitocode.util.DataDuroComplementos;

public class ParametroServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	ParametroServiceImpl service;
	
	@Mock
	ParametroRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }


	@Test
	public void testModificar() {
		
		Parametro para = data.nuevoParametro();
		para.setIdParametro(1);
		when(repo.save(para)).thenReturn(para);
		
		Parametro resp = service.modificar(para);
		
		assertEquals(para, resp);
	}

	@Test
	public void testModificarParametroNoTardanza() throws Exception {
		Parametro parametro = data.nuevoParametro();
		when(repo.save(parametro)).thenReturn(parametro);
		Parametro resp = service.modificar(parametro);
		assertEquals(parametro, resp);
	}
	
	@Test
	public void testModificarParametroTardDias() throws Exception{
		Parametro param_dias = data.nuevoParametro();
		param_dias.setCodigo("TIPTARD");
		param_dias.setValor("1");
		
		Parametro cntDia = data.nuevoParametro();
		cntDia.setCodigo("TARCNTDIAS");
		cntDia.setNombre("Cantidad Días");
		
		Parametro tpRango = data.nuevoParametro();
		tpRango.setCodigo("TIPORANGO");
		tpRango.setNombre("Tipo Rango");
		
		when(repo.findByCodigoAndEmpresa(Constantes.CODTARCNTDIAS, param_dias.getEmpresa())).thenReturn(cntDia);
		when(repo.findByCodigoAndEmpresa(Constantes.CODTIPORANGO, param_dias.getEmpresa())).thenReturn(tpRango);
		when(repo.save(param_dias)).thenReturn(param_dias);
		
		Parametro resp = service.modificar(param_dias);
		
		assertEquals(param_dias, resp);
	}
	
}
