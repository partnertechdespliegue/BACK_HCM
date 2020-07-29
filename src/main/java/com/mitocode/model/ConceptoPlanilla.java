package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Table(name="concepto_planilla")

public class ConceptoPlanilla {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer iidConceptoPlanilla;
	
	@NotNull(message="No se ha indicado una descripcion para el concepto planilla")
	@NotEmpty(message="La descripcion del concepto planilla  no puede estar vacio")
	@Length (message="La descripcion del concepto planilla no debe exceder los 100 caracteres", min=0,max=100)
	@Column (name="sdescripcion",nullable = false, length= 100)
	private String  sdescripcion;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_haber_provision", nullable = true)
	private CuentaContable  cuentaHaberProvision;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_debe_provision", nullable = true)
	private CuentaContable  cuentaDebeProvision;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_haber_pago", nullable = true)
	private CuentaContable  cuentaHaberPago;
	
	@ManyToOne
	@JoinColumn(name = "cuenta_debe_pago", nullable = true)
	private CuentaContable  cuentaDebePago;
	
	@ManyToOne
	@JoinColumn(name = "id_empresa", nullable = true)
	private Empresa empresa;
	
	@OneToOne
	@JoinColumn (name="id_remuneracion", nullable= true)
	private Remuneraciones remuneracion;
	
	@Column (name="columnaBD", nullable = true)
	private String scolumnaBD;

	public Integer getIidConceptoPlanilla() {
		return iidConceptoPlanilla;
	}

	public void setIidConceptoPlanilla(Integer iidConceptoPlanilla) {
		this.iidConceptoPlanilla = iidConceptoPlanilla;
	}

	public String getSdescripcion() {
		return sdescripcion;
	}

	public void setSdescripcion(String sdescripcion) {
		this.sdescripcion = sdescripcion;
	}

	public CuentaContable getCuentaHaberProvision() {
		return cuentaHaberProvision;
	}

	public void setCuentaHaberProvision(CuentaContable cuentaHaberProvision) {
		this.cuentaHaberProvision = cuentaHaberProvision;
	}

	public CuentaContable getCuentaDebeProvision() {
		return cuentaDebeProvision;
	}

	public void setCuentaDebeProvision(CuentaContable cuentaDebeProvision) {
		this.cuentaDebeProvision = cuentaDebeProvision;
	}

	public CuentaContable getCuentaHaberPago() {
		return cuentaHaberPago;
	}

	public void setCuentaHaberPago(CuentaContable cuentaHaberPago) {
		this.cuentaHaberPago = cuentaHaberPago;
	}

	public CuentaContable getCuentaDebePago() {
		return cuentaDebePago;
	}

	public void setCuentaDebePago(CuentaContable cuentaDebePago) {
		this.cuentaDebePago = cuentaDebePago;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Remuneraciones getRemuneracion() {
		return remuneracion;
	}

	public void setRemuneracion(Remuneraciones remuneracion) {
		this.remuneracion = remuneracion;
	}

	public String getScolumnaBD() {
		return scolumnaBD;
	}

	public void setScolumnaBD(String scolumnaBD) {
		this.scolumnaBD = scolumnaBD;
	}
	
	
	
}
