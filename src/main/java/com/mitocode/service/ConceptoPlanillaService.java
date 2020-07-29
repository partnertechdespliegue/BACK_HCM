package com.mitocode.service;

import java.util.List;

import com.mitocode.dto.EmpresaDTO;
import com.mitocode.model.ConceptoPlanilla;
import com.mitocode.model.CuentaContable;
import com.mitocode.model.Empresa;


public interface ConceptoPlanillaService extends ICRUD<ConceptoPlanilla> {

	public List<ConceptoPlanilla> listarxEmpresa (Empresa empresa);
	public ConceptoPlanilla buscarConceptoPlanillaxDes (String conceptoPlanilla);
	
	public Double listarProvisionDebe (EmpresaDTO empresaDTO, CuentaContable ctaContable);
	public Double listarProvisionHaber (EmpresaDTO empresaDTO, CuentaContable ctaContable);
	
}
