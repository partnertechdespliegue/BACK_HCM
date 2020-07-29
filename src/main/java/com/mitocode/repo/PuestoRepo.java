package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.ProyeccionPuesto;
import com.mitocode.model.Puesto;

public interface PuestoRepo extends JpaRepository<Puesto, Integer>{

	@Query(value="select p.iid_puesto, p.iestado, p.scategoria, p.snombre, p.id_area_departamento_empresa\r\n" + 
			"from puesto p" +"\r\n Inner Join area_departamento_empresa areaDepEmp \r\n" + 
			"On p.id_area_departamento_empresa = areaDepEmp.iid_area_departamento_empresa \r\n" + 
			"Inner Join departamento_empresa depEmp on depEmp.iid_departamento_empresa = areaDepEmp.id_departamento_empresa \r\n" +
			"Inner Join empresa emp on emp.id_empresa = depEmp.id_empresa where emp.id_empresa = :idempresa", nativeQuery = true)
	List<Puesto> listarPuestoXEmpresa(@Param("idempresa") Integer idEmpresa);
	
	@Query(value="SELECT iid_puesto, iestado, scategoria, snombre, id_area_departamento_empresa\r\n" + 
			"	FROM puesto p INNER JOIN proyeccion pr on p.iid_puesto = pr.id_puesto \r\n" + 
			"	where p.id_area_departamento_empresa = :idAreaDepartamento and pr.iorden > :iorden", nativeQuery = true)
	List<Puesto> listarPuestoXOrden(@Param("idAreaDepartamento") Integer idAreaDepartamento, @Param("iorden") Integer iorden);
	
	List<Puesto> findByAreaDepartamentoEmpresa(AreaDepartamentoEmpresa areaDepEmp);
	AreaDepartamentoEmpresa findBySnombre(String snombre);

}
