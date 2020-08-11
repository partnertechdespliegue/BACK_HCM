package com.mitocode.service;

import java.util.List;

import com.mitocode.model.Empresa;
import com.mitocode.model.Reclutamiento;

public interface ReclutamientoService extends ICRUD<Reclutamiento> {

	public List<Reclutamiento> listarxEmpresa(Empresa empresa);
}
