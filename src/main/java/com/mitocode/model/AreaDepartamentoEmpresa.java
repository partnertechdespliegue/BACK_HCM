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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class AreaDepartamentoEmpresa {

	// DepartamentoEmpresa --> AreaDepartamentoEmpresa
	// empresa -> departamentoEmpresa
	// iidDepartamentoEmpresa -> iid_areaDepartamentoEmpresa

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iidAreaDepartamentoEmpresa;
	
	@Column
	private String snombre;

	@Column
	private Integer iestado;
	
	@ManyToOne
	@JoinColumn(name = "id_departamentoEmpresa", nullable = false)
	private DepartamentoEmpresa departamentoEmpresa;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "areaDepEmp")
	private List<Contrato> lsContrato;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "areaDepartamentoEmpresa")
	private List<Puesto> lspuesto;


	public DepartamentoEmpresa getDepartamentoEmpresa() {
		return departamentoEmpresa;
	}

	public void setDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
		this.departamentoEmpresa = departamentoEmpresa;
	}

	public Integer getIidAreaDepartamentoEmpresa() {
		return iidAreaDepartamentoEmpresa;
	}

	public void setIidAreaDepartamentoEmpresa(Integer iidAreaDepartamentoEmpresa) {
		this.iidAreaDepartamentoEmpresa = iidAreaDepartamentoEmpresa;
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
