package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Puesto;
import com.mitocode.model.Solicitud;
import com.mitocode.model.Trabajador;

public interface SolicitudRepo extends JpaRepository<Solicitud, Integer> {
	
	List<Solicitud> findByTrabajador(Trabajador trabajador);
	
	@Query(value="select * from solicitud s\r\n" + 
			"join trabajador t on s.id_trabajador=t.id_trabajador\r\n" + "join puesto p on p.iid_puesto= s.id_puesto\r\n"+
			"join empresa e on e.id_empresa=t.id_empresa where e.id_empresa=:idempresa", nativeQuery = true)
	List<Solicitud> listarSolicitudXEmpresa(@Param("idempresa") Integer idEmpresa);

}
