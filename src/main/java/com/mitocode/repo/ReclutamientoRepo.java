package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Reclutamiento;

public interface ReclutamientoRepo extends JpaRepository<Reclutamiento, Integer>{

	@Query(value="select * from reclutamiento r\r\n" + 
			"inner join solicitud s  on r.id_solicitud= s.iid_solicitud\r\n" + 
			"inner join trabajador t on t.id_trabajador=s.id_trabajador\r\n" + 
			"inner join empresa e on e.id_empresa=t.id_empresa\r\n" + 
			"inner join postulante po on po.id_reclutamiento=r.iid_reclutamiento where e.id_empresa=:idempresa", nativeQuery = true)
	List<Reclutamiento> listarReclutamientoXEmpresa(@Param("idempresa") Integer idEmpresa);
}
