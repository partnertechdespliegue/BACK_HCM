package com.mitocode.test.unit.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.mitocode.model.Afp;
import com.mitocode.model.Empresa;
import com.mitocode.repo.AfpRepo;
import com.mitocode.service.impl.AfpServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class AfpServiceImplTest {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	AfpServiceImpl service;
	
	@Mock
	AfpRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testRegistrarAfp() throws Exception {
		
		Afp afp = data.nuevoAfp();
		afp.getEmpresa().setIdEmpresa(1);
		when(repo.save(afp)).thenReturn(afp);
		Afp resp = service.registrar(afp);
		assertEquals(afp, resp);
	}
	
	@Test
	public void testModificar() throws Exception {
		
		Afp afp = data.nuevoAfp();
		afp.setIdAfp(1);
		afp.getEmpresa().setIdEmpresa(1);
		afp.setDescripcion("PRIMA SEGURO");
		
		when(repo.save(afp)).thenReturn(afp);
		Afp resp = service.modificar(afp);
		
		assertEquals(afp, resp);
	}
	
	@Test
	public void testListar() throws Exception {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		
		Afp afp = data.nuevoAfp();
		afp.setIdAfp(1);
		afp.getEmpresa().setIdEmpresa(1);
		
		ArrayList<Afp> lsafp = new ArrayList<Afp>();
		lsafp.add(afp);
		lsafp.add(afp);
		
		when(repo.findByEmpresa(emp)).thenReturn(lsafp);
		
		List<Afp> lsresp = service.listarXEmpresa(emp);
		
		assertEquals(lsafp.size(), lsresp.size());
	}
	
	@Test
	public void testEliminar() throws Exception {
		
		Afp afp = data.nuevoAfp();
		afp.setIdAfp(1);
		
		when(repo.existsById(afp.getIdAfp())).thenReturn(false);
		Boolean resp = service.eliminar(afp.getIdAfp());
		
		assertEquals(false, resp);
	}

}
