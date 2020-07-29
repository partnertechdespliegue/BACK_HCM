package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "proyeccion")

public class ProyeccionPuesto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer iidProyeccion;

	@Column(name = "iorden", nullable = false)
	private Integer iorden;

	@OneToOne
	@JoinColumn(name = "id_puesto", nullable = true)
	private Puesto puesto;

	@OneToOne
	@JoinColumn(name = "id_puesto_proyeccion", nullable = true)
	private Puesto puestoProyeccion;

	public Integer getIidProyeccion() {
		return iidProyeccion;
	}

	public void setIidProyeccion(Integer iidProyeccion) {
		this.iidProyeccion = iidProyeccion;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public Integer getIorden() {
		return iorden;
	}

	public void setIorden(Integer orden) {
		this.iorden = orden;
	}

	public Puesto getPuestoProyeccion() {
		return puestoProyeccion;
	}

	public void setPuestoProyeccion(Puesto puestoProyeccion) {
		this.puestoProyeccion = puestoProyeccion;
	}

}
