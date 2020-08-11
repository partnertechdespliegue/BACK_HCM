package com.mitocode.model;



import java.sql.Timestamp;

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
@Table(name = "postulante")
public class Postulante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_postulante;
	
	
	@Column(name = "nombres", nullable = false, length = 150)
	private String nombres;
	
	
	@Column(name = "ape_paterno", nullable = false, length = 100)
	private String ape_paterno;
	
	
	@Column(name = "ape_materno", nullable = false, length = 100)
	private String ape_materno;
		
	
	@Column(name = "fecha_nacimiento", nullable = false)
	private Timestamp fecha_nacimiento;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idTipoDoc", nullable = false)
	private TipoDoc idTipoDoc;
	
	
	@Column(name = "nro_documento", nullable = false)
	private Integer nro_documento;	
	
	
	@Column(name = "telefono", nullable = false)
	private Integer telefono;
	
	@Column(name = "correo", nullable = false, length = 100)
	private String correo;
	
	@Column(name = "tiempo_experiencia", nullable = false)
	private Integer tiempo_experiencia;
	
	@Column(name = "unidad_tiempo", nullable = true, length = 1)
	private String unidad_tiempo;
	
	@Column(name="pretension", nullable = false)
	private Double pretension;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idNivelEdu", nullable = false)
	private NivelEdu idNivelEdu;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idOcupacion", nullable = false)
	private Ocupacion idOcupacion;
	
	@Column(name = "comentarios", nullable = false, length = 10000)
	private String comentarios;
	
	@JsonIgnore
	@Column(name = "estado", nullable = false)
	private Integer estado;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_reclutamiento", nullable=false)
	private Reclutamiento reclutamiento;


	public Integer getId_postulante() {
		return id_postulante;
	}

	public void setId_postulante(Integer id_postulante) {
		this.id_postulante = id_postulante;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApe_paterno() {
		return ape_paterno;
	}

	public void setApe_paterno(String ape_paterno) {
		this.ape_paterno = ape_paterno;
	}

	public String getApe_materno() {
		return ape_materno;
	}

	public void setApe_materno(String ape_materno) {
		this.ape_materno = ape_materno;
	}

	public Timestamp getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Timestamp fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}



	public Ocupacion getIdOcupacion() {
		return idOcupacion;
	}

	public void setIdOcupacion(Ocupacion idOcupacion) {
		this.idOcupacion = idOcupacion;
	}

	public TipoDoc getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setIdTipoDoc(TipoDoc idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}

	public Integer getNro_documento() {
		return nro_documento;
	}

	public void setNro_documento(Integer nro_documento) {
		this.nro_documento = nro_documento;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Integer getTiempo_experiencia() {
		return tiempo_experiencia;
	}

	public void setTiempo_experiencia(Integer tiempo_experiencia) {
		this.tiempo_experiencia = tiempo_experiencia;
	}

	public String getUnidad_tiempo() {
		return unidad_tiempo;
	}

	public void setUnidad_tiempo(String unidad_tiempo) {
		this.unidad_tiempo = unidad_tiempo;
	}

	public Double getPretension() {
		return pretension;
	}

	public void setPretension(Double pretension) {
		this.pretension = pretension;
	}



	public NivelEdu getIdNivelEdu() {
		return idNivelEdu;
	}

	public void setIdNivelEdu(NivelEdu idNivelEdu) {
		this.idNivelEdu = idNivelEdu;
	}



	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Reclutamiento getReclutamiento() {
		return reclutamiento;
	}

	public void setReclutamiento(Reclutamiento reclutamiento) {
		this.reclutamiento = reclutamiento;
	}

	
}
