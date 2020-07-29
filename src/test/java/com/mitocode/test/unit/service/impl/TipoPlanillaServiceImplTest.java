package com.mitocode.test.unit.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mitocode.model.Empresa;
import com.mitocode.model.Perfil;
import com.mitocode.model.TipoPlanilla;
import com.mitocode.model.TipoPlanillaDetalle;
import com.mitocode.model.TipoPlanillaPerfil;
import com.mitocode.model.Trabajador;
import com.mitocode.repo.TipoPlanPerfilRepo;
import com.mitocode.repo.TipoPlanillaDetalleRepo;
import com.mitocode.repo.TipoPlanillaRepo;
import com.mitocode.service.impl.TipoPlanillaServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TipoPlanillaServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	TipoPlanillaServiceImpl service;
	
	@Mock
	TipoPlanillaRepo repo;
	
	@Mock
	TipoPlanillaDetalleRepo repo_detalle;
	
	@Mock
	TipoPlanPerfilRepo repo_perfil;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void listarCorrectoPorEmpresa() throws Exception {
		List<TipoPlanilla> lsTipPlan = new ArrayList<>();
		Empresa emp = data.nuevaEmpresa();
		when(repo.findByEmpresa(emp)).thenReturn(lsTipPlan);
		List<TipoPlanilla> response = service.listarPorEmpresa(emp);
		assertNotNull(lsTipPlan);
		assertEquals(response, lsTipPlan);
	}
	
	@Test
	public void LanzarErrorListar() throws Exception {
		Empresa emp = data.nuevaEmpresa();
		when(repo.findByEmpresa(emp)).thenThrow(RuntimeException.class);
		assertThrows(Exception.class,()->{
			service.listarPorEmpresa(emp);
		});
	}
	
	@Test
	public void EliminarBDTipoPlanillaCorrecto() throws Exception{
		when(repo.existsById(1)).thenReturn(false);
		Boolean resp = service.eliminar(1);
		assertTrue(resp);
	}
	
	@Test
	public void EliminarBDTipoPlanillaError() throws Exception{
		when(repo.existsById(1)).thenReturn(true);
		Boolean resp = service.eliminar(1);
		assertFalse(resp);
	}
	
	@Test
	public void LanzarExcepcionEliminar() throws Exception{
		doThrow(RuntimeException.class).when(repo).deleteById(1);
		assertThrows(Exception.class,()->{
			service.eliminar(1);
		});
	}
	
	@Test
	public void AsignarTrabajadoresCorrecto()throws Exception{
		TipoPlanilla tipoPlan = data.nuevoTipoPlanilla();
		TipoPlanillaDetalle plan1 = data.nuevoTipoPlanillaDetalle();
		plan1.getTrabajador().setIdTrabajador(1);
		List<Trabajador> lsTrabajador = new ArrayList<>();
		lsTrabajador.add(plan1.getTrabajador());
		List<TipoPlanillaDetalle> detalles = new ArrayList<>();
		detalles.add(plan1);
		when(repo_detalle.save(Matchers.<TipoPlanillaDetalle>any())).thenReturn(plan1);
		List<TipoPlanillaDetalle> resp = service.registrarTrabajadores(lsTrabajador, tipoPlan);
		assertTrue(resp.size()>0);
		assertEquals(resp.size(),1);
	}
	
	@Test
	public void LanzarErrorAsignarTrabajador() throws Exception{
		List<Trabajador> lsTrabajador = new ArrayList<>();
		TipoPlanilla tipoPlanilla = data.nuevoTipoPlanilla();
		lsTrabajador.add(data.nuevoTrabajador());
		when(repo_detalle.save(Matchers.<TipoPlanillaDetalle>any())).thenThrow(RuntimeException.class);
		assertThrows(Exception.class,()->{
			service.registrarTrabajadores(lsTrabajador, tipoPlanilla);
		});
	}
	
	/*@Test
	public void obtenerTipoPlanillaPorPerfil() throws Exception{
		List<TipoPlanillaPerfil> lsTipoPlanillaPerfil = new ArrayList<>();
		lsTipoPlanillaPerfil.add(data.nuevoTipoPlanillaPerfil());
		lsTipoPlanillaPerfil.add(data.nuevoTipoPlanillaPerfil());
		lsTipoPlanillaPerfil.add(data.nuevoTipoPlanillaPerfil());
		Perfil perfil = data.nuevoPerfil();
		when(repo_perfil.findByPerfil(perfil)).thenReturn(lsTipoPlanillaPerfil);
		List<TipoPlanilla> resp = service.listarPorPerfil(perfil);
		assertTrue(resp.size()>0);
		assertEquals(resp.size(),3);
	}
	
	@Test
	public void LanzarErrorListarTipoPlanillaPorPerfil() throws Exception{
		Perfil perfil = data.nuevoPerfil();
		when(repo_perfil.findByPerfil(perfil)).thenThrow(RuntimeException.class);
		assertThrows(RuntimeException.class,()->{
			service.listarPorPerfil(perfil);
		});
	}*/
}
