package com.mitocode.testng.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.controller.HorarioController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Empresa;
import com.mitocode.model.Horario;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestHorarioController extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	HorarioController controller;
	
	@Test (priority = 0)
	public void testRegistrarHorario() throws Exception {
		/*Horario horario = data.nuevoHorario();
		horario.getEmpresa().setIdEmpresa(1);
		ResponseWrapper resp = controller.insertarHorario(horario);
		assertEquals((Horario) resp.getDefaultObj(), horario);*/
	}
	
	@Test (priority = 1)
	public void testListarHorarioXempresa() throws Exception {
	
		Empresa emp = data.nuevaEmpresa();
		emp.setIdEmpresa(1);
		ResponseWrapper resp = controller.listarHorariosXEmpresa(emp);
		assertTrue(resp.getAaData().size() > 0);
	}
	

	/*@Test (priority = 2)
	public void testModificarHorario() throws Exception {
	
		Horario horario = data.nuevoHorario();
		horario.getEmpresa().setIdEmpresa(1);
		horario.setDescripcion("MODIFICADO");
		ResponseWrapper resp = controller.modificarHorario(horario);
		Horario res_horario = (Horario) resp.getDefaultObj();
		
		assertEquals(res_horario.getDescripcion(), "MODIFICADO");
	}*/

}
