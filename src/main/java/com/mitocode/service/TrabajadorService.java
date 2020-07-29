package com.mitocode.service;

import java.util.List;

import com.mitocode.model.Empresa;
import com.mitocode.model.Trabajador;

public interface TrabajadorService extends ICRUD<Trabajador>{
	
	public Trabajador encontrarTrab(Integer id);
	List<Trabajador> encontrarTrab(Empresa emp);
	List<Trabajador> listarGerencial(Empresa emp);

	List<Trabajador> listarPorEmpresa(Empresa empresa);

}
