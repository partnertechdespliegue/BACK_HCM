package com.mitocode.service;

import java.util.List;
import com.mitocode.model.CuentaContable;
import com.mitocode.model.Empresa;


public interface CuentaContableService extends ICRUD<CuentaContable> {

	public List<CuentaContable> listarxEmpresa (Empresa empresa);
	public CuentaContable buscarCuentaContablexDesc (String cuentaContable);
}
