package com.mitocode.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.Afp;
import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.ConceptoPlanilla;
import com.mitocode.model.CuentaContable;
import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.Empresa;
import com.mitocode.model.Eps;
import com.mitocode.model.Parametro;
import com.mitocode.model.PlanillaHistorico;
import com.mitocode.model.ProyeccionPuesto;
import com.mitocode.model.Puesto;
import com.mitocode.model.TipoPermiso;
import com.mitocode.repo.AfpRepo;
import com.mitocode.repo.AreaDepartamentoEmpresaRepo;
import com.mitocode.repo.ConceptoPlanillaRepo;
import com.mitocode.repo.CuentaContableRepo;
import com.mitocode.repo.DepartamentoEmpresaRepo;
import com.mitocode.repo.EmpresaRepo;
import com.mitocode.repo.EpsRepo;
import com.mitocode.repo.ParametroRepo;
import com.mitocode.repo.ProyeccionPuestoRepo;
import com.mitocode.repo.PuestoRepo;
import com.mitocode.repo.TipoPermisoRepo;
import com.mitocode.service.EmpresaService;
import com.mitocode.util.Constantes;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private EmpresaRepo repo;
	
	@Autowired
	private ParametroRepo repo_para;
	
	@Autowired
	private AfpRepo repo_afp;
	
	@Autowired
	private EpsRepo repo_eps;
	
	@Autowired
	private CuentaContableRepo repo_ctaContable;
	
	@Autowired
	private ConceptoPlanillaRepo repo_concepPlanilla;
	
	@Autowired
	private TipoPermisoRepo repo_tipoPermiso;
	
	@Autowired
	private DepartamentoEmpresaRepo repo_depEmp;
	
	@Autowired
	private AreaDepartamentoEmpresaRepo repo_areaDepEmp;
	
	@Autowired
	private PuestoRepo repo_puesto;
	
	@Autowired
	private ProyeccionPuestoRepo repo_proy;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);
	
	
 
	@Override
	public Empresa registrar(Empresa obj) {
		try {
			Date fecha_hoy=new Date();
			Timestamp tm_fec_hoy=new Timestamp(fecha_hoy.getTime());
			Empresa res_emp = new Empresa();
			obj.setFechaRegistro(tm_fec_hoy);
			obj.setEstado(Constantes.ConsActivo);
			res_emp = repo.save(obj);
			
			crearCuentasContables(res_emp);			
		
			crearConceptoPlanilla(res_emp);
			
			crearAreaEmpresaDepPues(res_emp);
			
			//crearPuesto();
			

			Afp res_afp = guardarAfpDefecto(res_emp);
			
			List<Eps> tmp_eps = new ArrayList<>();
			Eps eps1 = this.crearEps("COLSANITAS PERU S.A.  EPS",0.0225,obj);
			Eps eps2 = this.crearEps("MAPFRE PERU S.A. EPS",0.0225,obj);
			Eps eps3 = this.crearEps("PACÍFICO S.A. EPS",0.0225,obj);
			Eps eps4 = this.crearEps("PERSALUD S.A. EPS",0.0225,obj);
			Eps eps5 = this.crearEps("RÍMAC INTERNACIONAL S.A. EPS",0.0225,obj);
			Eps eps6 = this.crearEps("SERVICIOS PROPIOS",0.0225,obj);
			tmp_eps.add(eps1);
			tmp_eps.add(eps2);
			tmp_eps.add(eps3);
			tmp_eps.add(eps4);
			tmp_eps.add(eps5);
			tmp_eps.add(eps6);
			repo_eps.saveAll(tmp_eps);
			
			List<TipoPermiso> tmp_permisos = new ArrayList<>();
			TipoPermiso tp1 = this.CrearTipoPermiso("Maternidad",true,98,obj);
			TipoPermiso tp2 = this.CrearTipoPermiso("Paternidad caso Parto Normal/Cesarea",true,10,obj);
			TipoPermiso tp3 = this.CrearTipoPermiso("Paternidad caso Parto Prematuro/Multpile",true,20,obj);
			TipoPermiso tp4 = this.CrearTipoPermiso("Paternidad caso Complicaciones Graves en Madre/Hijo",true,30,obj);
			TipoPermiso tp5 = this.CrearTipoPermiso("Fallecimiento",true,null,obj);
			TipoPermiso tp6 = this.CrearTipoPermiso("Descanso Médico",true,null,obj);
			tmp_permisos.add(tp1);
			tmp_permisos.add(tp2);
			tmp_permisos.add(tp3);
			tmp_permisos.add(tp4);
			tmp_permisos.add(tp5);
			tmp_permisos.add(tp6);
			repo_tipoPermiso.saveAll(tmp_permisos);
			
		
			for (int i = 0; i < Constantes.ConstarEmpr; i++) {
				
				Parametro p = new Parametro();
				
				switch (i) {
				/**
				 * En este primer case se agrega el Salario minimo vital
				 */
				case 0:
					p.setDescripcion(Constantes.DESSALMINVIT);
					p.setCodigo(Constantes.CODSALMINVIT);
					p.setValor(Constantes.VALSALMINVIT);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESSALMINVIT);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;
				/**
				 * En el segundo case se agrega la Aportacion EsSalud
				 */
				case 1:
					p.setDescripcion(Constantes.DESESSALUD);
					p.setCodigo(Constantes.CODESSALUD);
					p.setValor(Constantes.VALESSALUD);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESESSALUD);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;

				/**
				 * En el tercer case se agrega la Unidad Impositiva Tributaria
				 */
				case 2:
					p.setDescripcion(Constantes.DESUIT);
					p.setCodigo(Constantes.CODUIT);
					p.setValor(Constantes.VALUIT);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESUIT);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;

				/**
				 * En el cuarto case se agrega la Bonificacion Extraordinaria Ley 29351
				 */
				case 3:
					p.setDescripcion(Constantes.DESBONEXT29351);
					p.setCodigo(Constantes.CODBONEXT29351);
					p.setValor(Constantes.VALBONEXT29351);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESBONEXT29351);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;

				/**
				 * En el quinto case se agrega el Monto de EsSalud+Vida
				 */
				case 4:
					p.setDescripcion(Constantes.DESESSALUDVIDA);
					p.setCodigo(Constantes.CODESSALUDVIDA);
					p.setValor(Constantes.VALESSALUDVIDA);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESESSALUDVIDA);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;
				/**
				 * En el sexto case se agrega el porcentaje interes CTS
				 */
				case 5:
					p.setDescripcion(Constantes.PORCINCTS);
					p.setCodigo(Constantes.CODPORCINCTS);
					p.setValor(Constantes.VALPORCINCTS);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.PORCINCTS);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;
				/**
				 * En el septimo case se agrega las horas de jornada laboral
				*/
				case 6:
					p.setDescripcion(Constantes.DESHORTRAB);
					p.setCodigo(Constantes.CODHORTRAB);
					p.setValor(Constantes.VALHORTRAB);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESHORTRAB);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;
				
				case 7:
					p.setDescripcion(Constantes.DESTIPTARD);
					p.setCodigo(Constantes.CODTIPTARD);
					p.setValor(Constantes.VALTIPTARD);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESTIPTARD);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;
					
				case 8:
					p.setDescripcion(Constantes.DESTARCNTDIAS);
					p.setCodigo(Constantes.CODTARCNTDIAS);
					p.setValor(Constantes.VALTARCNTDIAS);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESTARCNTDIAS);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;
					
				case 9:
					p.setDescripcion(Constantes.DESTIPORANGO);
					p.setCodigo(Constantes.CODTIPORANGO);
					p.setValor(Constantes.VALTIPORANGO);
					p.setEstado(0);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESTIPORANGO);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;
				
				case 10:
					Integer id_onp = res_afp.getIdAfp();
					p.setDescripcion(Constantes.DESONP);
					p.setCodigo(Constantes.CODONP);
					p.setValor(String.valueOf(id_onp));
					p.setEstado(0);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESONP);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;					
				case 11:
					p.setDescripcion(Constantes.DESTIEMTOLE);
					p.setCodigo(Constantes.CODTIEMTOLE);
					p.setValor(Constantes.VALTIEMTOLE);
					p.setEstado(1);
					p.setGrupo(Constantes.GRPEMPRESA);
					p.setNombre(Constantes.DESTIEMTOLE);
					p.setEmpresa(res_emp);
					repo_para.save(p);
					break;

				default:
					break;
				}
			}
			return res_emp;
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " registrarEmpresa. ERROR : " + e.getMessage());
			throw e;
		}
	}
	
	
	private void crearAreaEmpresaDepPues(Empresa res_emp) {
		
		DepartamentoEmpresa depEmp = new DepartamentoEmpresa();
		DepartamentoEmpresa res_depEmp = new DepartamentoEmpresa();
		
		AreaDepartamentoEmpresa areDepEmp = new AreaDepartamentoEmpresa();
		AreaDepartamentoEmpresa res_areDepEmp = new AreaDepartamentoEmpresa();
		
		Puesto puesto = new Puesto();
		Puesto res_puesto = new Puesto();
		
		ProyeccionPuesto proy = new ProyeccionPuesto();
		
		
		depEmp.setSnombre("Gerencia");
		depEmp.setIestado(1);
		depEmp.setEmpresa(res_emp);
		
		res_depEmp = repo_depEmp.save(depEmp);
		
		areDepEmp.setSnombre("Gerencial General");
		areDepEmp.setIestado(1);
		areDepEmp.setDepartamentoEmpresa(res_depEmp);
		
		res_areDepEmp = repo_areaDepEmp.save(areDepEmp);
		
		puesto.setSnombre("CEO");
		puesto.setIestado(1);
		puesto.setScategoria("G");
		puesto.setAreaDepartamentoEmpresa(res_areDepEmp);
		
		res_puesto = repo_puesto.save(puesto);
		
		proy.setIorden(1);
		proy.setPuesto(puesto);
		
		repo_proy.save(proy);
		
	}

	private void crearConceptoPlanilla(Empresa res_emp) {
			
			
			ConceptoPlanilla feriadosLabor = new ConceptoPlanilla();
			feriadosLabor.setSdescripcion("Feriados laborados");
			feriadosLabor.setEmpresa(res_emp);
			feriadosLabor.setScolumnaBD("rem_dia_ferdo_labo");
			//planilla.setDiaFerdo(feriadosLabor.getScolumnaBD());
			repo_concepPlanilla.save(feriadosLabor);
						
			ConceptoPlanilla horasExtras25 = new ConceptoPlanilla();
			horasExtras25.setSdescripcion("Horas extras 25%");
			horasExtras25.setEmpresa(res_emp);
			horasExtras25.setScolumnaBD("rem_ho_ext25");
			repo_concepPlanilla.save(horasExtras25);
			
			ConceptoPlanilla horasExtras35 = new ConceptoPlanilla();
			horasExtras35.setSdescripcion("Horas extras 35%");
			horasExtras35.setEmpresa(res_emp);
			horasExtras35.setScolumnaBD("rem_ho_ext35");
			repo_concepPlanilla.save(horasExtras35);
			
			ConceptoPlanilla gratificaciones = new ConceptoPlanilla();
			gratificaciones.setSdescripcion("Gratificaciones");
			gratificaciones.setEmpresa(res_emp);
			gratificaciones.setScolumnaBD("rem_grati");
			repo_concepPlanilla.save(gratificaciones);
			
			ConceptoPlanilla comisiones = new ConceptoPlanilla();
			comisiones.setSdescripcion("Comisiones");
			comisiones.setEmpresa(res_emp);
			comisiones.setScolumnaBD("rem_comisiones");
			repo_concepPlanilla.save(comisiones);
			
			ConceptoPlanilla ventaVacaci = new ConceptoPlanilla();
			ventaVacaci.setSdescripcion("Venta vacaciones");
			ventaVacaci.setEmpresa(res_emp);
			ventaVacaci.setScolumnaBD("rem_vaca_vend");
			repo_concepPlanilla.save(ventaVacaci);
			
			ConceptoPlanilla remJorNormal = new ConceptoPlanilla();
			remJorNormal.setSdescripcion("Remuneracion jornada normal");
			remJorNormal.setEmpresa(res_emp);
			remJorNormal.setScolumnaBD("rem_jor_norm");
			repo_concepPlanilla.save(remJorNormal);
			
			ConceptoPlanilla asigFamiliar = new ConceptoPlanilla();
			asigFamiliar.setSdescripcion("Asignacion familiar");
			asigFamiliar.setEmpresa(res_emp);
			asigFamiliar.setScolumnaBD("asig_fam");
			repo_concepPlanilla.save(asigFamiliar);
			
			ConceptoPlanilla vacaciones = new ConceptoPlanilla();
			vacaciones.setSdescripcion("Vacaciones");
			vacaciones.setEmpresa(res_emp);
			vacaciones.setScolumnaBD("rem_dia_vaca");
			repo_concepPlanilla.save(vacaciones);
			
			ConceptoPlanilla boniLet29351 = new ConceptoPlanilla();
			boniLet29351.setSdescripcion("Bonificacion let 29351");
			boniLet29351.setEmpresa(res_emp);
			boniLet29351.setScolumnaBD("bonif29351");
			repo_concepPlanilla.save(boniLet29351);
			
			ConceptoPlanilla movilidTotal = new ConceptoPlanilla();
			movilidTotal.setSdescripcion("Movilidad total");
			movilidTotal.setEmpresa(res_emp);
			movilidTotal.setScolumnaBD("movilidad");
			repo_concepPlanilla.save(movilidTotal);
			
			ConceptoPlanilla cts = new ConceptoPlanilla();
			cts.setSdescripcion("CTS");
			cts.setEmpresa(res_emp);
			cts.setScolumnaBD("rem_cts");
			repo_concepPlanilla.save(cts);
			
			ConceptoPlanilla afp = new ConceptoPlanilla();
			afp.setSdescripcion("AFP");
			afp.setEmpresa(res_emp);
			afp.setScolumnaBD("tot_aport_afp");
			repo_concepPlanilla.save(afp);
			
			ConceptoPlanilla onp = new ConceptoPlanilla();
			onp.setSdescripcion("ONP");
			onp.setEmpresa(res_emp);
			onp.setScolumnaBD("dsct_onp");
			repo_concepPlanilla.save(onp);
			
			ConceptoPlanilla tardanza = new ConceptoPlanilla();
			tardanza.setSdescripcion("Tardanza");
			tardanza.setEmpresa(res_emp);
			tardanza.setScolumnaBD("dsct_tardanza");
			repo_concepPlanilla.save(tardanza);
			
			ConceptoPlanilla renta5ta = new ConceptoPlanilla();
			renta5ta.setSdescripcion("Renta 5ta");
			renta5ta.setEmpresa(res_emp);
			renta5ta.setScolumnaBD("dsct_5ta_cat");
			repo_concepPlanilla.save(renta5ta);
			
			ConceptoPlanilla esSaludVida = new ConceptoPlanilla();
			esSaludVida.setSdescripcion("EsSalud Vida");
			esSaludVida.setEmpresa(res_emp);
			esSaludVida.setScolumnaBD("essalud_vida");
			repo_concepPlanilla.save(esSaludVida);
			
			ConceptoPlanilla eps = new ConceptoPlanilla();
			eps.setSdescripcion("EPS");
			eps.setEmpresa(res_emp);
			eps.setScolumnaBD("aport_eps");
			repo_concepPlanilla.save(eps);
			
			ConceptoPlanilla adelTrabaj = new ConceptoPlanilla();
			adelTrabaj.setSdescripcion("Adelantos del trabajador");
			adelTrabaj.setEmpresa(res_emp);
			adelTrabaj.setScolumnaBD("mon_adela");
			repo_concepPlanilla.save(adelTrabaj);
			
			ConceptoPlanilla esSalud = new ConceptoPlanilla();
			esSalud.setSdescripcion("EsSalud");
			esSalud.setEmpresa(res_emp);
			esSalud.setScolumnaBD("apor_essalud");
			repo_concepPlanilla.save(esSalud);
			
			ConceptoPlanilla sctr = new ConceptoPlanilla();
			sctr.setSdescripcion("SCTR");
			sctr.setEmpresa(res_emp);
			sctr.setScolumnaBD("sctr");
			repo_concepPlanilla.save(sctr);
		
		
	}


	private void crearCuentasContables(Empresa res_emp) {
		
		// 10 EFECTIVO Y EQUIVALENTES DE EFECTIVO

		CuentaContable caja = new CuentaContable();
		caja.setSdescripcion("Caja");
		caja.setIcodigoCuenta(101);
		caja.setEmpresa(res_emp);		
		repo_ctaContable.save(caja);
		
		CuentaContable fondosFijos = new CuentaContable();
		fondosFijos.setSdescripcion("Fondos fijos");
		fondosFijos.setIcodigoCuenta(102);
		fondosFijos.setEmpresa(res_emp);		
		repo_ctaContable.save(fondosFijos);
		
		CuentaContable efecTransito = new CuentaContable();
		efecTransito.setSdescripcion("Efectivo en tránsito");
		efecTransito.setIcodigoCuenta(1031);
		efecTransito.setEmpresa(res_emp);		
		repo_ctaContable.save(efecTransito);
		
		CuentaContable cheqTransito = new CuentaContable();
		cheqTransito.setSdescripcion("Cheques en tránsito");
		cheqTransito.setIcodigoCuenta(1032);
		cheqTransito.setEmpresa(res_emp);		
		repo_ctaContable.save(cheqTransito);
		
		CuentaContable ctaCorOpe = new CuentaContable();
		ctaCorOpe.setSdescripcion("Cuentas corrientes operativas");
		ctaCorOpe.setIcodigoCuenta(1041);
		ctaCorOpe.setEmpresa(res_emp);		
		repo_ctaContable.save(ctaCorOpe);
		
		CuentaContable ctaCorFinEspe = new CuentaContable();
		ctaCorFinEspe.setSdescripcion("Cuentas corrientes para fines específicos");
		ctaCorFinEspe.setIcodigoCuenta(1042);
		ctaCorFinEspe.setEmpresa(res_emp);		
		repo_ctaContable.save(ctaCorFinEspe);
		
		CuentaContable otroEquEfec = new CuentaContable();
		otroEquEfec.setSdescripcion("Otro equivalentes de efectivo");
		otroEquEfec.setIcodigoCuenta(1051);
		otroEquEfec.setEmpresa(res_emp);		
		repo_ctaContable.save(otroEquEfec);
		
		CuentaContable depAhorro = new CuentaContable();
		depAhorro.setSdescripcion("Depósitos de ahorro");
		depAhorro.setIcodigoCuenta(1061);
		depAhorro.setEmpresa(res_emp);		
		repo_ctaContable.save(depAhorro);
		
		CuentaContable depPlazo = new CuentaContable();
		depPlazo.setSdescripcion("Depósitos a plazo");
		depPlazo.setIcodigoCuenta(1062);
		depPlazo.setEmpresa(res_emp);		
		repo_ctaContable.save(depPlazo);
		
		CuentaContable fonGarant = new CuentaContable();
		fonGarant.setSdescripcion("Fondos en garantía");
		fonGarant.setIcodigoCuenta(1071);
		fonGarant.setEmpresa(res_emp);		
		repo_ctaContable.save(fonGarant);
		
		CuentaContable fonRetMandAut = new CuentaContable();
		fonRetMandAut.setSdescripcion("Fondos retenidos por mandato de la autoridad");
		fonRetMandAut.setIcodigoCuenta(1072);
		fonRetMandAut.setEmpresa(res_emp);		
		repo_ctaContable.save(fonRetMandAut);
		
		CuentaContable otrFonSujRest = new CuentaContable();
		otrFonSujRest.setSdescripcion("Otros fondos sujetos a restricción");
		otrFonSujRest.setIcodigoCuenta(1073);
		otrFonSujRest.setEmpresa(res_emp);		
		repo_ctaContable.save(otrFonSujRest);
		
		//12 CUENTAS POR COBRAR COMERCIALES – TERCEROS
		
		CuentaContable noEmiCtaXCobrCom = new CuentaContable();
		noEmiCtaXCobrCom.setSdescripcion("No emitidas");
		noEmiCtaXCobrCom.setIcodigoCuenta(1211);
		noEmiCtaXCobrCom.setEmpresa(res_emp);		
		repo_ctaContable.save(noEmiCtaXCobrCom);
		
		CuentaContable emitidaCartera = new CuentaContable();
		emitidaCartera.setSdescripcion("Emitidas en cartera");
		emitidaCartera.setIcodigoCuenta(1212);
		emitidaCartera.setEmpresa(res_emp);		
		repo_ctaContable.save(emitidaCartera);
		
		CuentaContable enCobraComp = new CuentaContable();
		enCobraComp.setSdescripcion("En cobranza");
		enCobraComp.setIcodigoCuenta(1213);
		enCobraComp.setEmpresa(res_emp);		
		repo_ctaContable.save(enCobraComp);
		
		CuentaContable enDescComp = new CuentaContable();
		enDescComp.setSdescripcion("En descuento");
		enDescComp.setIcodigoCuenta(1214);
		enDescComp.setEmpresa(res_emp);		
		repo_ctaContable.save(enDescComp);
		
		CuentaContable anticiClien = new CuentaContable();
		anticiClien.setSdescripcion("Anticipos de clientes");
		anticiClien.setIcodigoCuenta(122);
		anticiClien.setEmpresa(res_emp);		
		repo_ctaContable.save(anticiClien);
		
		CuentaContable enCartLet = new CuentaContable();
		enCartLet.setSdescripcion("En cartera");
		enCartLet.setIcodigoCuenta(1232);
		enCartLet.setEmpresa(res_emp);		
		repo_ctaContable.save(enCartLet);
		
		CuentaContable enCobraLet = new CuentaContable();
		enCobraLet.setSdescripcion("En cobranza");
		enCobraLet.setIcodigoCuenta(1233);
		enCobraLet.setEmpresa(res_emp);		
		repo_ctaContable.save(enCobraLet);
		
		CuentaContable enDescLet = new CuentaContable();
		enDescLet.setSdescripcion("En descuento");
		enDescLet.setIcodigoCuenta(1234);
		enDescLet.setEmpresa(res_emp);		
		repo_ctaContable.save(enDescLet);
		
		// 14 CUENTAS X COBRAR AL PERSONAL, A LOS ACCIONISTAS(SOCIOS) y DIRECTORES
		
		CuentaContable prestPer = new CuentaContable();
		prestPer.setSdescripcion("Préstamos");
		prestPer.setIcodigoCuenta(1411);
		prestPer.setEmpresa(res_emp);		
		repo_ctaContable.save(prestPer);
		
		CuentaContable adelRemun = new CuentaContable();
		adelRemun.setSdescripcion("Adelanto de remuneraciones");
		adelRemun.setIcodigoCuenta(1412);
		adelRemun.setEmpresa(res_emp);		
		repo_ctaContable.save(adelRemun);
		
		CuentaContable entRenCtaPer = new CuentaContable();
		entRenCtaPer.setSdescripcion("Entregas a rendir cuenta");
		entRenCtaPer.setIcodigoCuenta(1413);
		entRenCtaPer.setEmpresa(res_emp);		
		repo_ctaContable.save(entRenCtaPer);
		
		CuentaContable otrCtaCobPers = new CuentaContable();
		otrCtaCobPers.setSdescripcion("Otras cuentas por cobrar al personal");
		otrCtaCobPers.setIcodigoCuenta(1419);
		otrCtaCobPers.setEmpresa(res_emp);		
		repo_ctaContable.save(otrCtaCobPers);
		
		CuentaContable suscCobSocAcc = new CuentaContable();
		suscCobSocAcc.setSdescripcion("Suscripciones por cobrar a socios o accionistas");
		suscCobSocAcc.setIcodigoCuenta(1421);
		suscCobSocAcc.setEmpresa(res_emp);		
		repo_ctaContable.save(suscCobSocAcc);
		
		CuentaContable prestAcc = new CuentaContable();
		prestAcc.setSdescripcion("Préstamos");
		prestAcc.setIcodigoCuenta(1422);
		prestAcc.setEmpresa(res_emp);		
		repo_ctaContable.save(prestAcc);
		
		CuentaContable prestDir = new CuentaContable();
		prestDir.setSdescripcion("Préstamos");
		prestDir.setIcodigoCuenta(1431);
		prestDir.setEmpresa(res_emp);		
		repo_ctaContable.save(prestDir);
		
		CuentaContable adelDieta = new CuentaContable();
		adelDieta.setSdescripcion("Adelanto de dietas");
		adelDieta.setIcodigoCuenta(1432);
		adelDieta.setEmpresa(res_emp);		
		repo_ctaContable.save(adelDieta);
		
		CuentaContable entRenCtaDir = new CuentaContable();
		entRenCtaDir.setSdescripcion("Entregas a rendir cuenta");
		entRenCtaDir.setIcodigoCuenta(1433);
		entRenCtaDir.setEmpresa(res_emp);		
		repo_ctaContable.save(entRenCtaDir);
		
		CuentaContable diversas = new CuentaContable();
		diversas.setSdescripcion("Diversas");
		diversas.setIcodigoCuenta(149);
		diversas.setEmpresa(res_emp);		
		repo_ctaContable.save(diversas);
		
		// 40 TRIBUTOS, CONTRAPRESTACIONES Y APORTES AL SISTEMA PÚBLICO DE PENSIONES Y DE SALUD POR PAGAR
		
		CuentaContable igvCtaProp = new CuentaContable();
		igvCtaProp.setSdescripcion("IGV – Cuenta propia");
		igvCtaProp.setIcodigoCuenta(40111);
		igvCtaProp.setEmpresa(res_emp);		
		repo_ctaContable.save(igvCtaProp);
		
		CuentaContable igvSerPresNoDom= new CuentaContable();
		igvSerPresNoDom.setSdescripcion("IGV – Servicios prestados por no domiciliados");
		igvSerPresNoDom.setIcodigoCuenta(40112);
		igvSerPresNoDom.setEmpresa(res_emp);		
		repo_ctaContable.save(igvSerPresNoDom);
		
		CuentaContable igvRegPerc = new CuentaContable();
		igvRegPerc.setSdescripcion("IGV – Régimen de percepciones");
		igvRegPerc.setIcodigoCuenta(40113);
		igvRegPerc.setEmpresa(res_emp);		
		repo_ctaContable.save(igvRegPerc);
		
		CuentaContable igvRegRet = new CuentaContable();
		igvRegRet.setSdescripcion("IGV – Régimen de retenciones");
		igvRegRet.setIcodigoCuenta(40114);
		igvRegRet.setEmpresa(res_emp);		
		repo_ctaContable.save(igvRegRet);
		
		CuentaContable igvCtaImpor = new CuentaContable();
		igvCtaImpor.setSdescripcion("IGV – Importaciones");
		igvCtaImpor.setIcodigoCuenta(40115);
		igvCtaImpor.setEmpresa(res_emp);		
		repo_ctaContable.save(igvCtaImpor);
		
		CuentaContable igvCtaDestOpeGra = new CuentaContable();
		igvCtaDestOpeGra.setSdescripcion("IGV – Destinado a operaciones gravadas");
		igvCtaDestOpeGra.setIcodigoCuenta(40116);
		igvCtaDestOpeGra.setEmpresa(res_emp);		
		repo_ctaContable.save(igvCtaDestOpeGra);

		CuentaContable igvCtaOpeCom = new CuentaContable();
		igvCtaOpeCom.setSdescripcion("IGV - Destinado a operaciones comunes");
		igvCtaOpeCom.setIcodigoCuenta(40117);
		igvCtaOpeCom.setEmpresa(res_emp);		
		repo_ctaContable.save(igvCtaOpeCom);
		
		CuentaContable impSeleCons = new CuentaContable();
		impSeleCons.setSdescripcion("Impuesto selectivo al consumo");
		impSeleCons.setIcodigoCuenta(4012);
		impSeleCons.setEmpresa(res_emp);		
		repo_ctaContable.save(impSeleCons);
		
		CuentaContable derAranc = new CuentaContable();
		derAranc.setSdescripcion("Derechos arancelarios");
		derAranc.setIcodigoCuenta(40151);
		derAranc.setEmpresa(res_emp);		
		repo_ctaContable.save(derAranc);
		
		CuentaContable otrDerAranc = new CuentaContable();
		otrDerAranc.setSdescripcion("Otros derechos arancelarios");
		otrDerAranc.setIcodigoCuenta(40152);
		otrDerAranc.setEmpresa(res_emp);		
		repo_ctaContable.save(otrDerAranc);
		
		CuentaContable renTercCate = new CuentaContable();
		renTercCate.setSdescripcion("Renta de tercera categoría");
		renTercCate.setIcodigoCuenta(40171);
		renTercCate.setEmpresa(res_emp);		
		repo_ctaContable.save(renTercCate);
		
		CuentaContable renCuarCate = new CuentaContable();
		renCuarCate.setSdescripcion("Renta de cuarta categoría");
		renCuarCate.setIcodigoCuenta(40172);
		renCuarCate.setEmpresa(res_emp);		
		repo_ctaContable.save(renCuarCate);
		
		CuentaContable renQuinCate = new CuentaContable();
		renQuinCate.setSdescripcion("Renta de quinta categoría");
		renQuinCate.setIcodigoCuenta(40173);
		renQuinCate.setEmpresa(res_emp);		
		repo_ctaContable.save(renQuinCate);
		
		CuentaContable renNoDom = new CuentaContable();
		renNoDom.setSdescripcion("Renta de no domiciliados");
		renNoDom.setIcodigoCuenta(40174);
		renNoDom.setEmpresa(res_emp);		
		repo_ctaContable.save(renNoDom);
		
		CuentaContable otrReten = new CuentaContable();
		otrReten.setSdescripcion("Otras retenciones");
		otrReten.setIcodigoCuenta(40175);
		otrReten.setEmpresa(res_emp);		
		repo_ctaContable.save(otrReten);
		
		CuentaContable impTranFin = new CuentaContable();
		impTranFin.setSdescripcion("Impuesto a las transacciones financieras");
		impTranFin.setIcodigoCuenta(40181);
		impTranFin.setEmpresa(res_emp);		
		repo_ctaContable.save(impTranFin);
		
		CuentaContable impJueCasTrag = new CuentaContable();
		impJueCasTrag.setSdescripcion("Impuesto a las juegos de casino y tragamonedas");
		impJueCasTrag.setIcodigoCuenta(40182);
		impJueCasTrag.setEmpresa(res_emp);		
		repo_ctaContable.save(impJueCasTrag);
		
		CuentaContable tasPreServPub = new CuentaContable();
		tasPreServPub.setSdescripcion("Tasas por la prestación de servicios públicos");
		tasPreServPub.setIcodigoCuenta(40183);
		tasPreServPub.setEmpresa(res_emp);		
		repo_ctaContable.save(tasPreServPub);
		
		CuentaContable regalias = new CuentaContable();
		regalias.setSdescripcion("Regalías");
		regalias.setIcodigoCuenta(40184);
		regalias.setEmpresa(res_emp);		
		repo_ctaContable.save(regalias);
		
		CuentaContable impDivid = new CuentaContable();
		impDivid.setSdescripcion("Impuesto a los dividendos");
		impDivid.setIcodigoCuenta(40185);
		impDivid.setEmpresa(res_emp);		
		repo_ctaContable.save(impDivid);
		
		CuentaContable impTempActNet = new CuentaContable();
		impTempActNet.setSdescripcion("Impuesto temporal a los activos netos");
		impTempActNet.setIcodigoCuenta(40186);
		impTempActNet.setEmpresa(res_emp);		
		repo_ctaContable.save(impTempActNet);
		
		CuentaContable otrosImp = new CuentaContable();
		otrosImp.setSdescripcion("Otros impuestos");
		otrosImp.setIcodigoCuenta(40189);
		otrosImp.setEmpresa(res_emp);		
		repo_ctaContable.save(otrosImp);
		
		CuentaContable certTribut = new CuentaContable();
		certTribut.setSdescripcion("Certificados tributarios");
		certTribut.setIcodigoCuenta(402);
		certTribut.setEmpresa(res_emp);		
		repo_ctaContable.save(certTribut);
		
		CuentaContable essalud = new CuentaContable();
		essalud.setSdescripcion("ESSALUD");
		essalud.setIcodigoCuenta(4031);
		essalud.setEmpresa(res_emp);		
		repo_ctaContable.save(essalud);
		
		CuentaContable onp = new CuentaContable();
		onp.setSdescripcion("ONP");
		onp.setIcodigoCuenta(4032);
		onp.setEmpresa(res_emp);		
		repo_ctaContable.save(onp);
		
		CuentaContable contSenati = new CuentaContable();
		contSenati.setSdescripcion("Contribución al SENATI");
		contSenati.setIcodigoCuenta(4033);
		contSenati.setEmpresa(res_emp);		
		repo_ctaContable.save(contSenati);
		
		CuentaContable contSencico = new CuentaContable();
		contSencico.setSdescripcion("Contribución al SENCICO");
		contSencico.setIcodigoCuenta(4034);
		contSencico.setEmpresa(res_emp);		
		repo_ctaContable.save(contSencico);
		
		CuentaContable otrInsti = new CuentaContable();
		otrInsti.setSdescripcion("Otras instituciones");
		otrInsti.setIcodigoCuenta(4039);
		otrInsti.setEmpresa(res_emp);		
		repo_ctaContable.save(otrInsti);
		
		CuentaContable gobRegional = new CuentaContable();
		gobRegional.setSdescripcion("Gobiernos regionales");
		gobRegional.setIcodigoCuenta(405);
		gobRegional.setEmpresa(res_emp);		
		repo_ctaContable.save(gobRegional);
		
		CuentaContable impPatVeh = new CuentaContable();
		impPatVeh.setSdescripcion("Impuesto al patrimonio vehicular");
		impPatVeh.setIcodigoCuenta(40611);
		impPatVeh.setEmpresa(res_emp);		
		repo_ctaContable.save(impPatVeh);
		
		CuentaContable impApuest = new CuentaContable();
		impApuest.setSdescripcion("Impuesto a las apuestas");
		impApuest.setIcodigoCuenta(40612);
		impApuest.setEmpresa(res_emp);		
		repo_ctaContable.save(impApuest);
		
		CuentaContable impJuegos= new CuentaContable();
		impJuegos.setSdescripcion("Impuesto a los juegos");
		impJuegos.setIcodigoCuenta(40613);
		impJuegos.setEmpresa(res_emp);		
		repo_ctaContable.save(impJuegos);
		
		CuentaContable impAlcabala= new CuentaContable();
		impAlcabala.setSdescripcion("Impuesto de alcabala");
		impAlcabala.setIcodigoCuenta(40614);
		impAlcabala.setEmpresa(res_emp);		
		repo_ctaContable.save(impAlcabala);
		
		CuentaContable impPredial= new CuentaContable();
		impPredial.setSdescripcion("Impuesto predial");
		impPredial.setIcodigoCuenta(40615);
		impPredial.setEmpresa(res_emp);		
		repo_ctaContable.save(impPredial);
		
		CuentaContable impEspPubNoDep= new CuentaContable();
		impEspPubNoDep.setSdescripcion("Impuesto a los espectáculos públicos no deportivos");
		impEspPubNoDep.setIcodigoCuenta(40616);
		impEspPubNoDep.setEmpresa(res_emp);		
		repo_ctaContable.save(impEspPubNoDep);
		
		CuentaContable contribucion = new CuentaContable();
		contribucion.setSdescripcion("Contribuciones");
		contribucion.setIcodigoCuenta(4062);
		contribucion.setEmpresa(res_emp);		
		repo_ctaContable.save(contribucion);
		
		CuentaContable licAperEstab = new CuentaContable();
		licAperEstab.setSdescripcion("Licencia de apertura de establecimientos");
		licAperEstab.setIcodigoCuenta(40631);
		licAperEstab.setEmpresa(res_emp);		
		repo_ctaContable.save(licAperEstab);
		
		CuentaContable transPublico = new CuentaContable();
		transPublico.setSdescripcion("Transporte público");
		transPublico.setIcodigoCuenta(40632);
		transPublico.setEmpresa(res_emp);		
		repo_ctaContable.save(transPublico);
		
		CuentaContable estacVehiculos = new CuentaContable();
		estacVehiculos.setSdescripcion("Estacionamiento de vehículos");
		estacVehiculos.setIcodigoCuenta(40633);
		estacVehiculos.setEmpresa(res_emp);		
		repo_ctaContable.save(estacVehiculos);
		
		CuentaContable servPublArb = new CuentaContable();
		servPublArb.setSdescripcion("Servicios públicos o arbitrios");
		servPublArb.setIcodigoCuenta(40634);
		servPublArb.setEmpresa(res_emp);		
		repo_ctaContable.save(servPublArb);
		
		CuentaContable servAdmiDer = new CuentaContable();
		servAdmiDer.setSdescripcion("Servicios administrativos o derechos");
		servAdmiDer.setIcodigoCuenta(40635);
		servAdmiDer.setEmpresa(res_emp);		
		repo_ctaContable.save(servAdmiDer);
		
		CuentaContable otrCostAdmInt = new CuentaContable();
		otrCostAdmInt.setSdescripcion("Otros costos administrativos e intereses");
		otrCostAdmInt.setIcodigoCuenta(409);
		otrCostAdmInt.setEmpresa(res_emp);		
		repo_ctaContable.save(otrCostAdmInt);
		
		//41	REMUNERACIONES Y PARTICIPACIONES POR PAGAR
		
		CuentaContable sueldoSalarioXPagar = new CuentaContable();
		sueldoSalarioXPagar.setSdescripcion("Sueldos y salarios por pagar");
		sueldoSalarioXPagar.setIcodigoCuenta(4111);
		sueldoSalarioXPagar.setEmpresa(res_emp);		
		repo_ctaContable.save(sueldoSalarioXPagar);
		
		CuentaContable comisionXPagar = new CuentaContable();
		comisionXPagar.setSdescripcion("Comisiones por pagar");
		comisionXPagar.setIcodigoCuenta(4112);
		comisionXPagar.setEmpresa(res_emp);		
		repo_ctaContable.save(comisionXPagar);
		
		CuentaContable remunEspXPagar = new CuentaContable();
		remunEspXPagar.setSdescripcion("Remuneraciones en especie por pagar");
		remunEspXPagar.setIcodigoCuenta(4113);
		remunEspXPagar.setEmpresa(res_emp);		
		repo_ctaContable.save(remunEspXPagar);
		
		CuentaContable gratiXPagar = new CuentaContable();
		gratiXPagar.setSdescripcion("Gratificaciones por pagar");
		gratiXPagar.setIcodigoCuenta(4114);
		gratiXPagar.setEmpresa(res_emp);		
		repo_ctaContable.save(gratiXPagar);
		
		CuentaContable vacXPagar = new CuentaContable();
		vacXPagar.setSdescripcion("Vacaciones por pagar");
		vacXPagar.setIcodigoCuenta(4115);
		vacXPagar.setEmpresa(res_emp);		
		repo_ctaContable.save(vacXPagar);
		
		CuentaContable partTraXPagar = new CuentaContable();
		partTraXPagar.setSdescripcion("Participaciones de los trabajadores por pagar");
		partTraXPagar.setIcodigoCuenta(413);
		partTraXPagar.setEmpresa(res_emp);		
		repo_ctaContable.save(partTraXPagar);
		
		CuentaContable cts = new CuentaContable();
		cts.setSdescripcion("Compensación por tiempo de servicios");
		cts.setIcodigoCuenta(4151);
		cts.setEmpresa(res_emp);		
		repo_ctaContable.save(cts);
		
		CuentaContable adelantoCts = new CuentaContable();
		adelantoCts.setSdescripcion("Adelanto de compensación por tiempo de servicios");
		adelantoCts.setIcodigoCuenta(4152);
		adelantoCts.setEmpresa(res_emp);		
		repo_ctaContable.save(adelantoCts);
		
		CuentaContable pensJubiTrabaRem = new CuentaContable();
		pensJubiTrabaRem.setSdescripcion("Pensiones y jubilaciones");
		pensJubiTrabaRem.setIcodigoCuenta(4153);
		pensJubiTrabaRem.setEmpresa(res_emp);		
		repo_ctaContable.save(pensJubiTrabaRem);
		
		CuentaContable admiFondPens = new CuentaContable();
		admiFondPens.setSdescripcion("Administradoras de fondos de pensiones");
		admiFondPens.setIcodigoCuenta(417);
		admiFondPens.setEmpresa(res_emp);		
		repo_ctaContable.save(admiFondPens);
		
		CuentaContable otrRemPartXPag = new CuentaContable();
		otrRemPartXPag.setSdescripcion("Otras remuneraciones y participaciones por pagar");
		otrRemPartXPag.setIcodigoCuenta(419);
		otrRemPartXPag.setEmpresa(res_emp);		
		repo_ctaContable.save(otrRemPartXPag);
		
		//42	CUENTAS POR PAGAR COMERCIALES TERCEROS
		
		CuentaContable noEmiCtaXPagCom = new CuentaContable();
		noEmiCtaXPagCom.setSdescripcion("No emitidas");
		noEmiCtaXPagCom.setIcodigoCuenta(4211);
		noEmiCtaXPagCom.setEmpresa(res_emp);		
		repo_ctaContable.save(noEmiCtaXPagCom);
		
		CuentaContable emitidas = new CuentaContable();
		emitidas.setSdescripcion("Emitidas");
		emitidas.setIcodigoCuenta(4212);
		emitidas.setEmpresa(res_emp);		
		repo_ctaContable.save(emitidas);
		
		CuentaContable antProvee = new CuentaContable();
		antProvee.setSdescripcion("Anticipos a proveedores");
		antProvee.setIcodigoCuenta(422);
		antProvee.setEmpresa(res_emp);		
		repo_ctaContable.save(antProvee);
		
		CuentaContable letXPagar = new CuentaContable();
		letXPagar.setSdescripcion("Letras por pagar");
		letXPagar.setIcodigoCuenta(423);
		letXPagar.setEmpresa(res_emp);		
		repo_ctaContable.save(letXPagar);
		
		CuentaContable honXPagar = new CuentaContable();
		honXPagar.setSdescripcion("Honorarios por pagar");
		honXPagar.setIcodigoCuenta(424);
		honXPagar.setEmpresa(res_emp);		
		repo_ctaContable.save(honXPagar);
		
		
		// 44	CUENTAS POR PAGAR A LOS ACCIONISTAS(SOCIOS, PARTÍCIPES) Y DIRECTORES
		
		CuentaContable prestAccCtaXPag = new CuentaContable();
		prestAccCtaXPag.setSdescripcion("Préstamos");
		prestAccCtaXPag.setIcodigoCuenta(4411);
		prestAccCtaXPag.setEmpresa(res_emp);		
		repo_ctaContable.save(prestAccCtaXPag);
		
		CuentaContable dividendos = new CuentaContable();
		dividendos.setSdescripcion("Dividendos");
		dividendos.setIcodigoCuenta(4412);
		dividendos.setEmpresa(res_emp);		
		repo_ctaContable.save(dividendos);

		CuentaContable otrCtaXPagarAcc = new CuentaContable();
		otrCtaXPagarAcc.setSdescripcion("Otras cuentas por pagar");
		otrCtaXPagarAcc.setIcodigoCuenta(4419);
		otrCtaXPagarAcc.setEmpresa(res_emp);		
		repo_ctaContable.save(otrCtaXPagarAcc);
		
		CuentaContable dietas = new CuentaContable();
		dietas.setSdescripcion("Dietas");
		dietas.setIcodigoCuenta(4421);
		dietas.setEmpresa(res_emp);		
		repo_ctaContable.save(dietas);
		
		CuentaContable otrCtaXPagarDir = new CuentaContable();
		otrCtaXPagarDir.setSdescripcion("Otras cuentas por pagar");
		otrCtaXPagarDir.setIcodigoCuenta(4429);
		otrCtaXPagarDir.setEmpresa(res_emp);		
		repo_ctaContable.save(otrCtaXPagarDir);
				
		
		// 62 GASTOS DE PERSONAL Y DIRECTORES
		
		CuentaContable sueldoSalario = new CuentaContable();
		sueldoSalario.setSdescripcion("Sueldos y salarios");
		sueldoSalario.setIcodigoCuenta(6211);
		sueldoSalario.setEmpresa(res_emp);		
		repo_ctaContable.save(sueldoSalario);
		
		CuentaContable comisiones = new CuentaContable();
		comisiones.setSdescripcion("Comisiones");
		comisiones.setIcodigoCuenta(6212);
		comisiones.setEmpresa(res_emp);		
		repo_ctaContable.save(comisiones);
		
		CuentaContable remunerEspecie = new CuentaContable();
		remunerEspecie.setSdescripcion("Remuneraciones en especie");
		remunerEspecie.setIcodigoCuenta(6213);
		remunerEspecie.setEmpresa(res_emp);		
		repo_ctaContable.save(remunerEspecie);
		
		CuentaContable gratificaciones = new CuentaContable();
		gratificaciones.setSdescripcion("Gratificaciones");
		gratificaciones.setIcodigoCuenta(6214);
		gratificaciones.setEmpresa(res_emp); 		
		repo_ctaContable.save(gratificaciones);
		
		CuentaContable vacaciones = new CuentaContable();
		vacaciones.setSdescripcion("Vacaciones");
		vacaciones.setIcodigoCuenta(6215);
		vacaciones.setEmpresa(res_emp); 		
		repo_ctaContable.save(vacaciones);
		
		CuentaContable otrRemuneracion = new CuentaContable();
		otrRemuneracion.setSdescripcion("Otras remuneraciones");
		otrRemuneracion.setIcodigoCuenta(622);
		otrRemuneracion.setEmpresa(res_emp); 		
		repo_ctaContable.save(otrRemuneracion);
			
		CuentaContable indemnPersonal = new CuentaContable();
		indemnPersonal.setSdescripcion("Indemnizaciones al personal");
		indemnPersonal.setIcodigoCuenta(623);
		indemnPersonal.setEmpresa(res_emp); 		
		repo_ctaContable.save(indemnPersonal);
		
		CuentaContable capacitacion = new CuentaContable();
		capacitacion.setSdescripcion("Capacitacion");
		capacitacion.setIcodigoCuenta(624);
		capacitacion.setEmpresa(res_emp);
		repo_ctaContable.save(capacitacion);

		CuentaContable atencionPersonal = new CuentaContable();
		atencionPersonal.setSdescripcion("Atención al personal");
		atencionPersonal.setIcodigoCuenta(625);
		atencionPersonal.setEmpresa(res_emp);		
		repo_ctaContable.save(atencionPersonal);
		
//		CuentaContable gerentes = new CuentaContable();
//		gerentes.setSdescripcion("Gerentes");
//		gerentes.setIcodigoCuenta(626);
//		gerentes.setEmpresa(res_emp);		
//		repo_ctaContable.save(gerentes);
		
		CuentaContable regPrestSalud = new CuentaContable();
		regPrestSalud.setSdescripcion("Régimen de prestaciones de salud");
		regPrestSalud.setIcodigoCuenta(6271);
		regPrestSalud.setEmpresa(res_emp);		
		repo_ctaContable.save(regPrestSalud);
		
		CuentaContable regPensiones = new CuentaContable();
		regPensiones.setSdescripcion("Régimen de pensiones");
		regPensiones.setIcodigoCuenta(6272);
		regPensiones.setEmpresa(res_emp);
		repo_ctaContable.save(regPensiones);
		
		CuentaContable seguroComplem = new CuentaContable();
		seguroComplem.setSdescripcion("Seguro complementario de trabajo de riesgo, accidentes de trabajo y enfermedades profesionales");
		seguroComplem.setIcodigoCuenta(6273);
		seguroComplem.setEmpresa(res_emp);
		repo_ctaContable.save(seguroComplem);
		
		CuentaContable seguroVida = new CuentaContable();
		seguroVida.setSdescripcion("Seguro de vida");
		seguroVida.setIcodigoCuenta(6274);
		seguroVida.setEmpresa(res_emp);
		repo_ctaContable.save(seguroVida);
		
		CuentaContable seguroParticular = new CuentaContable();
		seguroParticular.setSdescripcion("Seguros particulares de prestaciones de salud – EPS y otros particulares");
		seguroParticular.setIcodigoCuenta(6275);
		seguroParticular.setEmpresa(res_emp);
		repo_ctaContable.save(seguroParticular);
		
		CuentaContable cajaBenSegSocPescador = new CuentaContable();
		cajaBenSegSocPescador.setSdescripcion("Caja de beneficios de seguridad social del pescador");
		cajaBenSegSocPescador.setIcodigoCuenta(6276);
		cajaBenSegSocPescador.setEmpresa(res_emp);
		repo_ctaContable.save(cajaBenSegSocPescador);
		
		CuentaContable contSenatiGast = new CuentaContable();
		contSenatiGast.setSdescripcion("Contribuciones al SENATI");
		contSenatiGast.setIcodigoCuenta(6277);
		contSenatiGast.setEmpresa(res_emp);
		repo_ctaContable.save(contSenatiGast);
		
		CuentaContable retribDirect= new CuentaContable();
		retribDirect.setSdescripcion("Retribuciones al directorio");
		retribDirect.setIcodigoCuenta(628);
		retribDirect.setEmpresa(res_emp);
		repo_ctaContable.save(retribDirect);
		
		CuentaContable compTiemServ = new CuentaContable();
		compTiemServ.setSdescripcion("Compensación por tiempo de servicio");
		compTiemServ.setIcodigoCuenta(6291);
		compTiemServ.setEmpresa(res_emp);
		repo_ctaContable.save(compTiemServ);
		
		CuentaContable pensJubilaciones = new CuentaContable();
		pensJubilaciones.setSdescripcion("Pensiones y jubilaciones");
		pensJubilaciones.setIcodigoCuenta(6292);
		pensJubilaciones.setEmpresa(res_emp);
		repo_ctaContable.save(pensJubilaciones);
		
		CuentaContable  otrosBenPostEmpleo = new CuentaContable();
		otrosBenPostEmpleo.setSdescripcion("Otros beneficios post-empleo");
		otrosBenPostEmpleo.setIcodigoCuenta(6293);
		otrosBenPostEmpleo.setEmpresa(res_emp);
		repo_ctaContable.save(otrosBenPostEmpleo);
		
		CuentaContable partiCorriente = new CuentaContable();
		partiCorriente.setSdescripcion("Participación corriente");
		partiCorriente.setIcodigoCuenta(62941);
		partiCorriente.setEmpresa(res_emp);
		repo_ctaContable.save(partiCorriente);
		
		CuentaContable partiDiferida = new CuentaContable();
		partiDiferida.setSdescripcion("Participación diferida");
		partiDiferida.setIcodigoCuenta(62942);
		partiDiferida.setEmpresa(res_emp);
		repo_ctaContable.save(partiDiferida);
		
		//63 GASTOS DE SERVICIOS PRESTADOS POR TERCEROS
		
		CuentaContable deCarga = new CuentaContable();
		deCarga.setSdescripcion("De carga");
		deCarga.setIcodigoCuenta(63111);
		deCarga.setEmpresa(res_emp);
		repo_ctaContable.save(deCarga);
		
		CuentaContable dePasajeros = new CuentaContable();
		dePasajeros.setSdescripcion("De pasajeros");
		dePasajeros.setIcodigoCuenta(63112);
		dePasajeros.setEmpresa(res_emp);
		repo_ctaContable.save(dePasajeros);
			
		CuentaContable correos = new CuentaContable();
		correos.setSdescripcion("Correos");
		correos.setIcodigoCuenta(6312);
		correos.setEmpresa(res_emp);
		repo_ctaContable.save(correos);
		
		CuentaContable alojamiento = new CuentaContable();
		alojamiento.setSdescripcion("Alojamiento");
		alojamiento.setIcodigoCuenta(6313);
		alojamiento.setEmpresa(res_emp);
		repo_ctaContable.save(alojamiento);
		
		CuentaContable alimentacion = new CuentaContable();
		alimentacion.setSdescripcion("Alimentación");
		alimentacion.setIcodigoCuenta(6314);
		alimentacion.setEmpresa(res_emp);
		repo_ctaContable.save(alimentacion);
		
		CuentaContable otrGastViajes = new CuentaContable();
		otrGastViajes.setSdescripcion("Otros gastos de viaje");
		otrGastViajes.setIcodigoCuenta(6315);
		otrGastViajes.setEmpresa(res_emp);
		repo_ctaContable.save(otrGastViajes);
		
		CuentaContable administrativa = new CuentaContable();
		administrativa.setSdescripcion("Administrativa");
		administrativa.setIcodigoCuenta(6321);
		administrativa.setEmpresa(res_emp);
		repo_ctaContable.save(administrativa);
		
		CuentaContable legalTributaria = new CuentaContable();
		legalTributaria.setSdescripcion("Legal y tributaria");
		legalTributaria.setIcodigoCuenta(6322);
		legalTributaria.setEmpresa(res_emp);
		repo_ctaContable.save(legalTributaria);
		
		CuentaContable auditoContable = new CuentaContable();
		auditoContable.setSdescripcion("Auditoría y contable");
		auditoContable.setIcodigoCuenta(6323);
		auditoContable.setEmpresa(res_emp);
		repo_ctaContable.save(auditoContable);
		
		CuentaContable mercadotecnia = new CuentaContable();
		mercadotecnia.setSdescripcion("Mercadotecnia");
		mercadotecnia.setIcodigoCuenta(6324);
		mercadotecnia.setEmpresa(res_emp);
		repo_ctaContable.save(mercadotecnia);
		
		CuentaContable medioambiental = new CuentaContable();
		medioambiental.setSdescripcion("Medioambiental");
		medioambiental.setIcodigoCuenta(6325);
		medioambiental.setEmpresa(res_emp);
		repo_ctaContable.save(medioambiental);
		
		CuentaContable invesDesar = new CuentaContable();
		invesDesar.setSdescripcion("Investigación y desarrollo");
		invesDesar.setIcodigoCuenta(6326);
		invesDesar.setEmpresa(res_emp);
		repo_ctaContable.save(invesDesar);
		
		CuentaContable produccion = new CuentaContable();
		produccion.setSdescripcion("Producción");
		produccion.setIcodigoCuenta(6327);
		produccion.setEmpresa(res_emp);
		repo_ctaContable.save(produccion);
		
		CuentaContable otros = new CuentaContable();
		otros.setSdescripcion("Otros");
		otros.setIcodigoCuenta(6329);
		otros.setEmpresa(res_emp);
		repo_ctaContable.save(otros);
		
		CuentaContable prodEncarTerc = new CuentaContable();
		prodEncarTerc.setSdescripcion("Producción encargada a terceros");
		prodEncarTerc.setIcodigoCuenta(633);
		prodEncarTerc.setEmpresa(res_emp);
		repo_ctaContable.save(prodEncarTerc);
		
		CuentaContable propInversion = new CuentaContable();
		propInversion.setSdescripcion("Propiedad de inversión");
		propInversion.setIcodigoCuenta(6341);
		propInversion.setEmpresa(res_emp);
		repo_ctaContable.save(propInversion);
		
		CuentaContable financiero = new CuentaContable();
		financiero.setSdescripcion("Financiero");
		financiero.setIcodigoCuenta(63421);
		financiero.setEmpresa(res_emp);
		repo_ctaContable.save(financiero);
		
		CuentaContable operativo = new CuentaContable();
		operativo.setSdescripcion("Operativo");
		operativo.setIcodigoCuenta(63422);
		operativo.setEmpresa(res_emp);
		repo_ctaContable.save(operativo);
		
		CuentaContable propPlantEquipo = new CuentaContable();
		propPlantEquipo.setSdescripcion("Propiedad, planta y equipo");
		propPlantEquipo.setIcodigoCuenta(6343);
		propPlantEquipo.setEmpresa(res_emp);
		repo_ctaContable.save(propPlantEquipo);
		
		CuentaContable intangibles = new CuentaContable();
		intangibles.setSdescripcion("Intangibles");
		intangibles.setIcodigoCuenta(6344);
		intangibles.setEmpresa(res_emp);
		repo_ctaContable.save(intangibles);
		
		CuentaContable actBiologico = new CuentaContable();
		actBiologico.setSdescripcion("Activos biológicos");
		actBiologico.setIcodigoCuenta(6345);
		actBiologico.setEmpresa(res_emp);
		repo_ctaContable.save(actBiologico);
		
		CuentaContable terrenos = new CuentaContable();
		terrenos.setSdescripcion("Terrenos");
		terrenos.setIcodigoCuenta(6351);
		terrenos.setEmpresa(res_emp);
		repo_ctaContable.save(terrenos);
		
		CuentaContable edificaciones = new CuentaContable();
		edificaciones.setSdescripcion("Edificaciones");
		edificaciones.setIcodigoCuenta(6352);
		edificaciones.setEmpresa(res_emp);
		repo_ctaContable.save(edificaciones);
		
		CuentaContable maqEquipExpl = new CuentaContable();
		maqEquipExpl.setSdescripcion("Maquinarias y equipos de explotación");
		maqEquipExpl.setIcodigoCuenta(6353);
		maqEquipExpl.setEmpresa(res_emp);
		repo_ctaContable.save(maqEquipExpl);
		
		CuentaContable equipTransporte = new CuentaContable();
		equipTransporte.setSdescripcion("Equipo de transporte");
		equipTransporte.setIcodigoCuenta(6354);
		equipTransporte.setEmpresa(res_emp);
		repo_ctaContable.save(equipTransporte);
		
		CuentaContable mueblesEnsere = new CuentaContable();
		mueblesEnsere.setSdescripcion("Muebles y enseres");
		mueblesEnsere.setIcodigoCuenta(6355);
		mueblesEnsere.setEmpresa(res_emp);
		repo_ctaContable.save(mueblesEnsere);
		
		CuentaContable equipDiversos = new CuentaContable();
		equipDiversos.setSdescripcion("Equipos diversos");
		equipDiversos.setIcodigoCuenta(6356);
		equipDiversos.setEmpresa(res_emp);
		repo_ctaContable.save(equipDiversos);
		
		CuentaContable energElectrica = new CuentaContable();
		energElectrica.setSdescripcion("Energía eléctrica");
		energElectrica.setIcodigoCuenta(6361);
		energElectrica.setEmpresa(res_emp);
		repo_ctaContable.save(energElectrica);
		
		CuentaContable gas = new CuentaContable();
		gas.setSdescripcion("Gas");
		gas.setIcodigoCuenta(6362);
		gas.setEmpresa(res_emp);
		repo_ctaContable.save(gas);
		
		CuentaContable agua = new CuentaContable();
		agua.setSdescripcion("Agua");
		agua.setIcodigoCuenta(6363);
		agua.setEmpresa(res_emp);
		repo_ctaContable.save(agua);
		
		CuentaContable telf = new CuentaContable();
		telf.setSdescripcion("Teléfono");
		telf.setIcodigoCuenta(6364);
		telf.setEmpresa(res_emp);
		repo_ctaContable.save(telf);
		
		CuentaContable internet = new CuentaContable();
		internet.setSdescripcion("Internet");
		internet.setIcodigoCuenta(6365);
		internet.setEmpresa(res_emp);
		repo_ctaContable.save(internet);
		
		CuentaContable radio = new CuentaContable();
		radio.setSdescripcion("Radio");
		radio.setIcodigoCuenta(6366);
		radio.setEmpresa(res_emp);
		repo_ctaContable.save(radio);
		
		CuentaContable cable = new CuentaContable();
		cable.setSdescripcion("Cable");
		cable.setIcodigoCuenta(6367);
		cable.setEmpresa(res_emp);
		repo_ctaContable.save(cable);
		
		CuentaContable publicidad = new CuentaContable();
		publicidad.setSdescripcion("Publicidad");
		publicidad.setIcodigoCuenta(6371);
		publicidad.setEmpresa(res_emp);
		repo_ctaContable.save(publicidad);
		
		CuentaContable publicaciones = new CuentaContable();
		publicaciones.setSdescripcion("Publicaciones");
		publicaciones.setIcodigoCuenta(6372);
		publicaciones.setEmpresa(res_emp);
		repo_ctaContable.save(publicaciones);
		
		CuentaContable relacPublicas = new CuentaContable();
		relacPublicas.setSdescripcion("Relaciones públicas");
		relacPublicas.setIcodigoCuenta(6373);
		relacPublicas.setEmpresa(res_emp);
		repo_ctaContable.save(relacPublicas);
		
		CuentaContable servContratistas = new CuentaContable();
		servContratistas.setSdescripcion("Servicios de contratistas");
		servContratistas.setIcodigoCuenta(638);
		servContratistas.setEmpresa(res_emp);
		repo_ctaContable.save(servContratistas);
		
		CuentaContable gastBancarios = new CuentaContable();
		gastBancarios.setSdescripcion("Gastos bancarios");
		gastBancarios.setIcodigoCuenta(6391);
		gastBancarios.setEmpresa(res_emp);
		repo_ctaContable.save(gastBancarios);
		
		CuentaContable gastLaborat = new CuentaContable();
		gastLaborat.setSdescripcion("Gastos de laboratorio");
		gastLaborat.setIcodigoCuenta(6392);
		gastLaborat.setEmpresa(res_emp);
		repo_ctaContable.save(gastLaborat);
		
	}
	
	
	

	@Override
	public Empresa modificar(Empresa obj) {
		try {
			return repo.save(obj);
		}catch(Exception e){
			LOG.error(this.getClass().getSimpleName()+" modificarEmpresa. ERROR : "+e.getMessage());
			throw e;
		}
	}
	
	
	@Override
	public List<Empresa> listar() {
		try {
			return repo.findAll();
		}catch(Exception e){
			LOG.error(this.getClass().getSimpleName()+" listarEmpresa. ERROR : "+e.getMessage());
			throw e;
		}
	}
	

	@Override
	public Empresa leer(Integer id) {
		Optional<Empresa> op = repo.findById(id);
		return op.isPresent() ? op.get() : new Empresa();
	}
	
	
	@Override
	public Boolean eliminar(Integer id) {
		try {
			Boolean resp = repo.existsById(id);
			repo.deleteById(id);
			return resp;
			
		}catch(Exception e){
			LOG.error(this.getClass().getSimpleName()+" eliminarEmpresa. ERROR : "+e.getMessage());
			throw e;
		}
	}

	
	@Override
	public Empresa buscarXRuc(String ruc) {
		try {		
			return repo.findByRuc(ruc);
		}catch(Exception e){
			LOG.error(this.getClass().getSimpleName()+" buscarEmpresaXRuc. ERROR : "+e.getMessage());
			throw e;
		}
	}
	
	
	@Override
	public Empresa buscarXRazonSocial(String razonSocial) {
		try {		
			return repo.findByRazonSocial(razonSocial);
		}catch(Exception e){
			LOG.error(this.getClass().getSimpleName()+" buscarEmpresaXRazonSocial. ERROR : "+e.getMessage());
			throw e;
		}
	}
	
	private Eps crearEps(String descripcion, Double aporte, Empresa empresa) {
		Eps e = new Eps();
		e.setDescripcion(descripcion);
		e.setAporte(aporte);
		e.setEmpresa(empresa);
		
		return e;
	}
	
	private TipoPermiso CrearTipoPermiso(String des, boolean estado, Integer dias, Empresa emp) {
		TipoPermiso tp = new TipoPermiso();
		tp.setDescripcion(des);
		tp.setDiasPermiso(dias);
		tp.setEstado(estado);
		tp.setEmpresa(emp);
		return tp;
	}
	
	public Afp guardarAfpDefecto(Empresa empresa) {
		Afp afp = new Afp();
		afp.setApoOblFndPen(0.13);
		afp.setComAnuSobSal(0.0);
		afp.setComSobFlu(0.0);
		afp.setComSobFluMix(0.0);
		afp.setDescripcion("ONP");
		afp.setPrimaSeguro(0.0);
		afp.setEmpresa(empresa);
		
		return repo_afp.save(afp);
		
	}

}
