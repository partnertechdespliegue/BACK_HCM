package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.controller.TipoPlanillaController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.TipoPlanillaDTO;
import com.mitocode.model.Empresa;
import com.mitocode.model.Perfil;
import com.mitocode.model.TipoPlanilla;
import com.mitocode.model.TipoPlanillaDetalle;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
//@TestPropertySource(locations = "classpath:db-test.properties")
public class TestTipoPlanillaController extends AbstractTestNGSpringContextTests {

	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoPlanillaController controller;
	
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
		
		
		ResponseWrapper resp = controller.eliminarTPD(tipPlanDTO);
		
		Integer estado = 1;
		assertEquals(estado, resp.getEstado());
	}
	
	@Test
	public void listarTrabajodesLibres() throws Exception {
		
		TipoPlanilla tipPlan = data.nuevoTipoPlanilla();
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);		
		
		ResponseWrapper resp = controller.listarTrabajodesLibres(tipPlan);
		Integer estado = 1;
		assertEquals(estado, resp.getEstado());
	}
	
	@Test(priority = 1)
	public void ListarTipoPlanillaPorEmpresa() throws Exception{
		Empresa empresa = data.nuevaEmpresa();
		empresa.setIdEmpresa(1);
		ResponseWrapper lsTipoPlan = controller.listarPorEmpresa(empresa);
		assertTrue(lsTipoPlan.getAaData().size()>0);
		assertEquals(lsTipoPlan.getAaData().size(), 2);
	}
	
	@Test(priority = 5)
	public void EliminarTipoPlanilla() throws Exception {
		ResponseWrapper resp = controller.eliminar(1);
		assertTrue((boolean) resp.getDefaultObj());
	}
	
	@Test(priority = 0)
	public void testRegistrarTipoPlanilla() throws Exception {
		TipoPlanillaDTO tipPlanDTO = data.nuevoTipoPlanillaDTO();
		
		ResponseWrapper resp = controller.registrar(tipPlanDTO);
		Integer estado = 1;
		assertEquals(estado, resp.getEstado());
	}
	
	@Test(priority = 4)
	public void testModifcar() {
		TipoPlanilla tipPlan = data.nuevoTipoPlanilla();
		tipPlan.setIdTipoPlanilla(1);
		//tipPlan.setCategoriaPlanilla(5);
		
		ResponseWrapper resp = controller.modificar(tipPlan);
		
		Integer estado = 1;
		assertEquals(estado, resp.getEstado());
	}
	
	@Test(priority = 2)
	public void testAsignarTrabajador() throws Exception{
		TipoPlanillaDTO tipoPlanillaDTO = data.nuevoTipoPlanillaDTO();
		ResponseWrapper resp = controller.registrarTrabajadores(tipoPlanillaDTO);
		Integer estado = 1;
		assertEquals(estado, resp.getEstado());
	}
	
	@Test(priority = 3)
	public void testListarPorPerfil() throws Exception{
		Perfil perfil = data.nuevoPerfil();
		perfil.setIdPerfil(1);
		ResponseWrapper resp = controller.listarPorPerfil(perfil);
		Integer estado = 1;
		assertEquals(estado, resp.getEstado());	
	}
	
	@Test
	public void testListarPorTipoPlanilla() throws Exception{
		TipoPlanilla tipoPlanilla = data.nuevoTipoPlanilla();
		tipoPlanilla.setIdTipoPlanilla(1);
		ResponseWrapper response = controller.listarPorTipoPlanilla(tipoPlanilla);
		Integer estado = 1;
		assertEquals(response.getEstado(), estado);
		assertTrue(response.getAaData().size()>0);
	}
}
