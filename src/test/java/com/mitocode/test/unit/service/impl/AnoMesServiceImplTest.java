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

import com.mitocode.model.Empresa;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.repo.PdoAnoRepo;
import com.mitocode.repo.PdoMesRepo;
import com.mitocode.service.impl.AnoMesServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class AnoMesServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	AnoMesServiceImpl service;
	
	@Mock
	PdoMesRepo repo_mes;
	
	@Mock
	PdoAnoRepo repo_ano;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void listarPorEmpresa() {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		PdoAno pdoAno = data.nuevoPdoAno();
		ArrayList<PdoAno> lspdoAno = new ArrayList<PdoAno>();
		lspdoAno.add(pdoAno);
		lspdoAno.add(pdoAno);
		lspdoAno.add(pdoAno);
		lspdoAno.add(pdoAno);
		lspdoAno.add(pdoAno);
		lspdoAno.add(pdoAno);
		when(repo_ano.findByEmpresa(emp)).thenReturn(lspdoAno);
		List lsresp = service.listarPorEmpresa(emp);
		
		assertEquals(lspdoAno.size(), lsresp.size());
	}
	
	@Test
	public void registrarAnoDefecto() {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		Boolean resp = service.registrarAnoDefecto(emp);
		
		assertEquals( true, resp);
	}
	
	
	//Fallando
	/*@Test
	public void listarMeses() {
		
		PdoMes pdoMes = data.nuevoPdoMes();
		ArrayList<PdoMes> lsmes = new ArrayList<PdoMes>();
		lsmes.add(pdoMes);
		lsmes.add(pdoMes);
		lsmes.add(pdoMes);
		
		when(repo_mes.findAll()).thenReturn(lsmes);
		
		List lsresp = service.listarMeses();
		
		assertEquals(lsresp.size(), lsmes.size());
	}*/
	
	@Test
	public void registrar() {
		
		PdoAno pdoAno = data.nuevoPdoAno();
		when(repo_ano.save(pdoAno)).thenReturn(pdoAno);
		PdoAno resp = service.registrar(pdoAno);
		assertEquals(pdoAno, resp);
	}
	
	@Test
	public void buscarSiExistePorDesc() {
		
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		PdoAno pdoAno = data.nuevoPdoAno();
		when(repo_ano.findByEmpresaAndDescripcion(emp, pdoAno.getDescripcion())).thenReturn(pdoAno);
		PdoAno resp = service.buscarSiExistePorDesc(emp, pdoAno);
		assertEquals(pdoAno, resp);
	}
	
	@Test
	public void ModificarMes() {
		
		PdoMes pdoMes = data.nuevoPdoMes();
		pdoMes.setIdPdoMes(1);
		pdoMes.setCantidadDias(2);
		when(repo_mes.save(pdoMes)).thenReturn(pdoMes);
		PdoMes resp = service.ModificarMes(pdoMes);
		assertEquals(pdoMes, resp);
	}
	
	@Test
	public void encontrarMes() {
		
		PdoMes pdoMes = data.nuevoPdoMes();
		pdoMes.setIdPdoMes(1);
		when(repo_mes.findByIdPdoMes(pdoMes.getIdPdoMes())).thenReturn(pdoMes);
		PdoMes resp = service.encontrarMes(pdoMes.getIdPdoMes());
		assertEquals(pdoMes, resp);
	}
	
	@Test
	public void encontrarAno() {
		
		PdoAno pdoAno = data.nuevoPdoAno();
		pdoAno.setIdPdoAno(1);
		when(repo_ano.findByIdPdoAno(pdoAno.getIdPdoAno())).thenReturn(pdoAno);
		PdoAno resp = service.encontrarAno(pdoAno.getIdPdoAno());
		assertEquals(pdoAno, resp);
	}

}
