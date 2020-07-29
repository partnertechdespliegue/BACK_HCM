package com.mitocode.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "planilla_historica_dsct_remu")
public class PlanillaHistoricaDsctRemu {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer iidPlanillaHistoricaDsctRemu;
	
	@ManyToOne
	@JoinColumn(name = "iid_remuneraciones", nullable = true, foreignKey = @ForeignKey(name = "fk_remuneraciones"))
	private Remuneraciones remuneraciones;
	
	@ManyToOne
	@JoinColumn(name = "iid_descuentos", nullable = true, foreignKey = @ForeignKey(name = "fk_descuentos"))
	private Descuentos descuentos;
	
	@ManyToOne
	@JoinColumn(name = "iid_planilla_historico", nullable = false, foreignKey = @ForeignKey(name = "fk_planilla_historico"))
	private PlanillaHistorico planillaHistorico;
	
	@Column(name = "dmonto", nullable = false)
	private Double dmonto;

	public Integer getIidPlanillaHistoricaDsctRemu() {
		return iidPlanillaHistoricaDsctRemu;
	}

	public void setIidPlanillaHistoricaDsctRemu(Integer iidPlanillaHistoricaDsctRemu) {
		this.iidPlanillaHistoricaDsctRemu = iidPlanillaHistoricaDsctRemu;
	}

	public Remuneraciones getRemuneraciones() {
		return remuneraciones;
	}

	public void setRemuneraciones(Remuneraciones remuneraciones) {
		this.remuneraciones = remuneraciones;
	}

	public Descuentos getDescuentos() {
		return descuentos;
	}

	public void setDescuentos(Descuentos descuentos) {
		this.descuentos = descuentos;
	}

	public PlanillaHistorico getPlanillaHistorico() {
		return planillaHistorico;
	}

	public void setPlanillaHistorico(PlanillaHistorico planillaHistorico) {
		this.planillaHistorico = planillaHistorico;
	}

	public Double getDmonto() {
		return dmonto;
	}

	public void setDmonto(Double dmonto) {
		this.dmonto = dmonto;
	}
	
}
