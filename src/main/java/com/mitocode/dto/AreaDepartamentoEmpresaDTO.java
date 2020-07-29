package com.mitocode.dto;

import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.DepartamentoEmpresa;

public class AreaDepartamentoEmpresaDTO {
	
	private AreaDepartamentoEmpresa areaDepartamentoEmpresa;
	private DepartamentoEmpresa departamentoEmpresa;
	
	
	public AreaDepartamentoEmpresa getAreaDepartamentoEmpresa() {
		return areaDepartamentoEmpresa;
	}
	public void setAreaDepartamentoEmpresa(AreaDepartamentoEmpresa areaDepartamentoEmpresa) {
		this.areaDepartamentoEmpresa = areaDepartamentoEmpresa;
	}
	public DepartamentoEmpresa getDepartamentoEmpresa() {
		return departamentoEmpresa;
	}
	public void setDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
		this.departamentoEmpresa = departamentoEmpresa;
	}

}
