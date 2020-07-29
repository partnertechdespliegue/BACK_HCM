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

import com.mitocode.model.AdelantoSueldo;
import com.mitocode.model.CuotaAdelanto;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Trabajador;
import com.mitocode.repo.CuotaAdelantoRepo;
import com.mitocode.service.impl.CuotaAdelantoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class CuotaAdelantoServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	CuotaAdelantoServiceImpl service;
	
	@Mock
	CuotaAdelantoRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void testRegistrar() {
		CuotaAdelanto cuoAde = data.nuevaCuotaAdelanto();
		when(repo.save(cuoAde)).thenReturn(cuoAde);
		CuotaAdelanto resp = service.registrar(cuoAde);
		
		assertEquals(resp, cuoAde);
	}

	@Test
	public void testModificar() {
		CuotaAdelanto cuoAde = data.nuevaCuotaAdelanto();
		cuoAde.setIdCuotaAdelanto(1);
		cuoAde.setMontoCuota(550.0);
		when(repo.save(cuoAde)).thenReturn(cuoAde);
		CuotaAdelanto resp = service.modificar(cuoAde);
		
		assertEquals(resp, cuoAde);
	}

	@Test
	public void testListarXAdeSue() {
		
		AdelantoSueldo adeSue = data.nuevoAdelantoSueldo();
		adeSue.setIdAdelantoSueldo(1);
		CuotaAdelanto cuoAde = data.nuevaCuotaAdelanto();
		List<CuotaAdelanto> lscuoAde = new ArrayList<CuotaAdelanto>();
		lscuoAde.add(cuoAde);
		lscuoAde.add(cuoAde);
		when(repo.findByAdelantoSueldo(adeSue)).thenReturn(lscuoAde);
		List<CuotaAdelanto> lsresp = service.listarXAdeSue(adeSue);
		
		assertEquals(lsresp, lscuoAde);
	}

	@Test
	public void testPagado() {
		CuotaAdelanto cuoAde = data.nuevaCuotaAdelanto();
		cuoAde.setIdCuotaAdelanto(1);
		when(repo.save(cuoAde)).thenReturn(cuoAde);
		CuotaAdelanto resp = service.Pagado(cuoAde);
		
		assertEquals(resp, cuoAde);
	}

	@Test
	public void testListarXTrabPAnoPMes() {
		Trabajador trab = data.nuevoTrabajador();
		PdoAno pdoAno = data.nuevoPdoAno();
		PdoMes pdoMes = data.nuevoPdoMes();
		
		CuotaAdelanto cuoAde = data.nuevaCuotaAdelanto();
		List<CuotaAdelanto> lscuoAde = new ArrayList<CuotaAdelanto>();
		lscuoAde.add(cuoAde);
		lscuoAde.add(cuoAde);
		
		when(repo.listarxTrabPAnoPMes(trab.getIdTrabajador(), pdoAno.getIdPdoAno(), pdoMes.getIdPdoMes())).thenReturn(lscuoAde);
		
		List<CuotaAdelanto> lsresp = service.listarXTrabPAnoPMes(trab, pdoAno, pdoMes);
		
		assertEquals(lscuoAde.size(), lsresp.size());
	}

	@Test
	public void testEncontrarCuoAde() {
		CuotaAdelanto cuoAde = data.nuevaCuotaAdelanto();
		cuoAde.setIdCuotaAdelanto(1);
		when(repo.findByIdCuotaAdelanto(cuoAde.getIdCuotaAdelanto())).thenReturn(cuoAde);
		CuotaAdelanto resp = service.EncontrarCuoAde(cuoAde.getIdCuotaAdelanto());
		
		assertEquals(resp, cuoAde);
	}

}
