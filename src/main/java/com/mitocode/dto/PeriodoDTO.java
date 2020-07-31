package com.mitocode.dto;

import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;

public class PeriodoDTO {

	PdoAno pdoAno;
	PdoMes pdoMes;
	
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
