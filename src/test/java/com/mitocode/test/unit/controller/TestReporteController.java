package com.mitocode.test.unit.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.ReporteController;
import com.mitocode.dto.Reporte;
import com.mitocode.model.Parametro;
import com.mitocode.repo.ParametroRepo;
import com.mitocode.util.DataDuroComplementos;

import net.sf.jasperreports.engine.JasperPrint;

public class TestReporteController {
	
	DataDuroComplementos data = new DataDuroComplementos();

	@MockBean
	private DataSource dataSource = Mockito.mock(DataSource.class);
	
	@MockBean
    Connection jdbcConnection = Mockito.mock(Connection.class);

	@InjectMocks
	ReporteController controller;
	
	@Mock
	ParametroRepo parametro_repo;
	
	@Mock
	ReporteController controller_service;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void generarReporte() throws Exception{
		Reporte reporte = data.nuevoReporte();
		JasperPrint print = new JasperPrint();
		Parametro parametro = data.nuevoParametro();
		byte[] resp = new byte [0];
		parametro.setValor("C://Users//PT0022//Desktop//PLANILLAS//PLANTEC_BACK_PROD//src//main//resources//Reportes//");
		Map<String, Object> parametor=new HashMap<String,Object>();
		parametor.put("id_trabajador",reporte.getIdTrabajador());
		parametor.put("id_pdo_mes",reporte.getIdPdoMeS());
		parametor.put("id_pdo_ano",reporte.getIdPdoAno());
		parametor.put("SUBREPORT_DIR",parametro.getValor());
	    MockMultipartFile file = new MockMultipartFile("file", "orig", null, "mocktext para testing".getBytes());
		when(parametro_repo.findByCodigoAndGrupo("RUTRZREPO", "REPORTES")).thenReturn(parametro);
		when(dataSource.getConnection()).thenReturn(jdbcConnection);
		when(controller_service.crearReporte(null, parametro.getValor()
											, parametor, jdbcConnection)).thenReturn(resp);
		byte[] datos = controller.ReportePDF(reporte, result);
		assertTrue(datos.length>0);
		
		
	}
	

	
}
