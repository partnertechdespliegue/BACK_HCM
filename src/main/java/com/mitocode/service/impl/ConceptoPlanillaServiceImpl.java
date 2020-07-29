package com.mitocode.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.dto.EmpresaDTO;
import com.mitocode.model.ConceptoPlanilla;
import com.mitocode.model.CuentaContable;
import com.mitocode.model.Empresa;
import com.mitocode.repo.ConceptoPlanillaRepo;
import com.mitocode.repo.PlanillaHistoricaRepo;
import com.mitocode.service.ConceptoPlanillaService;



@Service
public class ConceptoPlanillaServiceImpl implements ConceptoPlanillaService {
	
	@Autowired
	ConceptoPlanillaRepo repo;
	
	@Autowired
	PlanillaHistoricaRepo repo_planilla;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);
	@Override
	public ConceptoPlanilla registrar(ConceptoPlanilla obj) {
		
		try {
			return repo.save(obj);
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + "registrarConceptoPlanilla. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public ConceptoPlanilla modificar(ConceptoPlanilla obj) {
		
		try {
			return repo.save(obj);
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + "modificarConceptoPlanilla. ERROR : " + e.getMessage());
			throw e;
		}

	}

	@Override
	public ConceptoPlanilla leer(Integer id) {
		
		return null;
	}

	@Override
	public List<ConceptoPlanilla> listar() {
		
		return null;
	}

	@Override
	public Boolean eliminar(Integer id) {
		
		try {
			Boolean existe = repo.existsById(id);
			if(existe) {
				repo.deleteById(id);
			}return existe;			
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+ "eliminarConceptoPlanilla. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<ConceptoPlanilla> listarxEmpresa(Empresa empresa) {
		
		try {
			return repo.findByEmpresa(empresa);
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+ "listarConceptoPlanillaPorEmpresa. ERROR : " + e.getMessage());
			throw e;		
		}
	}

	@Override
	public ConceptoPlanilla buscarConceptoPlanillaxDes(String conceptoPlanilla) {
		
		try {
			return repo.findBySdescripcion(conceptoPlanilla);
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+ "buscarConceptoPlanillaPorDescripcion. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Double listarProvisionDebe(EmpresaDTO empresaDTO, CuentaContable ctaContable) {
		
		try {
			Double totProvDebe = 0.0;
			List<ConceptoPlanilla> lsConcPlaDebe = new ArrayList<>();
			lsConcPlaDebe = repo.findByCuentaDebeProvision(ctaContable);
			Double aux = 0.0;
			
			for(ConceptoPlanilla cp : lsConcPlaDebe ) {
				
				String cpColumnaBD = cp.getScolumnaBD();
				
				switch(cpColumnaBD) 
				{
					case "rem_dia_ferdo_labo":						
						aux=repo_planilla.sumRemDiaFerLab(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "rem_ho_ext25":						
						aux=repo_planilla.sumRemHoExt25(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "rem_ho_ext35":						
						aux=repo_planilla.sumRemHoExt35(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "rem_grati":						
						aux=repo_planilla.sumRemGrati(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "rem_comisiones":						
						aux=repo_planilla.sumRemComi(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "rem_vaca_vend":						
						aux=repo_planilla.sumRemVacaVend(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "rem_jor_norm":						
						aux=repo_planilla.sumRemJorNorm(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "asig_fam":						
						aux=repo_planilla.sumAsigFam(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "rem_dia_vaca":						
						aux=repo_planilla.sumRemDiaVaca(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "bonif29351":						
						aux=repo_planilla.sumBonif29351(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "movilidad":						
						aux=repo_planilla.sumMovilidad(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "rem_cts":						
						aux=repo_planilla.sumRemCts(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "tot_aport_afp":						
						aux=repo_planilla.sumTotAporAfp(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "dsct_onp":						
						aux=repo_planilla.sumDsctOnp(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "dsct_tardanza":						
						aux=repo_planilla.sumDsctTardan(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "dsct_5ta_cat":						
						aux=repo_planilla.sumDsct5taCat(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "essalud_vida":						
						aux=repo_planilla.sumEssaludVida(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "aport_eps":						
						aux=repo_planilla.sumAportEps(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "mon_adela":						
						aux=repo_planilla.sumMontAdelanto(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "apor_essalud":						
						aux=repo_planilla.sumAportEssalud(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;
					case "sctr":						
						aux=repo_planilla.sumSctr(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvDebe= totProvDebe+aux;
						break;						
				}
			}
			return totProvDebe;
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+ "listarProvisionDebe. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Double listarProvisionHaber(EmpresaDTO empresaDTO, CuentaContable ctaContable) {
		
		try {
			Double totProvHaber = 0.0;
			List<ConceptoPlanilla> lsConcPlaHaber = new ArrayList<>();
			lsConcPlaHaber = repo.findByCuentaHaberProvision(ctaContable);
			Double aux = 0.0;
			
			for(ConceptoPlanilla cp : lsConcPlaHaber ) {
				
				String cpColumnaBD = cp.getScolumnaBD();
				
				switch(cpColumnaBD) 
				{
					case "rem_dia_ferdo_labo":						
						aux=repo_planilla.sumRemDiaFerLab(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "rem_ho_ext25":						
						aux=repo_planilla.sumRemHoExt25(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "rem_ho_ext35":						
						aux=repo_planilla.sumRemHoExt35(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "rem_grati":						
						aux=repo_planilla.sumRemGrati(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "rem_comisiones":						
						aux=repo_planilla.sumRemComi(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "rem_vaca_vend":						
						aux=repo_planilla.sumRemVacaVend(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "rem_jor_norm":						
						aux=repo_planilla.sumRemJorNorm(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "asig_fam":						
						aux=repo_planilla.sumAsigFam(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "rem_dia_vaca":						
						aux=repo_planilla.sumRemDiaVaca(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "bonif29351":						
						aux=repo_planilla.sumBonif29351(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "movilidad":						
						aux=repo_planilla.sumMovilidad(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "rem_cts":						
						aux=repo_planilla.sumRemCts(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "tot_aport_afp":						
						aux=repo_planilla.sumTotAporAfp(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "dsct_onp":						
						aux=repo_planilla.sumDsctOnp(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "dsct_tardanza":						
						aux=repo_planilla.sumDsctTardan(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "dsct_5ta_cat":						
						aux=repo_planilla.sumDsct5taCat(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "essalud_vida":						
						aux=repo_planilla.sumEssaludVida(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "aport_eps":						
						aux=repo_planilla.sumAportEps(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "mon_adela":						
						aux=repo_planilla.sumMontAdelanto(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "apor_essalud":						
						aux=repo_planilla.sumAportEssalud(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
					case "sctr":						
						aux=repo_planilla.sumSctr(empresaDTO.getPdoAno().getIdPdoAno(),empresaDTO.getPdoMes().getIdPdoMes());
						totProvHaber= totProvHaber+aux;
						break;
				}

				
			}return totProvHaber;
		
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+ "listarProvisionHaber. ERROR : " + e.getMessage());
			throw e;
		}
	}	

}
