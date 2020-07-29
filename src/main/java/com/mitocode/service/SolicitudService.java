package com.mitocode.service;

import java.util.List;

import com.mitocode.dto.SolicitudDTO;
import com.mitocode.model.Empresa;
import com.mitocode.model.Puesto;
import com.mitocode.model.Solicitud;
import com.mitocode.model.Trabajador;

public interface SolicitudService extends ICRUD<Solicitud> {
	
	public List<Solicitud> listarxTrabajador(Trabajador trabajador);
	public List<Solicitud> listarxEmpresa(Empresa empresa);

}
