package com.mitocode.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitocode.model.Asistencia;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Trabajador;

public interface AsistenciaRepo extends JpaRepository<Asistencia,Integer>{
	
	List<Asistencia> findByTrabajadorAndPdoAnoAndPdoMesOrderByFechaAsc(Trabajador trabajador,PdoAno pdoAno, PdoMes pdoMes);
	
	Boolean existsByFechaAndTrabajador(Date fecha,Trabajador trabajador);
}
