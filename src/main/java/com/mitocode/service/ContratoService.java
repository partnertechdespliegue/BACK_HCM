package com.mitocode.service;

import java.util.List;

import com.mitocode.model.Contrato;
import com.mitocode.model.Empresa;

public interface ContratoService extends ICRUD<Contrato> {

	List<Contrato> listarPorEmpresa(Empresa empresa);
	List<Contrato> listarPorEmpresaYTipoComp(Empresa empresa, Integer tipoComp);
	List<Contrato> listarPorEmpresayCuartaCat(Empresa empresa);
	

}
