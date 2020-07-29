package com.mitocode.dto;

import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.ProyeccionPuesto;
import com.mitocode.model.Puesto;

public class PuestoDTO {
	
	private Puesto puesto;
	private AreaDepartamentoEmpresa areaDepartamentoEmpresa;
	private Puesto puestoJefe;
	private ProyeccionPuesto proyeccion;
	
	public Puesto getPuesto() {
		return puesto;
	}
	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}
	public AreaDepartamentoEmpresa getAreaDepartamentoEmpresa() {
		return areaDepartamentoEmpresa;
	}
	public void setAreaDepartamentoEmpresa(AreaDepartamentoEmpresa areaDepartamentoEmpresa) {
		this.areaDepartamentoEmpresa = areaDepartamentoEmpresa;
	}
	public Puesto getPuestoJefe() {
		return puestoJefe;
	}
	public void setPuestoJefe(Puesto puestoJefe) {
		this.puestoJefe = puestoJefe;
	}
	
	public ProyeccionPuesto getProyeccion() {
		return proyeccion;
	}
	public void setProyeccion(ProyeccionPuesto proyeccion) {
		this.proyeccion = proyeccion;
	}
	
}
