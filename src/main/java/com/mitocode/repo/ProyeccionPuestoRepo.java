package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.ProyeccionPuesto;
import com.mitocode.model.Puesto;

public interface ProyeccionPuestoRepo extends JpaRepository<ProyeccionPuesto, Integer> {

	@Query(value="select proy.*, p.*\r\n" + 
			"from proyeccion proy\r\n" + 
			"Inner Join puesto p on proy.id_puesto = p.iid_puesto\r\n" + 
			"Inner Join area_departamento_empresa areaDepEmp On p.id_area_departamento_empresa = areaDepEmp.iid_area_departamento_empresa \r\n" + 
			"Inner Join departamento_empresa depEmp on depEmp.iid_departamento_empresa = areaDepEmp.id_departamento_empresa\r\n" + 
			"Inner Join empresa emp on emp.id_empresa = depEmp.id_empresa where emp.id_empresa = :idempresa"			
			, nativeQuery = true)
	List<ProyeccionPuesto> listarProyPuestoXEmpresa(@Param("idempresa") Integer idEmpresa);
	ProyeccionPuesto findByPuesto(Puesto puesto);
	ProyeccionPuesto findByIidProyeccion(Integer iidProyeccion);
}
