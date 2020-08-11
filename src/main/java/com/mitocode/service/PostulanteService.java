package com.mitocode.service;

import java.util.List;

import com.mitocode.model.Empresa;
import com.mitocode.model.Postulante;

public interface PostulanteService extends ICRUD<Postulante>{

	List<Postulante> listarPorEmpresa(Empresa empresa);
	
}
