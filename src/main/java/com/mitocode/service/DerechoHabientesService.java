package com.mitocode.service;

import java.util.List;

import com.mitocode.model.DerechoHabientes;
import com.mitocode.model.Trabajador;

public interface DerechoHabientesService extends ICRUD<DerechoHabientes>{

	public DerechoHabientes encontrarDH(Integer id);
	List<DerechoHabientes> listarActivos(Trabajador trab);
	List<DerechoHabientes> listarInactivos(Trabajador trab);


}
