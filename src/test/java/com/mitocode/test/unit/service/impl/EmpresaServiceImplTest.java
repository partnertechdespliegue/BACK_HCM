package com.mitocode.test.unit.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mitocode.model.Afp;
import com.mitocode.model.Empresa;
import com.mitocode.model.Parametro;
import com.mitocode.repo.AfpRepo;
import com.mitocode.repo.EmpresaRepo;
import com.mitocode.repo.EpsRepo;
import com.mitocode.repo.ParametroRepo;
import com.mitocode.repo.TipoPermisoRepo;
import com.mitocode.service.impl.EmpresaServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class EmpresaServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	EmpresaServiceImpl service;
	
	@Mock
	EmpresaRepo repo_emp;
	
	@Mock
	ParametroRepo repo_parm;
	
	@Mock
	AfpRepo repo_afp;
	
	@Mock
	EpsRepo repo_eps;
	
	@Mock
	TipoPermisoRepo repo_tipe;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testRegistrar() {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		Afp afp = data.nuevoAfp();
		
		Parametro para = data.nuevoParametro();
		
		when(repo_emp.save(emp)).thenReturn(emp);
		when(repo_parm.save(para)).thenReturn(para);
		when(service.guardarAfpDefecto(Matchers.<Empresa>any())).thenReturn(afp);
		
		Empresa resp = service.registrar(emp);
		
		assertEquals(emp, resp);
	}

	/*@Test
	public void testModificar() {
		fail("Not yet implemented");
	}

	@Test
	public void testListar() {
		fail("Not yet implemented");
	}

	@Test
	public void testLeer() {
		fail("Not yet implemented");
	}

	@Test
	public void testEliminar() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuscarXRuc() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuscarXRazonSocial() {
		fail("Not yet implemented");
	}*/

}
