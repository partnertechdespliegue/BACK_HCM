package com.mitocode.service;

import java.util.List;

import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.Empresa;

public interface AreaDepartamentoEmpresaService  extends ICRUD<AreaDepartamentoEmpresa>{
	public List<AreaDepartamentoEmpresa> eliminarAreaDepartamentoEmpresa(AreaDepartamentoEmpresa areaDepEmp);
	public List<AreaDepartamentoEmpresa> listarAreaDepartamentoEmpresa(DepartamentoEmpresa depEmp);
	public List<AreaDepartamentoEmpresa> listarPorEmpresa(Empresa emp);

}