package com.mitocode.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mitocode.dto.AdelantoSueldoDTO;
import com.mitocode.dto.CuentaCargoDTO;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.Planilla;
import com.mitocode.dto.PlanillaDTO;
import com.mitocode.dto.PostulanteDTO;
import com.mitocode.dto.Reporte;
import com.mitocode.dto.SolicitudDTO;
import com.mitocode.dto.TipoPlanillaDTO;
import com.mitocode.dto.TrabajadorDTO;
import com.mitocode.model.AdelantoSueldo;
import com.mitocode.model.Afp;
import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.Asistencia;
import com.mitocode.model.Banco;
/*import com.mitocode.dto.CategoriaDTO;
import com.mitocode.model.Cargo;
import com.mitocode.model.Categoria;*/
import com.mitocode.model.CentroCosto;
import com.mitocode.model.Contrato;
import com.mitocode.model.CuentaCargo;
import com.mitocode.model.CuotaAdelanto;
import com.mitocode.model.Departamento;
import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.DerechoHabientes;
import com.mitocode.model.Descuentos;
import com.mitocode.model.Distrito;
import com.mitocode.model.Empresa;
import com.mitocode.model.EncargadoPlanilla;
import com.mitocode.model.Eps;
import com.mitocode.model.EstadoCivil;
import com.mitocode.model.Horario;
import com.mitocode.model.NivelEdu;
import com.mitocode.model.Ocupacion;
import com.mitocode.model.Pais;
import com.mitocode.model.Parametro;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Perfil;
import com.mitocode.model.Permiso;
import com.mitocode.model.PlanillaHistorico;
import com.mitocode.model.Postulante;
import com.mitocode.model.Provincia;
import com.mitocode.model.Puesto;
import com.mitocode.model.RegSalud;
import com.mitocode.model.RegimenLaboral;
import com.mitocode.model.RegimenTributario;
import com.mitocode.model.Rhe;
import com.mitocode.model.Sctr;
import com.mitocode.model.Situacion;
import com.mitocode.model.Solicitud;
import com.mitocode.model.TipoContrato;
import com.mitocode.model.TipoDoc;
import com.mitocode.model.TipoEmpresa;
import com.mitocode.model.TipoPago;
import com.mitocode.model.TipoPermiso;
import com.mitocode.model.TipoPlanilla;
import com.mitocode.model.TipoPlanillaDetalle;
import com.mitocode.model.TipoPlanillaPerfil;
import com.mitocode.model.TipoZona;
import com.mitocode.model.Trabajador;
import com.mitocode.model.Usuario;

public class DataDuroComplementos {
	
	public SolicitudDTO nuevaSolicitudDTO() {
		SolicitudDTO dto = new SolicitudDTO();
		
		Trabajador trabajador = nuevoTrabajador();
		trabajador.setIdTrabajador(1);
		Puesto puesto = nuevoPuesto();
		puesto.setIidPuesto(1);
		Empresa empresa = nuevaEmpresa();
		empresa.setIdEmpresa(1);
		
		dto.setTrabajador(trabajador);
		dto.setPuesto(puesto);
		dto.setSolicitud(nuevaSolicitud());
		
		return dto;
	}
	
	public Solicitud nuevaSolicitud() {
		Solicitud solicitud = new Solicitud();
		solicitud.setDbanda(10.0);
		solicitud.setSmotivos("Porque si");
		
		return solicitud;
	}
	
	public Puesto nuevoPuesto() {
		Puesto puesto = new Puesto();
		puesto.setSnombre("Puesto A");
		puesto.setIestado(1);
		puesto.setScategoria("M");
		AreaDepartamentoEmpresa areaDepartamentoEmpresa = this.nuevaAreaDepartamentoEmpresa();
		areaDepartamentoEmpresa.setIidAreaDepartamentoEmpresa(1);
		puesto.setAreaDepartamentoEmpresa(areaDepartamentoEmpresa);		
		return puesto;
	}
	
	public AreaDepartamentoEmpresa nuevaAreaDepartamentoEmpresa() {
		AreaDepartamentoEmpresa area = new AreaDepartamentoEmpresa();
		area.setSnombre("Area A");
		area.setIestado(1);
		DepartamentoEmpresa departamentoEmpresa = this.nuevoDepartamentoEmpresa();
		departamentoEmpresa.setIidDepartamentoEmpresa(1);
		area.setDepartamentoEmpresa(departamentoEmpresa);
		return area;
	}
	
	
	public DepartamentoEmpresa nuevoDepartamentoEmpresa() {
		DepartamentoEmpresa departamento = new DepartamentoEmpresa();
		departamento.setSnombre("Departamento A");
		departamento.setIestado(1);
		return departamento;
	}

	public EmpresaDTO nuevaEmpresaDTO() {
		EmpresaDTO empDTO = new EmpresaDTO();
		empDTO.setEmpresa(this.nuevaEmpresa());
		empDTO.setCuentaCargo(this.nuevaCuentaCargo());
		//empDTO.setCategoria(this.nuevoCategoria());
		empDTO.setParametro(this.nuevoParametro());
		empDTO.setCentroCosto(this.nuevoCentroCosto());
		empDTO.setDescuentos(this.nuevoDescuento());
		empDTO.setEncargadoPlanilla(this.nuevoEncargadoPlanilla());
		empDTO.setAfp(this.nuevoAfp());
		//empDTO.setTipoEmpresa(this.nuevoTipoEmpresa());
		empDTO.setRegTributario(this.nuevoRegimenTributario());
		empDTO.setTipoPermiso(this.nuevoTipoPermiso());
		return empDTO;
	}
	
	public TipoPlanillaDetalle nuevoTipoPlanillaDetalle() {
		TipoPlanillaDetalle detalle = new TipoPlanillaDetalle();
		Trabajador trab = nuevoTrabajador();
		TipoPlanilla tipoPlan = nuevoTipoPlanilla();
		detalle.setTipoPlanilla(tipoPlan);
		detalle.setTrabajador(trab);
		return detalle;		
	}
	
	public TipoPlanilla nuevoTipoPlanilla() {
		TipoPlanilla tipPlan = new TipoPlanilla();
		tipPlan.setDescripcion("Tipo Planilla A");
		tipPlan.setCategoriaPlanilla(4);
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		tipPlan.setEmpresa(emp);
		
		return tipPlan;
	}
	
	public TipoPlanillaDTO nuevoTipoPlanillaDTO() {
		TipoPlanillaDTO tipoPlan = new TipoPlanillaDTO();
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		tipoPlan.setEmpresa(emp);
		
		TipoPlanilla tipPlan = new TipoPlanilla();
		tipPlan.setDescripcion("Tipo Planilla A");
		tipPlan.setCategoriaPlanilla(4);
		
		List<Perfil> lsPerfil = new ArrayList<>();
		lsPerfil.add(nuevoPerfil());
		lsPerfil.add(nuevoPerfil2());
		
		List<Trabajador> lsTrabajador = new ArrayList<>();
		Trabajador trab1 = nuevoTrabajador();
		trab1.setIdTrabajador(1);
		Trabajador trab2 = nuevoTrabajador();
		trab2.setIdTrabajador(2);
		lsTrabajador.add(trab1);
		lsTrabajador.add(trab2);
		
		tipoPlan.setTipoPlanilla(tipPlan);
		tipoPlan.setEmpresa(emp);
		tipoPlan.setLsPerfil(lsPerfil);
		tipoPlan.setLsTrabajador(lsTrabajador);
		
		return tipoPlan;
	}
	
	public TipoPlanillaPerfil nuevoTipoPlanillaPerfil() {
		TipoPlanillaPerfil tpp = new TipoPlanillaPerfil();
		tpp.setEstado(1);
		tpp.setPerfil(nuevoPerfil());
		tpp.setTipoPlanilla(nuevoTipoPlanilla());
		return tpp;
	}
	
	public Reporte nuevoReporte(){
		Reporte reporte  = new Reporte();
		reporte.setIdPdoAno(1);
		reporte.setIdPdoMeS(1);
		reporte.setIdTrabajador(1);
		return reporte;
	}
	
	public EncargadoPlanilla nuevoEncargadoPlanilla(){
		EncargadoPlanilla encargado = new EncargadoPlanilla();
		/*encargado.setApellido("Chespirito");
		encargado.setNombre("Roberto");
		encargado.setNroDni("73058001");*/
		encargado.setEstado(1);
		encargado.setEmpresa(nuevaEmpresa());
		return encargado;	
	}
	
	public AdelantoSueldoDTO nuevoAdelantoSueldoDTO(){
		AdelantoSueldoDTO adelSueld = new AdelantoSueldoDTO();
		adelSueld.setAdelantoSueldo(nuevoAdelantoSueldo());
		adelSueld.setCuotaAdelanto(nuevaCuotaAdelanto());
		adelSueld.setEmpresa(nuevaEmpresa());
		adelSueld.setTrabajador(nuevoTrabajador());
		adelSueld.setPdoAno(nuevoPdoAno());
		adelSueld.setPdoMes(nuevoPdoMes());
		return adelSueld;
	}
	
	public CuotaAdelanto nuevaCuotaAdelantoSueldo(){
		CuotaAdelanto cuota = new CuotaAdelanto();
		cuota.setAdelantoSueldo(nuevoAdelantoSueldo());
		cuota.setEstado(1);
		cuota.setMontoCuota(5000.0);
		cuota.setPdoAno(nuevoPdoAno());
		cuota.setPdoMes(nuevoPdoMes());
		return cuota;
	}
	
	public Asistencia nuevaAsistencia(){
		Asistencia asistencia = new Asistencia();
		asistencia.setFecha(new java.sql.Date(2019257451));
		asistencia.setTipoAsistencia(0);
		asistencia.setHorIniDia(new java.sql.Timestamp(2019257451));
		asistencia.setTrabajador(nuevoTrabajador());
		asistencia.setPdoAno(nuevoPdoAno());
		asistencia.setPdoMes(nuevoPdoMes());
		return asistencia;
	}
	
	public Permiso nuevoPermiso(){
		Permiso permiso = new Permiso();
		permiso.setTipoPermiso(nuevoTipoPermiso());
		permiso.setFechaIni(new Date());
		permiso.setPdoMes(nuevoPdoMes());
		permiso.setPdoAno(nuevoPdoAno());
		permiso.setTrabajador(nuevoTrabajador());
		return permiso;
	}
	
	

	public AdelantoSueldo nuevoAdelantoSueldo() {
		AdelantoSueldo adeSue = new AdelantoSueldo();
		adeSue.setMontoTotal(2000.0);
		adeSue.setNroCuotas(4);
		Trabajador trab = nuevoTrabajador();
		trab.setIdTrabajador(1);
		adeSue.setEstado(0);
		adeSue.setTrabajador(trab);
		adeSue.setFechaSol(new Timestamp(20154852));
		
	
		return adeSue;
	}
	
	public DerechoHabientes nuevoDerechoHabiente() {
		DerechoHabientes derHab = new DerechoHabientes();
		derHab.setIdTipoDerechoHabiente(1);
		derHab.setNombre("Fulano");
		derHab.setApellido("DeTal");
		derHab.setFechaNac(new Timestamp(new Date().getTime()));
		derHab.setCorreo("Fulano@gmail.com");
		derHab.setNroCuspp("234567891234");
		derHab.setEstado(0);
		derHab.setFechaInicio(new Timestamp(new Date().getTime()));
		derHab.setFechaFin(new Timestamp(new Date().getTime()));
		Trabajador trab = nuevoTrabajador();
		trab.setIdTrabajador(1);
		derHab.setTrabajador(trab);		
		derHab.setNombreArchivo("text");
		
		return derHab;
	}
	
	public CuotaAdelanto nuevaCuotaAdelanto() {
		CuotaAdelanto cuoAde = new CuotaAdelanto();
		cuoAde.setAdelantoSueldo(nuevoAdelantoSueldo());
		cuoAde.setMontoCuota(500.0);
		cuoAde.setPdoAno(nuevoPdoAno());
		cuoAde.setPdoMes(nuevoPdoMes());
		cuoAde.setEstado(0);
		
		return cuoAde;
	}
	
	public Horario nuevoHorario() {
		Horario horario = new Horario();
		horario.setDescripcion("ADMINISTRATIVO");
		horario.setTotalDias("Jueves - Viernes");
		horario.setEmpresa(this.nuevaEmpresa());
		horario.setEstado(true);
		horario.setCheckDomingo(true);
		horario.setCheckSabado(true);
		horario.setCheckLunes(true);
		horario.setCheckMartes(true);
		horario.setCheckMiercoles(true);
		horario.setCheckJueves(true);
		horario.setCheckViernes(true);
		horario.setHorIniDia(new Timestamp(0));
		horario.setHorFinDia(new Timestamp(0));
		horario.setHorAlmuerIni(new Timestamp(0));
		horario.setHorAlmuerFin(new Timestamp(0));
		return horario;
	}
	
	public Descuentos nuevoDescuento() {
		Descuentos dsct = new Descuentos();
		dsct.setDescripcion("Daño en material otorgado");
		dsct.setTipoDsct(1);
		dsct.setMontoFijo(200.0);
		dsct.setEstado(1);
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		dsct.setEmpresa(emp);
		
		return dsct;
	}

	public TipoPermiso nuevoTipoPermiso() {
		TipoPermiso tip = new TipoPermiso();
		tip.setDescripcion("MATERNIDAD");
		tip.setDiasPermiso(90);
		tip.setEmpresa(this.nuevaEmpresa());
		tip.setEstado(true);
		return tip;
	}

	public Usuario nuevoUsuario() {
		Usuario user = new Usuario();
		user.setEmail("angel_96vir@hotmail.com");
		user.setUsername("junior");
		user.setPassword("12345");
		user.setEstado(true);
		user.setPerfil(this.nuevoPerfil());
		return user;
	}

	public Perfil nuevoPerfil() {
		Perfil perfil = new Perfil();
		perfil.setIdPerfil(1);
		perfil.setAmbito(1);
		perfil.setEstado(true);
		perfil.setNombres("ROLE_ADMIN");
		return perfil;
	}
	
	
	public Perfil nuevoPerfil2() {
		Perfil perfil = new Perfil();
		perfil.setIdPerfil(2);
		perfil.setAmbito(2);
		perfil.setEstado(true);
		perfil.setNombres("ROLE_SUPVISOR");
		return perfil;
	}

	public PlanillaDTO nuevoPlanillaDTO() {
		PlanillaDTO planDTO = new PlanillaDTO();
		planDTO.setPlanilla(this.nuevaPlanilla());

		planDTO.setContrato(this.nuevoContrato());
		planDTO.getContrato().setIdContrato(1);
		planDTO.getContrato().getTrabajador().getEmpresa().setIdEmpresa(1);

		planDTO.setTardanza(this.nuevaTardanza());
		planDTO.setTipoRango(this.nuevoTipoRango());

		return planDTO;
	}

	public Parametro nuevaTardanza() {
		Parametro par = new Parametro();
		par.setIdParametro(8);
		par.setCodigo("TIPTARD");
		par.setEstado(1);
		par.setGrupo("EMPRESA");
		par.setDescripcion("Tipo de tardanza");
		par.setNombre("Tipo de tardanza");
		par.setValor("1");
		par.setEmpresa(this.nuevoTrabajador().getEmpresa());
		return par;
	}

	public Parametro nuevoTipoRango() {
		Parametro par = new Parametro();
		par.setIdParametro(10);
		par.setCodigo("TIPORANGO");
		par.setEstado(0);
		par.setGrupo("EMPRESA");
		par.setDescripcion("Tipo de rango");
		par.setNombre("Tipo de rango");
		par.setValor("1");
		par.setEmpresa(this.nuevoTrabajador().getEmpresa());

		return par;
	}

	public Planilla nuevaPlanilla() {
		Planilla plan = new Planilla();
		plan.setFaltas_justi(1);
		plan.setFaltas_injusti(1);
		plan.setDias_vacaciones(3);
		plan.setDias_ferdo(1);
		plan.setDias_computables(26);
		plan.setFerdo_laborad(1);
		plan.setVacaciones_vend(0);
		plan.setHo_extra25(5.5);
		plan.setHo_extra35(6.5);
		plan.setFaltantes(25.0);
		plan.setAdelanto(0.0);
		plan.setPrestamos(0.0);
		plan.setTardanzas(0);
		plan.setComisiones(0.0);
		plan.setPdo_ano(this.nuevoPdoAno());
		plan.setPdo_mes(this.nuevoPdoMes());
		return plan;
	}

	public PlanillaHistorico nuevaPlanillaHistorico() {
		PlanillaHistorico planH = new PlanillaHistorico();

		planH.setDiaFerdo(5);
		planH.setFaltaJusti(0);
		planH.setRemFaltaJusti(2.5);
		planH.setFaltaInjusti(0);
		planH.setDsctFaltaInjusti(50.0);
		planH.setTiempo_tardanza(0);
		planH.setDsctTardanza(10.0);
		planH.setRemFerdo(50.0);
		planH.setDiaCompbl(26);
		planH.setHoExt25(5.5);
		planH.setRemHoExt25(50.0);
		planH.setHoExt35(6.0);
		planH.setRemHoExt35(5.5);
		planH.setDiaFerdoLabo(1);
		planH.setRemDiaFerdoLabo(60.0);
		planH.setRemJorNorm(5.5);
		planH.setAsigFam(93.0);
		planH.setRemGrati(930.0);
		planH.setDiaVaca(2);
		planH.setCts(930.0);
		planH.setRemDiaVaca(60.0);
		// planH.setTotComp(2500.0);
		planH.setBonif29351(930.0);
		planH.setMovilidad(120.0);
		planH.setTotIngre(2500.0);
		planH.setDsctFondObl(30.0);
		planH.setDsctComSobFLu(2.5);
		planH.setDsctComMixSobFlu(2.5);
		planH.setDsctComMixAnualSal(2.5);
		planH.setDsctPriSeg(2.5);
		planH.setDsctOnp(2.5);
		planH.setEssaludVida(5.0);
		planH.setDsct5taCat(30.0);
		planH.setMonFalt(50.0);
		planH.setMonAdela(2.5);
		planH.setMonPrest(400.0);
		planH.setTotDsct(300.0);
		planH.setNetPagPdt(2500.0);
		planH.setTotPagado(2500.0);
		planH.setAporEssalud(5.0);
		planH.setSctr(50.0);
		planH.setTotApor(2.5);
		planH.setRemVacaVend(0.0);

		planH.setContrato(this.nuevoContrato());
		planH.getContrato().setIdContrato(1);
		planH.setAfp(this.nuevoAfp());
		planH.setPdoAno(this.nuevoPdoAno());
		planH.setPdoMes(this.nuevoPdoMes());
		planH.setTipoTardanza(this.nuevoTipoTardanza());
		planH.setClaseTipoTardanza(this.nuevoClaseTipoTardanza());

		return planH;
	}

	public PdoAno nuevoPdoAno() {
		PdoAno pdoAno = new PdoAno();
		pdoAno.setIdPdoAno(8);
		pdoAno.setDescripcion(2022);
		pdoAno.setEmpresa(this.nuevaEmpresa());
		pdoAno.getEmpresa().setIdEmpresa(1);
		return pdoAno;
	}

	public PdoMes nuevoPdoMes() {
		PdoMes pdoMes = new PdoMes();
		pdoMes.setIdPdoMes(1);
		pdoMes.setDescripcion("Enero");
		pdoMes.setAbrev("ENE");
		pdoMes.setDiasFeriadoCalend(1);
		return pdoMes;
	}

	public Parametro nuevoTipoTardanza() {
		return null;
	}

	public Parametro nuevoClaseTipoTardanza() {
		return null;
	}

	public TrabajadorDTO nuevoTrabajadorDTO() {
		TrabajadorDTO trabDTO = new TrabajadorDTO();
		trabDTO.setTrabajador(this.nuevoTrabajador());
		trabDTO.setTipoDoc(this.nuevoTipoDoc());
		trabDTO.setPais(this.nuevoPais());
		trabDTO.setEstadoCivil(this.nuevoEstadoCivil());
		trabDTO.setDepartamento(this.nuevoDepartamento());
		trabDTO.setProvincia(this.nuevoProvincia());
		trabDTO.setDistrito(this.nuevoDistrito());
		trabDTO.setTipoZona(this.nuevoTipoZona());
		trabDTO.setNivelEdu(this.nuevoNivelEdu());
		trabDTO.setOcupacion(this.nuevoOcupacion());
		trabDTO.setEmpresa(this.nuevaEmpresa());
		trabDTO.getEmpresa().setIdEmpresa(1);
		trabDTO.setAfp(this.nuevoAfp());
		trabDTO.getAfp().setIdAfp(1);
		// trabDTO.setEps(this.nuevoEps());
		// ACA PUEDE HABER ERRROR TOO --------------------------------------
		trabDTO.setEpsPension(this.nuevoEpsPension());
		trabDTO.setEpsRegSalud(this.nuevoEpsRegSalud());
		trabDTO.setEpsSalud(this.nuevoEpsSalud());
		trabDTO.setRegSalud(this.nuevoRegSalud());
		trabDTO.setSituacion(this.nuevoSituacion());
		trabDTO.setContrato(this.nuevoContrato());
		trabDTO.setDerechoHabientes(this.nuevoDerechoHabiente());

		return trabDTO;
	}

	public Contrato nuevoContrato() {
		Contrato cont = new Contrato();

		Date hoy = new Date();
		Timestamp time = new Timestamp(hoy.getTime());

		cont.setRegAlterAcuAtp(1);
		cont.setDiscap(2);
		cont.setJorMax(3);
		cont.setHorNoc(4);
		cont.setFecInicio(time);
		cont.setSueldoBase(930);
		cont.setValorHora(15);
		cont.setNroCta("1234567910");
		cont.setNroCci("987654321");
		cont.setMovilidad(150);
		cont.setOtrIgr5ta(5);
		cont.setSindical(6);
		cont.setQuintaExo(7);
		cont.setCuentaCTS("15948637");
		cont.setTipoCuenta(1);
		cont.setTipoMoneda(1);
		cont.setFechaFirma(new Timestamp(25422657));

		cont.setTrabajador(this.nuevoTrabajador());
		cont.getTrabajador().setIdTrabajador(1);
		cont.setRegimenLaboral(this.nuevoRegimenLaboral());
		cont.getRegimenLaboral().setIdRegLaboral(1);
		cont.setTipoContrato(this.nuevoTipoContrato());
		cont.getTipoContrato().setIdTipContrato(1);
		cont.setCentroCosto(this.nuevoCentroCosto());
		cont.getCentroCosto().setIdCentroCosto(1);
		/*cont.setCategoria(this.nuevoCategoria());
		cont.getCategoria().setIdCategoria(1);
		cont.setCargo(this.nuevoCargo());
		cont.getCargo().setIdCargo(1);*/
		cont.setTipoPago(this.nuevoTipoPago());
		cont.getTipoPago().setIdTipoPago(1);
		cont.setBancoSueldo(this.nuevoBancoSueldo());
		cont.getBancoSueldo().setIdBanco(1);
		cont.setBancoCts(this.nuevoBancoCts());
		cont.getBancoCts().setIdBanco(2);
		cont.setSctrPension(this.nuevoSctrPension());
		cont.getSctrPension().setIdSctr(1);
		cont.setSctrSalud(this.nuevoSctrSalud());
		// cont.getSctrSalud().setIdSctr(1);

		return cont;
	}

	public Trabajador nuevoTrabajador() {
		Trabajador trab = new Trabajador();
		Date hoy = new Date();
		Timestamp time = new Timestamp(hoy.getTime());

		trab.setNombres("Adrian");
		trab.setApePater("Torres");
		trab.setApeMater("Morales");
		trab.setNroDoc("72868572");
		trab.setFecNac(time);
		trab.setSexo("M");
		trab.setCorreo("adrian@gmail.com");
		trab.setNroCel("98874563");
		trab.setDireccion("Av. Los cedros");
		trab.setNomZona("Las Flores");
		trab.setReferencia("Espalda de la iglesia");
		trab.setNroHij(0);
		trab.setFecRegPens(time);
		trab.setNroCuspp("123456");
		trab.setFecIngrSalud(time);
		trab.setNroEssalud("654321");
		trab.setEssaludVida(5);
		trab.setAfilAseguPens(4);
		trab.setEpsServProp(3);
		trab.setComiMixta(0);

		trab.setTipoDoc(this.nuevoTipoDoc());
		trab.setPais(this.nuevoPais());
		trab.setEstadoCivil(this.nuevoEstadoCivil());
		trab.setDepartamento(this.nuevoDepartamento());
		trab.setProvincia(this.nuevoProvincia());
		trab.setDistrito(this.nuevoDistrito());
		trab.setTipoZona(this.nuevoTipoZona());
		trab.setNivelEdu(this.nuevoNivelEdu());
		trab.setOcupacion(this.nuevoOcupacion());
		trab.setEmpresa(this.nuevaEmpresa());
		trab.getEmpresa().setIdEmpresa(1);
		trab.setAfp(this.nuevoAfp());
		trab.getAfp().setIdAfp(1);

		// ACA PUEDE HABER ERROR ---------------------------------------------
		trab.setEps(this.nuevoEpsPension());
		//trab.setEps(this.nuevoEpsRegSalud());
		//trab.setEps(this.nuevoEpsSalud());

		// trab.setEps(this.nuevoEps());
		// trab.getEps().setIdEps(1);
		trab.setRegSalud(this.nuevoRegSalud());
		// trab.getRegSalud().setIdRegSalud(1);
		trab.setSituacion(this.nuevoSituacion());
		// trab.getSituacion().setIdSituacion(1);

		return trab;
	}

	/*public CategoriaDTO nuevoCategoriaDTO() { // READY
		CategoriaDTO catDTO = new CategoriaDTO();
		catDTO.setCategoria(this.nuevoCategoria());
		catDTO.setCargo(this.nuevoCargo());
		return catDTO;
	}*/

	public Empresa nuevaEmpresa() { // READY
		Date hoy = new Date();
		//Timestamp fecha = new Timestamp(hoy.getTime());
		Empresa emp = new Empresa();
		emp.setRazonSocial("PARTNER TECH E.I.R.L.");
		emp.setRuc("20523799623");
		//emp.setFechaRegistro(fecha);
		emp.setEstado(1);
		emp.setUbicacion("av malachosky 123");
		//emp.setTipoEmp(this.nuevoTipoEmpresa());
		emp.setRegTrib(this.nuevoRegimenTributario());
		return emp;
	}
	
	public CuentaCargoDTO nuevaCuentaCargoDTO(){
		CuentaCargoDTO cuentaCargoDTO = new CuentaCargoDTO();
		cuentaCargoDTO.setContrato(nuevoContrato());
		cuentaCargoDTO.setCuentaCargo(nuevaCuentaCargo());
		cuentaCargoDTO.setDescripcion("file.docx");
		cuentaCargoDTO.setEmpresa(nuevaEmpresa());
		cuentaCargoDTO.setPdoAno(nuevoPdoAno());
		cuentaCargoDTO.setPdoMes(nuevoPdoMes());
		return cuentaCargoDTO;
	}

	public CuentaCargo nuevaCuentaCargo() {
		CuentaCargo cueCargo = new CuentaCargo();
		// cueCargo.setIdCuentaCargo(3);
		cueCargo.setDescripcion("Cargo1");
		cueCargo.setNroCuenta("5423654125412");
		cueCargo.setTipoCuenta(2);
		cueCargo.setTipoMoneda(1);
		cueCargo.setBanco(this.nuevoBancoSueldo());
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		cueCargo.setEmpresa(emp);

		return cueCargo;
	}

	public TipoEmpresa nuevoTipoEmpresa() { // READY
		TipoEmpresa tipEmp = new TipoEmpresa();
		tipEmp.setIdTipoEmp(2);
		tipEmp.setDescripcion("PEQUEÑA EMPRESA");
		return tipEmp;
	}

	public RegimenTributario nuevoRegimenTributario() { // READY
		RegimenTributario regTri = new RegimenTributario();
		regTri.setIdRegTrib(1);
		regTri.setDescripcion("MICROEMPRESA");
		return regTri;
	}

	public TipoZona nuevoTipoZona() { // READY
		TipoZona tz = new TipoZona();
		tz.setIdTipoZona(8);
		tz.setDescripcion("RES. RESIDENCIAL");
		return tz;
	}

	public TipoDoc nuevoTipoDoc() { // READY
		TipoDoc td = new TipoDoc();
		td.setIdTipoDoc(1);
		td.setDescripcion("DNI");
		return td;
	}

	public Situacion nuevoSituacion() { // READY
		Situacion sit = new Situacion();
		sit.setIdSituacion(1);
		sit.setDescripcion("ACTIVO O SUBSIDIADO");
		return sit;
	}

	public RegSalud nuevoRegSalud() { // READY
		RegSalud rs = new RegSalud();
		rs.setIdRegSalud(3);
		rs.setDescripcion("ESSALUD REGULAR (Exclusivamente)");
		return rs;
	}

	public Departamento nuevoDepartamento() { // READY
		Departamento depa = new Departamento();
		depa.setIdDepartamento(14);
		depa.setDescripcion("LIMA");
		return depa;
	}

	public Provincia nuevoProvincia() { // READY
		Provincia prov = new Provincia();
		prov.setIdProvincia(134);
		prov.setDescripcion("LIMA");
		prov.setDepartamento(this.nuevoDepartamento());
		return prov;
	}

	public Distrito nuevoDistrito() { // READY
		Distrito dist = new Distrito();
		dist.setIdDistrito(1371);
		dist.setDescripcion("SANTIAGO DE SURCO");
		dist.setProvincia(this.nuevoProvincia());
		return dist;
	}

	public Pais nuevoPais() { // READY
		Pais pais = new Pais();
		pais.setIdPais(1);
		pais.setDescripcion("Afganistán");
		return pais;
	}

	public Ocupacion nuevoOcupacion() { // READY
		Ocupacion ocup = new Ocupacion();
		ocup.setIdOcupacion(3806);
		ocup.setDescripcion("PROGRAMADOR, CODIFICADOR DE LOS PROGRAMAS");
		return ocup;
	}

	public NivelEdu nuevoNivelEdu() { // READY
		NivelEdu nivedu = new NivelEdu();
		nivedu.setIdNivelEdu(11);
		nivedu.setDescripcion("EDUCACIÓN UNIVERSITARIA COMPLETA");
		return nivedu;
	}

	public EstadoCivil nuevoEstadoCivil() { // READY
		EstadoCivil estciv = new EstadoCivil();
		estciv.setIdEstadoCivil(1);
		estciv.setDescripcion("SOLTERO");
		return estciv;
	}

	public Eps nuevoEpsRegSalud() { // READY
		Eps eps = new Eps();
		eps.setIdEps(3);
		eps.setDescripcion("PACÍFICO S.A. EPS");
		return eps;
	}

	public Eps nuevoEpsSalud() { // READY
		Eps eps = new Eps();
		eps.setIdEps(3);
		eps.setDescripcion("PACÍFICO S.A. EPS");
		return eps;
	}

	public Eps nuevoEpsPension() { // READY
		Eps eps = new Eps();
		eps.setIdEps(3);
		eps.setDescripcion("PACÍFICO S.A. EPS");
		eps.setAporte(20.0);
		return eps;
	}

	public Afp nuevoAfp() { // READY
		Afp afp = new Afp();
		// afp.setIdAfp(1);
		afp.setApoOblFndPen(0.13);
		afp.setComAnuSobSal(0.0);
		afp.setComSobFlu(0.0);
		afp.setComSobFluMix(0.0);
		afp.setDescripcion("ONP");
		afp.setPrimaSeguro(0.0);
		afp.setEmpresa(nuevaEmpresa());
		return afp;
	}

	public TipoPago nuevoTipoPago() { // READY
		TipoPago tipago = new TipoPago();
		tipago.setIdTipoPago(1);
		tipago.setDescripcion("DEPOSITO CUENTA");
		return tipago;
	}

	public TipoContrato nuevoTipoContrato() { // READY
		TipoContrato ticont = new TipoContrato();
		ticont.setIdTipContrato(4);
		ticont.setDescripcion("ADMINISTRATIVO DE SERVICIOS - D.LEG 1057 (1)");
		return ticont;
	}

	public Parametro nuevoParametro() { // READY
		Parametro par = new Parametro();
		par.setCodigo("COD_SALUD");
		par.setDescripcion("EsSalud");
		par.setEstado(1);
		par.setGrupo("Empresa");
		par.setNombre("EsSalud");
		par.setValor("5");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		par.setEmpresa(emp);
		return par;
	}
	
	public Parametro diasComputables() {
		Parametro para = new Parametro();
		para.setIdParametro(1);
		para.setCodigo("DIASCOMPTBASE");
		para.setDescripcion("Dias Computables Base");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Dias Computables Base");
		para.setValor("30");
		
		return para;
	}
	
	public Parametro salMinVital() {
		Parametro para = new Parametro();
		para.setIdParametro(14);
		para.setCodigo("SALMINVIT");
		para.setDescripcion("Salario Minimo Vital");
		para.setEstado(1);
		para.setGrupo("EMPRESA");
		para.setNombre("Salario Minimo Vital");
		para.setValor("930");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		para.setEmpresa(emp);
		
		return para;
	}
	
	public Parametro ctsAgragio() {
		Parametro para = new Parametro();
		para.setIdParametro(11);
		para.setCodigo("CTSAGRARIO");
		para.setDescripcion("Valor del CTS para Regimen Agrario");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Valor del CTS para Regimen Agrario");
		para.setValor("36.69");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		para.setEmpresa(emp);
		
		return para;
	}
	
	public Parametro ONP() {
		Parametro para = new Parametro();
		para.setIdParametro(24);
		para.setCodigo("ONP");
		para.setDescripcion("Oficina de Normalizacion Pensional");
		para.setEstado(1);
		para.setGrupo("EMPRESA");
		para.setNombre("Oficina de Normalizacion Pensional");
		para.setValor("1");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		para.setEmpresa(emp);
		
		return para;
	}
	
	public Parametro tipoTardanza() {
		Parametro para = new Parametro();
		para.setIdParametro(21);
		para.setCodigo("TIPTARD");
		para.setDescripcion("Tipo de tardanza");
		para.setEstado(1);
		para.setGrupo("EMPRESA");
		para.setNombre("Tipo de tardanza");
		para.setValor("2");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		para.setEmpresa(emp);
		
		return para;
	}
	
	public Parametro cantDias() {
		Parametro para = new Parametro();
		para.setIdParametro(22);
		para.setCodigo("TARCNTDIAS");
		para.setDescripcion("Tardanza por cantidad de días");
		para.setEstado(0);
		para.setGrupo("EMPRESA");
		para.setNombre("Tardanza por cantidad de días");
		para.setValor("3");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		para.setEmpresa(emp);
		
		return para;
	}
	
	public Parametro tipoRango() {
		Parametro para = new Parametro();
		para.setIdParametro(23);
		para.setCodigo("TIPORANGO");
		para.setDescripcion("Tipo de rango");
		para.setEstado(1);
		para.setGrupo("EMPRESA");
		para.setNombre("Tipo de rango");
		para.setValor("2");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		para.setEmpresa(emp);
		
		return para;
	}
	
	public Parametro gratPrd1Inicio() {
		Parametro para = new Parametro();
		para.setIdParametro(2);
		para.setCodigo("PERIODOGRATI1INI");
		para.setDescripcion("Primero de Enero");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Primero de Enero");
		para.setValor("01-01");
		
		return para;
	}
	
	public Parametro gratPrd1Fin() {
		Parametro para = new Parametro();
		para.setIdParametro(3);
		para.setCodigo("PERIODOGRATI1FIN");
		para.setDescripcion("Treinta de  Junio");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Treinta de  Junio");
		para.setValor("06-30");
		
		return para;
	}
	
	public Parametro gratPrd2Inicio() {
		Parametro para = new Parametro();
		para.setIdParametro(4);
		para.setCodigo("PERIODOGRATI2INI");
		para.setDescripcion("Primero de Julio");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Primero de Julio");
		para.setValor("07-01");
		
		return para;
	}
	
	public Parametro gratPrd2Fin() {
		Parametro para = new Parametro();
		para.setIdParametro(5);
		para.setCodigo("PERIODOGRATI2FIN");
		para.setDescripcion("Treinta y uno de Diciembre");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Treinta y uno de Diciembre");
		para.setValor("12-31");
		
		return para;
	}
	
	public Parametro ctsPrd1Inicio() {
		Parametro para = new Parametro();
		para.setIdParametro(6);
		para.setCodigo("PERIODOCTS1INI");
		para.setDescripcion("Primero de Mayo");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Primero de Mayo");
		para.setValor("05-01");
		
		return para;
	}
	
	public Parametro ctsPrd1Fin() {
		Parametro para = new Parametro();
		para.setIdParametro(7);
		para.setCodigo("PERIODOCTS1FIN");
		para.setDescripcion("Treinta y uno de Octubre");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Treinta y uno de Octubre");
		para.setValor("10-31");
		
		return para;
	}
	
	public Parametro ctsPrd2Inicio() {
		Parametro para = new Parametro();
		para.setIdParametro(8);
		para.setCodigo("PERIODOCTS2INI");
		para.setDescripcion("Primero de Noviembre");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Primero de Noviembre");
		para.setValor("11-01");
		
		return para;
	}
	
	public Parametro ctsPrd2Fin() {
		Parametro para = new Parametro();
		para.setIdParametro(9);
		para.setCodigo("PERIODOCTS2FIN");
		para.setDescripcion("Treinta de Abril");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Treinta de Abril");
		para.setValor("04-30");
		
		return para;
	}
	
	public Parametro ctsPesquero() {
		Parametro para = new Parametro();
		para.setIdParametro(12);
		para.setCodigo("CTSPESQUERO");
		para.setDescripcion("Valor del CTS para Regimen Pesquero");
		para.setEstado(1);
		para.setGrupo("GLOBAL");
		para.setNombre("Valor del CTS para Regimen Pesquero");
		para.setValor("0.0833");
		
		return para;
	}
	
	public Parametro UIT() {
		Parametro para = new Parametro();
		para.setIdParametro(16);
		para.setCodigo("UIT");
		para.setDescripcion("Unidad Impositiva Tributaria");
		para.setEstado(1);
		para.setGrupo("EMPRESA");
		para.setNombre("Unidad Impositiva Tributaria");
		para.setValor("4200");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		para.setEmpresa(emp);
		
		return para;
	}
	
	public Parametro esSalud() {
		Parametro para = new Parametro();
		para.setIdParametro(18);
		para.setCodigo("ESSALUDVIDA");
		para.setDescripcion("Aportacion EsSalud");
		para.setEstado(1);
		para.setGrupo("EMPRESA");
		para.setNombre("Aportacion EsSalud");
		para.setValor("0.09");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		para.setEmpresa(emp);
		
		return para;
	}
	
	public Parametro bonif29351() {
		Parametro para = new Parametro();
		para.setIdParametro(17);
		para.setCodigo("BONEXT29351");
		para.setDescripcion("Bonificacion Extraordinaria Ley 29351");
		para.setEstado(1);
		para.setGrupo("EMPRESA");
		para.setNombre("Bonificacion Extraordinaria Ley 29351");
		para.setValor("0.09");
		Empresa emp = nuevaEmpresa();
		emp.setIdEmpresa(1);
		para.setEmpresa(emp);
		
		return para;
	}
	
	public Rhe nuevoRhe() {
		Rhe rhe = new Rhe();
		
		Date hoy = new Date();
		Timestamp fechaHoy = new Timestamp(hoy.getTime());
		rhe.setFechaReg(fechaHoy);
		rhe.setNombreArchivo("");
		
		return rhe;
	}

	public Sctr nuevoSctrSalud() { // READY
		Sctr sctr = new Sctr();
		sctr.setIdSctr(2);
		sctr.setDescripcion("ESSALUD");
		return sctr;
	}

	public Sctr nuevoSctrPension() { // READY
		Sctr sctr = new Sctr();
		sctr.setIdSctr(4);
		sctr.setDescripcion("ONP");
		return sctr;
	}

	public RegimenLaboral nuevoRegimenLaboral() { // READY
		RegimenLaboral reglab = new RegimenLaboral();
		reglab.setIdRegLaboral(2);
		reglab.setDescripcion("CONSTRUCCION CIVIL");
		return reglab;
	}

	public CentroCosto nuevoCentroCosto() { // READY
		CentroCosto cencos = new CentroCosto();
		// cencos.setIdCentroCosto(1);
		cencos.setDescripcion("PRODUCCION");
		cencos.setEmpresa(this.nuevaEmpresa());
		return cencos;
	}

	/*public Categoria nuevoCategoria() { // READY
		Categoria cate = new Categoria();
		// cate.setIdCategoria(1);
		cate.setDescripcion("EMPLEADO");
		cate.setEmpresa(this.nuevaEmpresa());
		return cate;
	}

	public Cargo nuevoCargo() { // READY
		Cargo cargo = new Cargo();
		// cargo.setIdCargo(1);
		cargo.setDescripcion("PROGRAMADOR");
		cargo.setCategoria(this.nuevoCategoria());
		return cargo;
	}*/

	public Banco nuevoBancoSueldo() { // READY
		Banco banco = new Banco();
		banco.setIdBanco(2);
		banco.setDescripcion("BANCO DE CREDITO DEL PERU");
		return banco;
	}

	public Banco nuevoBancoCts() { // READY
		Banco banco = new Banco();
		banco.setIdBanco(2);
		banco.setDescripcion("BANCO DE CREDITO DEL PERU");
		return banco;
	}
	
	public PostulanteDTO nuevoPostulanteDTO() {
		PostulanteDTO postDto = new PostulanteDTO();
		
		Ocupacion ocup = nuevoOcupacion();
		ocup.setIdOcupacion(1);		
		NivelEdu nivE = nuevoNivelEdu();
		nivE.setIdNivelEdu(1);
		TipoDoc tipDoc = nuevoTipoDoc();
		tipDoc.setIdTipoDoc(1);
		
		Postulante pos = nuevoPostulante();
		pos.setEstado(1);		
		postDto.setPostulante(pos);		
		postDto.setTipoDoc(tipDoc);
		postDto.setOcupacion(ocup);
		postDto.setNivelEdu(nivE);
		
		return postDto;
	}
	
	public Postulante nuevoPostulante() {
		Postulante post = new Postulante();
					
		post.setNombres("Diana");
		post.setApe_paterno("Choque");
		post.setApe_materno("Torres");
		post.setFecha_nacimiento(new Timestamp(new Date().getTime()));
		post.setNro_documento(79854125);
		post.setTelefono(985625874);
		post.setCorreo("d@gmail.com");
		post.setTiempo_experiencia(2);
		post.setUnidad_tiempo("A");
		post.setPretension(1500.00);
		post.setComentarios("adsadas");
		//post.setEstado(1);
		return post;
	}
}
