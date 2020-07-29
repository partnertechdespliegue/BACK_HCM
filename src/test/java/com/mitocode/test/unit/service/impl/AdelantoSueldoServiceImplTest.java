package com.mitocode.test.unit.service.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.mitocode.dto.AdelantoSueldoDTO;
import com.mitocode.model.AdelantoSueldo;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Trabajador;
import com.mitocode.repo.AdelantoSueldoRepo;
import com.mitocode.service.impl.AdelantoSueldoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AdelantoSueldoServiceImplTest {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	AdelantoSueldoServiceImpl service;
	
	@Mock
	AdelantoSueldoRepo repo;
	
	@Before
	public void init() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void registrar() {
				  
		  Trabajador trabajador = data.nuevoTrabajador();
		  trabajador.setIdTrabajador(1);
		  AdelantoSueldo adeSue = data.nuevoAdelantoSueldo();
		  when(repo.save(adeSue)).thenReturn(adeSue);
		  AdelantoSueldo resp = service.registrar(adeSue);
		 
		 assertEquals(adeSue, resp);
	}
	
	@Test
	public void modificar() {
				  
		  Trabajador trabajador = data.nuevoTrabajador();
		  trabajador.setIdTrabajador(1);
		  AdelantoSueldo adeSue = data.nuevoAdelantoSueldo();
		  when(repo.save(adeSue)).thenReturn(adeSue);
		  AdelantoSueldo resp = service.modificar(adeSue);
		 
		 assertEquals(adeSue, resp);
	}
	
	@Test
	public void listarXTrab() {
				  
		  Trabajador trabajador = data.nuevoTrabajador();
		  trabajador.setIdTrabajador(1);
		  AdelantoSueldo adeSue = data.nuevoAdelantoSueldo();
		  ArrayList<AdelantoSueldo> lsadeSue = new ArrayList<AdelantoSueldo>();
		  lsadeSue.add(adeSue);
		  lsadeSue.add(adeSue);
		  when(repo.findByTrabajador(trabajador)).thenReturn(lsadeSue);
		  List lsresp = service.listarXTrab(trabajador);
		 
		 assertEquals(lsadeSue.size(), lsresp.size());
	}
	
	@Test
	public void deudaSaldada() {
				  
		  AdelantoSueldo adeSue = data.nuevoAdelantoSueldo();
		 adeSue.setIdAdelantoSueldo(1);
		  when(repo.save(adeSue)).thenReturn(adeSue);
		  AdelantoSueldo resp = service.deudaSaldada(adeSue);
		 
		 assertEquals(adeSue, resp);
	}
	
	@Test
	public void listarDeuda() {
				  
		  Trabajador trabajador = data.nuevoTrabajador();
		  trabajador.setIdTrabajador(1);
		  AdelantoSueldo adeSue = data.nuevoAdelantoSueldo();
		  ArrayList<AdelantoSueldo> lsadeSue = new ArrayList<AdelantoSueldo>();
		  lsadeSue.add(adeSue);
		  when(repo.findByTrabajadorAndEstado(trabajador, 0)).thenReturn(lsadeSue);
		  List lsresp = service.listarDeuda(trabajador);
		 
		 assertEquals(lsadeSue.size(), lsresp.size());
	}
	
	@Test
	public void econtrarAdeSueldo() {
				  
		  AdelantoSueldo adeSue = data.nuevoAdelantoSueldo();
		 adeSue.setIdAdelantoSueldo(1);
		  when(repo.findByIdAdelantoSueldo(adeSue.getIdAdelantoSueldo())).thenReturn(adeSue);
		  AdelantoSueldo resp = service.econtrarAdeSueldo(adeSue.getIdAdelantoSueldo());
		 
		 assertEquals(adeSue, resp);
	}

}
