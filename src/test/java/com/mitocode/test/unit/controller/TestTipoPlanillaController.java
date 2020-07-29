package com.mitocode.test.unit.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mitocode.controller.TipoPlanillaController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.TipoPlanillaDTO;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Contrato;
import com.mitocode.model.Empresa;
import com.mitocode.model.Perfil;
import com.mitocode.model.TipoPlanilla;
import com.mitocode.model.TipoPlanillaDetalle;
import com.mitocode.model.TipoPlanillaPerfil;
import com.mitocode.model.Trabajador;
import com.mitocode.service.ContratoService;
import com.mitocode.service.TipoPlanPerfilService;
import com.mitocode.service.TipoPlanillaDetalleService;
import com.mitocode.service.impl.TipoPlanillaServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class TestTipoPlanillaController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	TipoPlanillaController controller;
	
	@Mock
	TipoPlanillaServiceImpl service;
	
	@Mock
	TipoPlanPerfilService service_tpp;
	
	@Mock
	TipoPlanillaDetalleService service_tpd;
	
	@Mock
	ContratoService service_contrato;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void eliminarTPD() throws Exception {
		
		TipoPlanillaDTO tipPlanDTO = data.nuevoTipoPlanillaDTO();
		TipoPlanillaDetalle tipPlanDet = data.nuevoTipoPlanillaDetalle();
		tipPlanDet.setIdTipoPlanillaDetalle(1);
		List<TipoPlanillaDetalle> lstipPlanDet = new ArrayList<>();
		lstipPlanDet.add(tipPlanDet);
		tipPlanDet.setIdTipoPlanillaDetalle(2);
		lstipPlanDet.add(tipPlanDet);
		tipPlanDTO.setLsTipoPlanillaDetalle(lstipPlanDet);
		
		when( service_tpd.eliminarTPD(lstipPlanDet)).thenReturn(false);
		
		ResponseWrapper resp = controller.eliminarTPD(tipPlanDTO);
		
		Integer estado = 1;
		assertEquals(estado, resp.getEstado());
	}
	
	/*@Test
	public void listarTrabajodesLibres() throws Exception {
		
		TipoPlanilla tipPlan = data.nuevoTipoPlanilla();
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		int planilla = 1;
		int rhe = 2;
		
		Contrato cnt = data.nuevoContrato();
		cnt.setIdContrato(1);
		List<Contrato> lscontrato = new ArrayList<>();
		lscontrato.add(cnt);
		cnt.setIdContrato(2);
		lscontrato.add(cnt);
		
		Trabajador trab = data.nuevoTrabajador();
		trab.setIdTrabajador(1);
		List<Trabajador> lstrab = new ArrayList<>();
		lstrab.add(trab);
		trab.setIdTrabajador(2);
		lstrab.add(trab);
		
		
		when(service_contrato.listarPorEmpresaYTipoComp(emp, planilla)).thenReturn(lscontrato);
		when(service_tpd.armarListTrab(lstrab)).thenReturn(lstrab);
		ResponseWrapper resp = controller.listarTrabajodesLibres(tipPlan);
		Integer estado = 1;
		assertEquals(estado, resp.getEstado());
	}*/

	@Test
	public void ObtenerListaResponsePorEmpresa() throws Exception{
		List<TipoPlanilla> lsTip = new ArrayList<>();
		Empresa emp = data.nuevaEmpresa();
		when(service.listarPorEmpresa(emp)).thenReturn(lsTip);
		ResponseWrapper response = controller.listarPorEmpresa(emp);
		assertEquals((int)response.getEstado(),1);
		assertEquals(response.getMsg(),"Tipos de Planilla listados correctamente");
		assertNotNull(response.getAaData());
		assertEquals(response.getAaData(), lsTip);
	}

	@Test
	public void testRegistrar () throws Exception {
		TipoPlanillaDTO tipPlanDTO = data.nuevoTipoPlanillaDTO();
		
		TipoPlanilla tipoplan = data.nuevoTipoPlanilla();
		tipoplan.setIdTipoPlanilla(1);
		
		TipoPlanillaPerfil tpp = new TipoPlanillaPerfil();
		tpp.setTipoPlanilla(tipoplan);
		tpp.setPerfil(tipPlanDTO.getLsPerfil().get(0));
	
		when(service.registrar(Matchers.<TipoPlanilla>any())).thenReturn(tipoplan);
		when(service_tpp.registrar(Matchers.<TipoPlanillaPerfil>any())).thenReturn(tpp);
		ResponseWrapper response = controller.registrar(tipPlanDTO);
		assertEquals(tipoplan,(TipoPlanilla) response.getDefaultObj());
		
	}
	
	@Test
	public void listarPortipoPlanillaCorrecto() throws Exception{
		TipoPlanilla tipoPlanilla = data.nuevoTipoPlanilla();
		List<TipoPlanillaPerfil> lsTipPlanPerfil = new ArrayList<>();
		lsTipPlanPerfil.add(data.nuevoTipoPlanillaPerfil());
		lsTipPlanPerfil.add(data.nuevoTipoPlanillaPerfil());
		lsTipPlanPerfil.add(data.nuevoTipoPlanillaPerfil());

		when(service_tpp.listarPorTipoPlanilla(tipoPlanilla)).thenReturn(lsTipPlanPerfil);
		ResponseWrapper response = controller.listarPorTipoPlanilla(tipoPlanilla);
		assertTrue(response.getAaData().size()>0);
		assertEquals(response.getAaData().size(), 3);
	}
	
	@Test
	public void LanzarErrorlistarPortipoPlanilla() throws Exception{
		StackTraceElement[] lsStack = new StackTraceElement[1];
		StackTraceElement stack = new StackTraceElement("TipoPlanilla","listarPortipoPlanilla","Controller",24);
		lsStack[0]= stack;
		RuntimeException e = new RuntimeException();
		e.setStackTrace(lsStack);
		TipoPlanilla tipoPlanilla = data.nuevoTipoPlanilla();
		when(service_tpp.listarPorTipoPlanilla(tipoPlanilla)).thenThrow(e);
		assertThrows(ExceptionResponse.class,()->{
			controller.listarPorTipoPlanilla(tipoPlanilla);
		});
	}
	
	@Test
	public void ModificarPerfilesCorrecto() throws Exception{
		TipoPlanillaDTO tipoPlanillaDTO = data.nuevoTipoPlanillaDTO();
		List<TipoPlanillaPerfil> lsTipoPlanPerfil = new ArrayList<>();
		lsTipoPlanPerfil.add(data.nuevoTipoPlanillaPerfil());
		lsTipoPlanPerfil.add(data.nuevoTipoPlanillaPerfil());
		lsTipoPlanPerfil.add(data.nuevoTipoPlanillaPerfil());
		tipoPlanillaDTO.setLsTipoPlanillaPerfil(lsTipoPlanPerfil);
		when(service_tpp.modificar(data.nuevoTipoPlanillaPerfil())).thenReturn(null);
		ResponseWrapper response = controller.modificarPlanillaPerfil(tipoPlanillaDTO);
		Integer estado = 1;
		assertEquals(estado, response.getEstado());
	}
	
	@Test
	public void testModificarCambiarCategoria() {
		TipoPlanilla tipPlan = data.nuevoTipoPlanilla();
		tipPlan.setIdTipoPlanilla(1);
		
		TipoPlanilla otro = data.nuevoTipoPlanilla();
		otro.setIdTipoPlanilla(1);
		otro.setCategoriaPlanilla(5);
		
		when(service.encontrarTipPlan(Matchers.<TipoPlanilla>any())).thenReturn(otro);
		when(service.modificar(Matchers.<TipoPlanilla>any())).thenReturn(tipPlan);
		when(service_tpd.eliminar(Matchers.<TipoPlanilla>any())).thenReturn(false);
		
		ResponseWrapper resp = controller.modificar(tipPlan);
		Integer estado = 1;
		assertEquals(estado, resp.getEstado());
	}
	
	@Test
	public void LanzarErrorListarPorEmpresa() throws Exception{
		StackTraceElement[] lsStack = new StackTraceElement[1];
		StackTraceElement stack = new StackTraceElement("TipoPlanilla","ListarPorEmpresa","Controller",24);
		lsStack[0]= stack;
		RuntimeException e = new RuntimeException();
		e.setStackTrace(lsStack);
		Empresa emp = data.nuevaEmpresa();
		when(service.listarPorEmpresa(emp)).thenThrow(e);
		assertThrows(ExceptionResponse.class,()->{
			controller.listarPorEmpresa(emp);
		});
	}
	
	@Test
	public void EliminarConExitoTipoPlanilla() throws Exception{
		when(service.eliminar(1)).thenReturn(true);
		ResponseWrapper response = controller.eliminar(1);
		assertEquals((int)response.getEstado(), 1);
		assertEquals(response.getMsg(), "Tipo de planilla eliminado correctamente");
		assertTrue((boolean) response.getDefaultObj());
	}
	
	@Test
	public void EliminarErrorTipoPlanilla() throws Exception{
		when(service.eliminar(1)).thenReturn(false);
		ResponseWrapper response = controller.eliminar(1);
		assertEquals((int)response.getEstado(), 2);
		assertEquals(response.getMsg(), "Error al eliminar el tipo de Planilla");
		assertFalse((boolean) response.getDefaultObj());
	}
	
	@Test
	public void LanzarExceptionEliminar() throws Exception{
		StackTraceElement[] lsStack = new StackTraceElement[1];
		StackTraceElement stack = new StackTraceElement("TipoPlanilla","Eliminar","Controller",666);
		lsStack[0]= stack;
		RuntimeException e = new RuntimeException();
		e.setStackTrace(lsStack);
		when(service.eliminar(1)).thenThrow(e);
		assertThrows(ExceptionResponse.class,()->{
			controller.eliminar(1);
		});
	}
	
	@Test
	public void RegistrarTrabajadoresPlanillaCorrecto() throws Exception{
		TipoPlanillaDTO tipPlan = data.nuevoTipoPlanillaDTO();
		TipoPlanillaDetalle plan1 = data.nuevoTipoPlanillaDetalle();
		TipoPlanillaDetalle plan2 = data.nuevoTipoPlanillaDetalle();
		plan1.getTrabajador().setIdTrabajador(1);
		plan2.getTrabajador().setIdTrabajador(2);
		List<TipoPlanillaDetalle> detalles = new ArrayList<>();
		detalles.add(plan1);
		detalles.add(plan2);
		when(service.registrarTrabajadores(tipPlan.getLsTrabajador(),tipPlan.getTipoPlanilla()))
								.thenReturn(detalles);
		ResponseWrapper response = controller.registrarTrabajadores(tipPlan);
		assertTrue(response.getAaData().size()>0);
		assertEquals(response.getAaData().size(), 2);
	}
	
	@Test
	public void LanzarErrorRegistrarTrabajadoresPlanilla() throws Exception{
		StackTraceElement[] lsStack = new StackTraceElement[1];
		StackTraceElement stack = new StackTraceElement("TipoPlanilla","asignarTrabajador","Controller",666);
		lsStack[0]= stack;
		RuntimeException e = new RuntimeException();
		e.setStackTrace(lsStack);
		TipoPlanillaDTO tipPlan = data.nuevoTipoPlanillaDTO();
		when(service.registrarTrabajadores(tipPlan.getLsTrabajador(),tipPlan.getTipoPlanilla()))
						.thenThrow(e);
		assertThrows(ExceptionResponse.class,()->{
			controller.registrarTrabajadores(tipPlan);
		});
	}
	
	@Test
	public void ListarTrabajadoresPorTipoPlanillaCorrecto() throws Exception{
		TipoPlanilla tipoPlanilla = data.nuevoTipoPlanilla();
		List<Contrato> lsContrato = new ArrayList<>();
		lsContrato.add(data.nuevoContrato());
		lsContrato.add(data.nuevoContrato());
		lsContrato.add(data.nuevoContrato());
		tipoPlanilla.setCategoriaPlanilla(4);
		Empresa empresa = data.nuevaEmpresa();
		empresa.setIdEmpresa(1);
		tipoPlanilla.setEmpresa(empresa);
		when(service_contrato.listarPorEmpresaYTipoComp(empresa, 1)).thenReturn(lsContrato);
		when(service_tpd.armarListContrato(lsContrato, tipoPlanilla)).thenReturn(lsContrato);
		ResponseWrapper response = controller.listarTrabajadoresPorTipoPlanilla(tipoPlanilla);
		assertTrue(response.getAaData().size()>0);
		assertEquals(response.getAaData().size(), 3);
	}
	
	@Test
	public void ListarPorPerfilCorrecto() throws Exception{
		Perfil perfil = data.nuevoPerfil();
		List<TipoPlanilla> lsTipoPlanilla = new ArrayList<>();
		lsTipoPlanilla.add(data.nuevoTipoPlanilla());
		lsTipoPlanilla.add(data.nuevoTipoPlanilla());
		lsTipoPlanilla.add(data.nuevoTipoPlanilla());
		when(service.listarPorPerfil(perfil)).thenReturn(lsTipoPlanilla);
		ResponseWrapper response = controller.listarPorPerfil(perfil);
		assertTrue(response.getAaData().size()>0);
		assertEquals(response.getAaData().size(),3);
	}
	
	@Test
	public void LanzarErrorListarPerfil() throws Exception{
		Perfil perfil = data.nuevoPerfil();
		StackTraceElement[] lsStack = new StackTraceElement[1];
		StackTraceElement stack = new StackTraceElement("TipoPlanilla","listarPorPerfiles","Controller",666);
		lsStack[0]= stack;
		RuntimeException e = new RuntimeException();
		e.setStackTrace(lsStack);
		when(service.listarPorPerfil(perfil)).thenThrow(e);
		assertThrows(ExceptionResponse.class,()->{
			controller.listarPorPerfil(perfil);
		});
	}
	
	
}
