package com.mitocode.repo;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mitocode.model.Falta;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Trabajador;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class FaltasRepoTest extends AbstractTestNGSpringContextTests{
	
	DataDuroComplementos data = new DataDuroComplementos();

	@Autowired
	FaltasRepo repo;

  /*@Test
  public void existsByFechaAndTrabajadorTest() {
    throw new RuntimeException("Test not implemented");
  }*/

  @Test
  public void findByJustificadoAndPdoAnoAndPdoMesAndTrabajadorTest() {
    int tipoJustificacion = 1;
    PdoAno pdoAno = data.nuevoPdoAno();
    pdoAno.setIdPdoAno(6);
    PdoMes pdoMes = data.nuevoPdoMes();
    pdoMes.setIdPdoMes(4);
    Trabajador trabajador = data.nuevoTrabajador();
    trabajador.setIdTrabajador(1);
    
    List<Falta> lsFalta = repo.findByJustificadoAndPdoAnoAndPdoMesAndTrabajador(tipoJustificacion, pdoAno, pdoMes, trabajador);
    
    assertTrue(lsFalta.size()>0);
    assertTrue(lsFalta.size() == 1);
  }

  /*@Test
  public void findByTrabajadorAndPdoAnoAndPdoMesOrderByFechaAscTest() {
    throw new RuntimeException("Test not implemented");
  }*/
}
