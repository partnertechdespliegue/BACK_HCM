package com.mitocode.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
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
@Table 
public class DepartamentoEmpresa { 
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iidDepartamentoEmpresa;
	
	@Column(nullable = false)
	private String snombre;
	
	@Column(nullable = false)
	private Integer iestado;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "departamentoEmpresa")
	private List<AreaDepartamentoEmpresa> lsareadepartamentoempresa;
	
	//@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_empresa", nullable = false, foreignKey = @ForeignKey(name="FK_Empresa"))
	private Empresa empresa;
	
	@OneToOne
	@JoinColumn(name="iid_gerente", nullable = true, foreignKey = @ForeignKey(name="FK_Trabajador"))
	private Trabajador gerente;

	public List<AreaDepartamentoEmpresa> getLsareadepartamentoempresa() {
		return lsareadepartamentoempresa;
	}
	public Trabajador getGerente() {
		return gerente;
	}
	public void setLsareadepartamentoempresa(List<AreaDepartamentoEmpresa> lsareadepartamentoempresa) {
		this.lsareadepartamentoempresa = lsareadepartamentoempresa;
	}

	public void setGerente(Trabajador gerente) {
		this.gerente = gerente;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public Integer getIidDepartamentoEmpresa() {
		return iidDepartamentoEmpresa;
	}
	public void setIidDepartamentoEmpresa(Integer iidDepartamentoEmpresa) {
		this.iidDepartamentoEmpresa = iidDepartamentoEmpresa;
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
}
