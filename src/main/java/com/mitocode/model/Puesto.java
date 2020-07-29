package com.mitocode.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "puesto")
public class Puesto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iidPuesto;
	
	@Column
	private String snombre;
	
	@Column
	private Integer iestado;
	
	@Column(length = 1)
	private String scategoria;// M, MASIVO ---- E, ESPECIALIZADO

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "puesto")
	private ProyeccionPuesto proyeccionPuesto;
	
	@ManyToOne
	@JoinColumn(name = "id_areaDepartamentoEmpresa", nullable = false)
	private AreaDepartamentoEmpresa areaDepartamentoEmpresa;

	public AreaDepartamentoEmpresa getAreaDepartamentoEmpresa() {
		return areaDepartamentoEmpresa;
	}

	public void setAreaDepartamentoEmpresa(AreaDepartamentoEmpresa areaDepartamentoEmpresa) {
		this.areaDepartamentoEmpresa = areaDepartamentoEmpresa;
	}

	public Integer getIidPuesto() {
		return iidPuesto;
	}

	public void setIidPuesto(Integer iidPuesto) {
		this.iidPuesto = iidPuesto;
	}

	public String getSnombre() {
		return snombre;
	}

	public void setSnombre(String snombre) {
		this.snombre = snombre;
	}

	public Integer getIestado() {
		return iestado;
	}

	public void setIestado(Integer iestado) {
		this.iestado = iestado;
	}

	public String getScategoria() {
		return scategoria;
	}

	public void setScategoria(String scategoria) {
		this.scategoria = scategoria;
	}

	public ProyeccionPuesto getProyeccionPuesto() {
		return proyeccionPuesto;
	}

	public void setProyeccionPuesto(ProyeccionPuesto proyeccionPuesto) {
		this.proyeccionPuesto = proyeccionPuesto;
	}

}
