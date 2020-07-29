package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.DepartamentoEmpresa;

public interface AreaDepartamentoEmpresaRepo extends JpaRepository<AreaDepartamentoEmpresa, Integer>{
	//depEmp   cat
	//area cargo
	//descripcion   snombre
	/*@Query(value="select area.iid_area_departamento_empresa, area.snombre, depEmp.iid_departamento_empresa, depEmp.snombre, emp.id_empresa, emp.razon_social  from departamento_empresa depEmp\r\n" + 
			"Inner Join area_departamento_empresa area On area.id_departamento_empresa = depEmp.iid_departamento_empresa \r\n" + 
			"Inner Join empresa emp on emp.id_empresa = depEmp.id_empresa where emp.id_empresa = :idempresa", nativeQuery = true)
	List<AreaDepartamentoEmpresa> listarAreaDepartamentoXEmpresa(@Param("idempresa") Integer idEmpresa);*/
	
	@Query(value="select area.iid_area_departamento_empresa, area.iestado, area.snombre, area.id_departamento_empresa\r\n" + 
			"from departamento_empresa depEmp Inner Join area_departamento_empresa area \r\n" + 
			"On area.id_departamento_empresa = depEmp.iid_departamento_empresa \r\n" + 
			"Inner Join empresa emp on emp.id_empresa = depEmp.id_empresa where emp.id_empresa = :idempresa", nativeQuery = true)
	List<AreaDepartamentoEmpresa> listarAreaDepartamentoXEmpresa(@Param("idempresa") Integer idEmpresa);

	List<AreaDepartamentoEmpresa> findByDepartamentoEmpresa(DepartamentoEmpresa depEmp);
	AreaDepartamentoEmpresa findBySnombre(String snombre);
	
}

