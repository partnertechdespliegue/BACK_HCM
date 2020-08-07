package com.mitocode.testng.repo;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.model.Empresa;
import com.mitocode.model.Solicitud;
import com.mitocode.repo.SolicitudRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class SolicitudRepoTest extends AbstractTestNGSpringContextTests {

	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	SolicitudRepo repo;

	@Test
	public void listarSolicitudXEmpresaTest() {
		Empresa empresa = data.nuevaEmpresa();
		empresa.setIdEmpresa(1);
		List<Solicitud> resp = repo.listarSolicitudXEmpresa(empresa.getIdEmpresa());
		assertTrue(resp.size() >= 0);
	}
}
