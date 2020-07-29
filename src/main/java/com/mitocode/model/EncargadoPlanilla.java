package com.mitocode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "encargado_planilla")
public class EncargadoPlanilla {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEncargadoPlanilla;
	
	private Integer estado;
	
	@OneToOne
	@JoinColumn(name = "id_trabajador", nullable = false)
	private Trabajador trabajador;
	
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = false)
	private Empresa empresa;

	public Integer getIdEncargadoPlanilla() {
		return idEncargadoPlanilla;
	}

	public void setIdEncargadoPlanilla(Integer idEncargadoPlanilla) {
		this.idEncargadoPlanilla = idEncargadoPlanilla;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
