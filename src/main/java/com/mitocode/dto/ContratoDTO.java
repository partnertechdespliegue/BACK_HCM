package com.mitocode.dto;

import javax.validation.constraints.NotNull;

import com.mitocode.model.Contrato;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;

public class ContratoDTO {

	@NotNull(message="No se ha especificado un contrato")
	private Contrato contrato;
	private PdoAno pdoAno;
	private PdoMes pdoMes;
	
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	public PdoAno getPdoAno() {
		return pdoAno;
	}
	public void setPdoAno(PdoAno pdoAno) {
		this.pdoAno = pdoAno;
	}
	public PdoMes getPdoMes() {
		return pdoMes;
	}
	public void setPdoMes(PdoMes pdoMes) {
		this.pdoMes = pdoMes;
	}	
}
