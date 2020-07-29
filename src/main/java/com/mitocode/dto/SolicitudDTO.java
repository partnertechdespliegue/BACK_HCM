package com.mitocode.dto;

import com.mitocode.model.Empresa;
import com.mitocode.model.Puesto;
import com.mitocode.model.Solicitud;
import com.mitocode.model.Trabajador;

public class SolicitudDTO {

	private Trabajador trabajador;
	
	private Puesto puesto;
	
	private Solicitud solicitud;
	
	private Empresa empresa;

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	
}
