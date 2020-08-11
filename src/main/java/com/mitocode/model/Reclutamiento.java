package com.mitocode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reclutamiento")
public class Reclutamiento {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer iidReclutamiento;
	
	@OneToOne
	@JoinColumn (name="id_solicitud", nullable=true)
	private Solicitud solicitud;

	public Integer getIidReclutamiento() {
		return iidReclutamiento;
	}

	public void setIidReclutamiento(Integer iidReclutamiento) {
		this.iidReclutamiento = iidReclutamiento;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}



}
