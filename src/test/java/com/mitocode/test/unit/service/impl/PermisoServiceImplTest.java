package com.mitocode.test.unit.service.impl;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mitocode.model.Permiso;
import com.mitocode.repo.PermisoRepo;
import com.mitocode.service.impl.PermisoServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class PermisoServiceImplTest {

	DataDuroComplementos data = new DataDuroComplementos();

	@InjectMocks
	PermisoServiceImpl service;

	@Mock
	PermisoRepo repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRegistrarPermisoCorrecto() throws Exception {
		Permiso permiso = data.nuevoPermiso();
		Permiso fin = permiso;
		Integer dias = permiso.getTipoPermiso().getDiasPermiso();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(permiso.getFechaIni());
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		fin.setFechaFin(calendar.getTime());
		when(repo.save(permiso)).thenReturn(fin);
		Permiso resp = service.registrar(permiso);
		assertEquals(fin.getFechaFin(), resp.getFechaFin());
	}

	@Test
	public void testRegistrarPermisoInCorrecto() throws Exception {
		Permiso permiso = data.nuevoPermiso();
		permiso.setTipoPermiso(null);
		assertThrows(NullPointerException.class, () -> {
			service.registrar(permiso);
		});
	}

}
