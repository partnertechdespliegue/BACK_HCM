package com.mitocode.dto;

import java.util.List;

import com.mitocode.model.Pagina;
import com.mitocode.model.Perfil;

public class ModuloDTO {

	List<Pagina> lsPagina;
	Perfil perfil;
	
	public List<Pagina> getLsPagina() {
		return lsPagina;
	}
	public void setLsPagina(List<Pagina> lsPagina) {
		this.lsPagina = lsPagina;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
}
