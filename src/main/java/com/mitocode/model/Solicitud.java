package com.mitocode.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="solicitud")

public class Solicitud {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer iidSolicitud;
	
	@ManyToOne
	@JoinColumn (name="id_puesto", nullable=false)
	private Puesto puesto;
	
	@ManyToOne
	@JoinColumn (name="id_trabajador", nullable=false)
	private Trabajador trabajador;
	
	@Column (name="banda",nullable = true)
	private double dbanda;
	
	@Column (name="motivos",nullable = false)
	private String smotivos;
	
	@Column (name="estado",nullable = false)
	private Integer iestado;
	
	@Column (name="fecha_inicio",nullable = false)
	private Timestamp tfechaInicio;

	public Integer getIidSolicitud() {
		return iidSolicitud;
	}

	public void setIidSolicitud(Integer iidSolicitud) {
		this.iidSolicitud = iidSolicitud;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public double getDbanda() {
		return dbanda;
	}

	public void setDbanda(double dbanda) {
		this.dbanda = dbanda;
	}

	public String getSmotivos() {
		return smotivos;
	}

	public void setSmotivos(String smotivos) {
		this.smotivos = smotivos;
	}

	public Integer getIestado() {
		return iestado;
	}

	public void setIestado(Integer iestado) {
		this.iestado = iestado;
	}

	public Timestamp getTfechaInicio() {
		return tfechaInicio;
	}

	public void setTfechaInicio(Timestamp tfechaInicio) {
		this.tfechaInicio = tfechaInicio;
	}
	
	
}
