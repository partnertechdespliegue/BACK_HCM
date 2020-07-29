package com.mitocode.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.mitocode.model.Afp;
import com.mitocode.model.Banco;
import com.mitocode.model.CentroCosto;
import com.mitocode.model.ConceptoPlanilla;
import com.mitocode.model.CuentaCargo;
import com.mitocode.model.Descuentos;
import com.mitocode.model.Empresa;
import com.mitocode.model.EncargadoPlanilla;
import com.mitocode.model.Parametro;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.RegimenTributario;
import com.mitocode.model.Remuneraciones;
import com.mitocode.model.TipoEmpresa;
import com.mitocode.model.TipoPermiso;
import com.mitocode.model.TipoPlanilla;
import com.mitocode.model.CuentaContable;


public class EmpresaDTO {
	
	@NotNull(message="No se ha especificado una empresa")
	private Empresa empresa;
	
	private Parametro parametro;
	
	
	private CentroCosto centroCosto;
	
	private Afp afp;
	
	private TipoEmpresa tipoEmpresa;
	
	private RegimenTributario regTributario;
	
	private TipoPermiso tipoPermiso;
	
	private EncargadoPlanilla encargadoPlanilla;
	
	private CuentaCargo cuentaCargo;
	
	private Banco banco;
	
	private Remuneraciones remuneraciones;
	
	private Descuentos descuentos;	

	private CuentaContable cuentaContable;

	private ConceptoPlanilla conceptoPlanilla;
	
	private TipoPlanilla tipoPlanilla;
	
	private PdoAno pdoAno;
	
	private PdoMes pdoMes;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public TipoPermiso getTipoPermiso() {
		return tipoPermiso;
	}

	public void setTipoPermiso(TipoPermiso tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public Afp getAfp() {
		return afp;
	}

	public void setAfp(Afp afp) {
		this.afp = afp;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public RegimenTributario getRegTributario() {
		return regTributario;
	}

	public void setRegTributario(RegimenTributario regTributario) {
		this.regTributario = regTributario;
	}

	public EncargadoPlanilla getEncargadoPlanilla() {
		return encargadoPlanilla;
	}

	public void setEncargadoPlanilla(EncargadoPlanilla encargadoPlanilla) {
		this.encargadoPlanilla = encargadoPlanilla;
	}

	public CuentaCargo getCuentaCargo() {
		return cuentaCargo;
	}

	public void setCuentaCargo(CuentaCargo cuentaCargo) {
		this.cuentaCargo = cuentaCargo;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
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


	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public ConceptoPlanilla getConceptoPlanilla() {
		return conceptoPlanilla;
	}

	public void setConceptoPlanilla(ConceptoPlanilla conceptoPlanilla) {
		this.conceptoPlanilla = conceptoPlanilla;
	}
	
	public PdoAno getPdoAno() {
		return pdoAno;
	}

	public void setPdoAno(PdoAno pdoAno) {
		this.pdoAno = pdoAno;
	}

	public PdoMes getPdoMes() {
		return pdoMes;
	}

	public void setPdoMes(PdoMes pdoMes) {
		this.pdoMes = pdoMes;
	}

	public TipoPlanilla getTipoPlanilla() {
		return tipoPlanilla;
	}

	public void setTipoPlanilla(TipoPlanilla tipoPlanilla) {
		this.tipoPlanilla = tipoPlanilla;
	}

	
}
