package com.mitocode.dto;

import javax.validation.Valid;

import com.mitocode.model.NivelEdu;
import com.mitocode.model.Ocupacion;
import com.mitocode.model.Postulante;
import com.mitocode.model.TipoDoc;

public class PostulanteDTO {
	
	//@Valid
	private Postulante postulante;
	
	private TipoDoc tipoDoc;
	
	private NivelEdu nivelEdu;
	
	private Ocupacion ocupacion;
	
	public Ocupacion getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(Ocupacion ocupacion) {
		this.ocupacion = ocupacion;
	}


	public TipoDoc getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(TipoDoc tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public NivelEdu getNivelEdu() {
		return nivelEdu;
	}

	public void setNivelEdu(NivelEdu nivelEdu) {
		this.nivelEdu = nivelEdu;
	}

	public Postulante getPostulante() {
		return postulante;
	}

	public void setPostulante(Postulante postulante) {
		this.postulante = postulante;
	}

}
