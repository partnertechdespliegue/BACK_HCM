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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="cuenta_contable")

public class CuentaContable {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer iidCuentaContable;
	
	@NotNull(message="No se ha indicado una descripcion para la cuenta contable")
	@NotEmpty(message="La descripcion de la cuenta contable no puede estar vacio")
	@Length (message="La descripcion de la cuenta contable no debe exceder los 100 caracteres", min=0,max=100)
	@Column (name="descripcion",nullable = false, length= 100)
	private String  sdescripcion;
	
	@NotNull(message="El codigo de la cuenta contable no puede estar vacio")
	@Column (name="codigo_cuenta",nullable = false)
	private Integer icodigoCuenta;
	
	@ManyToOne
	@JoinColumn (name="id_empresa", nullable=false)
	private Empresa empresa;
	
	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "cuentaHaberProvision")
	private List<ConceptoPlanilla> lsconPlanHaProv;
	
	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "cuentaDebeProvision")
	private List<ConceptoPlanilla> lsconPlanDeProv;
	
	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "cuentaHaberPago")
	private List<ConceptoPlanilla> lsconPlanHaPag;
	
	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "cuentaDebePago")
	private List<ConceptoPlanilla> lsconPlanDePag;
	
	public Integer getIidCuentaContable() {
		return iidCuentaContable;
	}

	public void setIidCuentaContable(Integer iidCuentaContable) {
		this.iidCuentaContable = iidCuentaContable;
	}

	public String getSdescripcion() {
		return sdescripcion;
	}

	public void setSdescripcion(String sdescripcion) {
		this.sdescripcion = sdescripcion;
	}

	public Integer getIcodigoCuenta() {
		return icodigoCuenta;
	}

	public void setIcodigoCuenta(Integer icodigoCuenta) {
		this.icodigoCuenta = icodigoCuenta;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<ConceptoPlanilla> getLsconPlanHaProv() {
		return lsconPlanHaProv;
	}

	public void setLsconPlanHaProv(List<ConceptoPlanilla> lsconPlanHaProv) {
		this.lsconPlanHaProv = lsconPlanHaProv;
	}

	public List<ConceptoPlanilla> getLsconPlanDeProv() {
		return lsconPlanDeProv;
	}

	public void setLsconPlanDeProv(List<ConceptoPlanilla> lsconPlanDeProv) {
		this.lsconPlanDeProv = lsconPlanDeProv;
	}

	public List<ConceptoPlanilla> getLsconPlanHaPag() {
		return lsconPlanHaPag;
	}

	public void setLsconPlanHaPag(List<ConceptoPlanilla> lsconPlanHaPag) {
		this.lsconPlanHaPag = lsconPlanHaPag;
	}

	public List<ConceptoPlanilla> getLsconPlanDePag() {
		return lsconPlanDePag;
	}

	public void setLsconPlanDePag(List<ConceptoPlanilla> lsconPlanDePag) {
		this.lsconPlanDePag = lsconPlanDePag;
	}

	

	
	
	

}
