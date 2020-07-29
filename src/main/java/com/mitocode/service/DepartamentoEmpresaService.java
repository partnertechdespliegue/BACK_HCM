package com.mitocode.service;

import java.util.List;

import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.Empresa;

public interface DepartamentoEmpresaService  extends ICRUD<DepartamentoEmpresa>{
	public List<DepartamentoEmpresa> eliminardeDepartamentoEmpresas(DepartamentoEmpresa depEmp);
	public List<DepartamentoEmpresa> listarDepartXEmpresa(Empresa emp);
	

}
