package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "huellero_digital")
public class HuelleroDigital {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "iid_huellero_digital", nullable = false)	
	private Integer iidHuelleroDigital;
	
	@Column(name = "sip_privada", nullable = false)
	private String sipPrivada;
	
	@Column(name = "sip_publica", nullable = false)
	private String sipPublica;
	
	@ManyToOne
	@JoinColumn(name="iid_empresa", nullable=true)
	private Empresa empresa;

	public Integer getIidHuelleroDigital() {
		return iidHuelleroDigital;
	}

	public void setIidHuelleroDigital(Integer iidHuelleroDigital) {
		this.iidHuelleroDigital = iidHuelleroDigital;
	}

	public String getSipPrivada() {
		return sipPrivada;
	}

	public void setSipPrivada(String sipPrivada) {
		this.sipPrivada = sipPrivada;
	}

	public String getSipPublica() {
		return sipPublica;
	}

	public void setSipPublica(String sipPublica) {
		this.sipPublica = sipPublica;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
}
