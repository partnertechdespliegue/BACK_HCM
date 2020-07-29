package com.mitocode.service;

import java.sql.Date;
import java.util.List;

import com.mitocode.model.Falta;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Trabajador;

public interface FaltasService extends ICRUD<Falta>{
	
	List<Falta> busarPorTrabajadoryPeriodo(Falta falta);
	Integer buscarPorFechaYTrabajador(Date fecha, Trabajador trabajador);
	List<Falta> buscarFaltas(Integer tipo,PdoAno pdoAno, PdoMes pdoMes, Trabajador trabajador);

}
