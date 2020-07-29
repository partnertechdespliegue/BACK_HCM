package com.mitocode.dto;

import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.Empresa;
import com.mitocode.model.Trabajador;

public class DepartamentoEmpresaDTO {
	
	private Empresa empresa;
	private DepartamentoEmpresa departamentoEmpresa;
	private Trabajador trabajador;
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public DepartamentoEmpresa getDepartamentoEmpresa() {
		return departamentoEmpresa;
	}
	public void setDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
		this.departamentoEmpresa = departamentoEmpresa;
	}
	public Trabajador getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

}
