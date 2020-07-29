package com.mitocode.controller.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.Test;

import com.mitocode.controller.TipoPermisoController;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Afp;
import com.mitocode.model.Empresa;
import com.mitocode.model.TipoPermiso;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestTipoPermisoController {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	TipoPermisoController controller;
	
	@Test (priority = 0)
	public void testRegistrarTipoPermiso() throws Exception {
	
		/*EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		ResponseWrapper resp = controller.insertar(empDTO);
		
		assertEquals((TipoPermiso) resp.getDefaultObj(), empDTO.getTipoPermiso());*/
	}
	
	@Test (priority = 2)
	public void testEliminarAfp() throws Exception {
	
		Integer id = 2;
		ResponseWrapper resp = controller.eliminar(id);
		assertEquals(resp.getDefaultObj(), false);
	}
	
	@Test (priority = 3)
	public void testListarPorEmpresaAfp() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = controller.listarXempresa(emp);
		
		assertTrue(resp.getAaData().size() > 0);
	}
	
	@Test (priority = 1)
	public void testModificarAfp() throws Exception {
	
		/*EmpresaDTO empDTO = data.nuevaEmpresaDTO();
		empDTO.getEmpresa().setIdEmpresa(1);
		empDTO.getTipoPermiso().setDescripcion("PATERNIDAD");
		ResponseWrapper resp = controller.modificar(empDTO);
		TipoPermiso tipoPerm = (TipoPermiso) resp.getDefaultObj();
		
		assertEquals(tipoPerm.getDescripcion(), "PATERNIDAD");*/
	}
	
	@Test (priority = 4)
	public void testEliminarTipoPermiso() throws Exception {

		Integer id = 1;
		ResponseWrapper resp = controller.eliminar(id);

		assertEquals(resp.getDefaultObj(),  true);
	}
}
