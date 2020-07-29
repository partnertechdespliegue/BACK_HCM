package com.mitocode.service;

import java.util.List;

import com.mitocode.model.Puesto;
import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.Empresa;
import com.mitocode.model.ProyeccionPuesto;

public interface PuestoService extends ICRUD<Puesto>{
	
	public List<Puesto> listarPuesto(AreaDepartamentoEmpresa areaDepEmp);
	public List<Puesto> listarPorEmpresa(Empresa emp);
	public List<ProyeccionPuesto> listarProyeccionPuesto(Empresa emp);
	List<Puesto> listarPuestoXOrden(Integer idAreaDepartamento, Integer iorden);	

}
