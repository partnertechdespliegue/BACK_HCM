package com.mitocode.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.icu.util.Calendar;
import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.Planilla;
import com.mitocode.dto.PlanillaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.TrabajadorDTO;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.AdelantoSueldo;
import com.mitocode.model.Asistencia;
import com.mitocode.model.Contrato;
import com.mitocode.model.CuotaAdelanto;
import com.mitocode.model.Empresa;
import com.mitocode.model.Falta;
import com.mitocode.model.Horario;
import com.mitocode.model.Parametro;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.PlanillaHistoricaDsctRemu;
import com.mitocode.model.PlanillaHistorico;
import com.mitocode.model.TipoPlanilla;
import com.mitocode.model.TrabDescuento;
import com.mitocode.model.TrabRemuneracion;
import com.mitocode.model.Trabajador;
import com.mitocode.model.VacacionesTomadas;
import com.mitocode.model.VacacionesVendidas;
import com.mitocode.service.AdelantoSueldoService;
import com.mitocode.service.AsistenciaService;
import com.mitocode.service.ContratoService;
import com.mitocode.service.CuotaAdelantoService;
import com.mitocode.service.FaltasService;
import com.mitocode.service.ParametroService;
import com.mitocode.service.PlanillaHistoricaDsctRemuService;
import com.mitocode.service.PlanillaHistoricoService;
import com.mitocode.service.TipoPlanPerfilService;
import com.mitocode.service.TipoPlanillaDetalleService;
import com.mitocode.service.TrabDescuentoService;
import com.mitocode.service.TrabRemuneracionService;
import com.mitocode.service.VacacionesTomadasService;
import com.mitocode.service.VacacionesVendidasService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/planilla")
public class PlanillaController {

	@Autowired
	PlanillaHistoricoService service;

	@Autowired
	AdelantoSueldoService service_as;

	@Autowired
	CuotaAdelantoService service_ca;

	@Autowired
	TipoPlanillaDetalleService service_tpd;

	@Autowired
	ContratoService service_contrato;

	@Autowired
	FaltasService service_falta;

	@Autowired
	VacacionesTomadasService service_vactoma;

	@Autowired
	AsistenciaService service_asistencia;

	@Autowired
	VacacionesVendidasService service_vacven;

	@Autowired
	ParametroService service_para;

	@Autowired
	PlanillaHistoricaDsctRemuService service_planHDsctRemu;

	@Autowired
	TrabDescuentoService service_trabDsct;

	@Autowired
	TrabRemuneracionService service_trabRemu;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/generarPlanilla")
	public ResponseWrapper generarPlanilla(@RequestBody PlanillaDTO planillaDTO, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();

			}).collect(Collectors.toList());
			errors.remove(null);
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/generarPlanilla",
					"Error en la validacion: Lista de Errores:" + errors.toString(), planillaDTO);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();

			if (planillaDTO.getContrato() == null) {

				PlanillaDTO dto = planillaDTO;

				int tipoCategoria = tipoCategoria(dto.getTipoPlanilla());

				List<Contrato> lscontrato = service_contrato
						.listarPorEmpresaYTipoComp(dto.getTipoPlanilla().getEmpresa(), tipoCategoria);

				// Obtener los contratos por tipo de planilla
				List<Contrato> lscontratoTP = service_tpd.armarListContrato(lscontrato, dto.getTipoPlanilla());

				for (Contrato contrato : lscontratoTP) {

					dto.setContrato(contrato);
					if (service.encontrarPlanilla(dto) == null) {

						Planilla planilla = crearObjetoPlanilla(dto);
						dto.setPlanilla(planilla);

						List<PlanillaHistoricaDsctRemu> lsplanhDsctRemu = obtenerLsPHDsctRemu(dto);
						// [0] dsct [1] remu
						Double[] dsctRemu = obtenerTotalDsctRemu(lsplanhDsctRemu);

						PlanillaHistorico tmp_planilla_historico = crearPlanilla(dto, dsctRemu);

						PlanillaHistorico resp = service.registrar(tmp_planilla_historico);

						for (PlanillaHistoricaDsctRemu phDR : lsplanhDsctRemu) {
							phDR.setPlanillaHistorico(resp);
						}

						service_planHDsctRemu.registrarLista(lsplanhDsctRemu);
					}
				}

				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgGenerarPlanillaOK);

			} else {

				PlanillaHistorico res_planilla_historico = new PlanillaHistorico();
				// Double rem_jor_norm = 0.00;

				Planilla planilla = crearObjetoPlanilla(planillaDTO);

				planillaDTO.setPlanilla(planilla);

				List<PlanillaHistoricaDsctRemu> lsplanhDsctRemu = obtenerLsPHDsctRemu(planillaDTO);
				// [0] dsct [1] remu
				Double[] dsctRemu = obtenerTotalDsctRemu(lsplanhDsctRemu);

				PlanillaHistorico tmp_planilla_historico = this.crearPlanilla(planillaDTO, dsctRemu);
				res_planilla_historico = service.registrar(tmp_planilla_historico);

				if (res_planilla_historico != null) {

					for (PlanillaHistoricaDsctRemu phDR : lsplanhDsctRemu) {
						phDR.setPlanillaHistorico(res_planilla_historico);
					}

					response.setAaData(service_planHDsctRemu.registrarLista(lsplanhDsctRemu));
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgGenerarPlanillaOK);
					response.setDefaultObj(res_planilla_historico);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgGenerarPlanillaError);
				}
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + "generarPlanilla. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/generarPlanilla",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					planillaDTO);
		}
	}

	private Planilla crearObjetoPlanilla(PlanillaDTO planillaDTO) {

		Planilla planilla = new Planilla();
		Contrato contrato = planillaDTO.getContrato();
		PdoAno pdoAno = planillaDTO.getPdoAno();
		PdoMes pdoMes = planillaDTO.getPdoMes();
		Empresa empresa = planillaDTO.getTipoPlanilla().getEmpresa();
		Parametro tipoTardanza = planillaDTO.getTardanza();
		Parametro tolerancia = obtenerTolerancia(empresa);
		int toleranciaMinutos = Integer.parseInt(tolerancia.getValor());

		planilla.setPdo_ano(pdoAno);
		planilla.setPdo_mes(pdoMes);

		List<Falta> lsfaltaInjustificadas = service_falta.buscarFaltas(Constantes.ConsInActivo, pdoAno, pdoMes,
				contrato.getTrabajador());
		List<Falta> lsfaltaJustificadas = service_falta.buscarFaltas(Constantes.ConsInActivo, pdoAno, pdoMes,
				contrato.getTrabajador());
		List<VacacionesTomadas> lsvacacionTomada = service_vactoma.encontrarVacacionTomada(contrato.getTrabajador(),
				pdoAno, pdoMes);
		int cantDiasVT = vacacionesTomadas(lsvacacionTomada, pdoAno, pdoMes);
		List<Asistencia> lsasistencia = service_asistencia.buscarPorTrabajador(contrato.getTrabajador(), pdoAno,
				pdoMes);
		int cantDiasAsistidos = diasComputables(lsasistencia, pdoMes);
		int diasFeriadoLaborados = feriadosLaborado(lsasistencia, pdoMes);
		List<VacacionesVendidas> lsvacacionVendidas = service_vacven.listarPorTrabPeriodo(contrato.getTrabajador(),
				pdoAno, pdoMes);
		int diasVacacionesVendidas = diasVacacionesVendidas(lsvacacionVendidas);
		Horario horario = contrato.getTrabajador().getHorario();
		int tardCantDiasTotal = 0;
		int tardanzaMininutos = 0;
		if (tipoTardanza.getValor().equals("1")) {
			tardCantDiasTotal = obtenerTardanzaDias(lsasistencia, empresa, horario, toleranciaMinutos);
		} else {
			Parametro rangoMinuto = planillaDTO.getTipoRango();
			tardanzaMininutos = obtenerTardanzaMinutos(lsasistencia, rangoMinuto, horario, toleranciaMinutos);
		}
		double[] lshorasExtras = obtenerHorasExtras(lsasistencia, horario);
		double deudaAdelantoSueldo = pagarDeuda(planillaDTO);

		planilla.setFaltas_injusti(lsfaltaInjustificadas.size());
		planilla.setFaltas_justi(lsfaltaJustificadas.size());
		planilla.setDias_vacaciones(cantDiasVT);
		planilla.setDias_computables(cantDiasAsistidos);
		planilla.setFerdo_laborad(diasFeriadoLaborados);
		planilla.setVacaciones_vend(diasVacacionesVendidas);
		planilla.setHo_extra25(lshorasExtras[0]);
		planilla.setHo_extra35(lshorasExtras[1]);
		planilla.setTardanzas(tardCantDiasTotal);
		// planilla.setMinTardanzas(tardanzaMininutos);
		planilla.setAdelanto(deudaAdelantoSueldo);
		planilla.setDias_ferdo(planillaDTO.getPdoMes().getDiasFeriadoCalend());
		planilla.setFaltantes(0.0);
		planilla.setPrestamos(0.0);
		planilla.setComisiones(0.0);

		return planilla;
	}

	private Double[] obtenerTotalDsctRemu(List<PlanillaHistoricaDsctRemu> lsplanhDsctRemus) {

		Double[] remuDsct = new Double[2];

		double totalDsct = 0.0;
		double totalRemu = 0.0;

		for (PlanillaHistoricaDsctRemu phDsctRemu : lsplanhDsctRemus) {
			if (phDsctRemu.getDescuentos() != null) {
				totalDsct += phDsctRemu.getDmonto();
			} else {
				totalRemu += phDsctRemu.getDmonto();
			}
		}

		remuDsct[0] = totalDsct;
		remuDsct[1] = totalRemu;

		return remuDsct;
	}

	private List<PlanillaHistoricaDsctRemu> obtenerLsPHDsctRemu(PlanillaDTO planillaDTO) {
		List<TrabDescuento> lstrabDsct = service_trabDsct.listarXTrab(planillaDTO.getContrato().getTrabajador());
		List<TrabRemuneracion> lstrabRemu = service_trabRemu.listarXTrab(planillaDTO.getContrato().getTrabajador());

		List<PlanillaHistoricaDsctRemu> lsphDsctRemu = new ArrayList<>();

		if (lstrabDsct.size() > 0) {
			for (TrabDescuento tD : lstrabDsct) {
				PlanillaHistoricaDsctRemu phDR = new PlanillaHistoricaDsctRemu();
				// phDR.setPlanillaHistorico(planillaHistorico);
				phDR.setDescuentos(tD.getDescuentos());
				phDR.setDmonto(tD.getDescuentos().getMontoFijo());

				// PlanillaHistoricaDsctRemu resp_phDR = service_planHDsctRemu.registrar(phDR);
				lsphDsctRemu.add(phDR);
			}
		}

		if (lstrabRemu.size() > 0) {
			for (TrabRemuneracion tR : lstrabRemu) {
				PlanillaHistoricaDsctRemu phDR = new PlanillaHistoricaDsctRemu();
				// phDR.setPlanillaHistorico(planillaHistorico);
				phDR.setRemuneraciones(tR.getRemuneraciones());
				phDR.setDmonto(tR.getRemuneraciones().getMontoFijo());

				// PlanillaHistoricaDsctRemu resp_phDR = service_planHDsctRemu.registrar(phDR);
				lsphDsctRemu.add(phDR);
			}
		}
		System.out.println("Frenar");
		return lsphDsctRemu;
	}

	private int tipoCategoria(TipoPlanilla tipoPlanilla) {
		if (tipoPlanilla.getCategoriaPlanilla() == 5)
			return 1;
		return 2;
	}

	private int diasComputables(List<Asistencia> lsasistencia, PdoMes pdoMes) {
		int cantDiasAsistidos = lsasistencia.size();

		switch (pdoMes.getIdPdoMes()) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			cantDiasAsistidos -= 1;
			break;
		case 2:
			if (pdoMes.getCantidadDias() == 29) {
				cantDiasAsistidos += 1;
			} else {
				cantDiasAsistidos += 2;
			}
			break;
		default:
			break;
		}
		if (cantDiasAsistidos < 0) {
			return 0;
		} else {
			return cantDiasAsistidos;
		}
	}

	private int vacacionesTomadas(List<VacacionesTomadas> lsvacacionTomada, PdoAno pdoAno, PdoMes pdoMes) {
		int cantDiasVT = 0;
		if (lsvacacionTomada.size() > 0) {
			for (VacacionesTomadas vacacionesTomadas : lsvacacionTomada) {
				if (vacacionesTomadas.getTipo() == 0) {
					cantDiasVT += vacacionesTomadas.getPdoVacacion().getDiasTomados();
				} else {
					if (vacacionesTomadas.getPdoMesIni().getIdPdoMes() == pdoMes.getIdPdoMes()
							&& vacacionesTomadas.getPdoAnoIni().getIdPdoAno() == pdoAno.getIdPdoAno()) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(vacacionesTomadas.getFechaIni());
						int diasInicio = calendar.get(Calendar.DAY_OF_MONTH);
						cantDiasVT += pdoMes.getCantidadDias() - diasInicio + 1;
					} else if (vacacionesTomadas.getPdoMesFin().getIdPdoMes() == pdoMes.getIdPdoMes()
							&& vacacionesTomadas.getPdoAnoFin().getIdPdoAno() == pdoAno.getIdPdoAno()) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(vacacionesTomadas.getFechaFin());
						cantDiasVT += calendar.get(Calendar.DAY_OF_MONTH);
					}
				}
			}
		}
		return cantDiasVT;
	}

	private int feriadosLaborado(List<Asistencia> lsasistencia, PdoMes pdoMes) {
		int diasFeriadoLaborados = 0;
		if (pdoMes.getTxtDiasFeriados() != null) {
			String[] feriados = pdoMes.getTxtDiasFeriados().split(",");
			for (int i = 0; i < feriados.length; i++) {
				int diaFeriado = Integer.parseInt(feriados[i]);
				Calendar calendar = Calendar.getInstance();
				for (Asistencia asistencia : lsasistencia) {
					calendar.setTime(asistencia.getFecha());
					int diaLaborado = calendar.get(Calendar.DAY_OF_MONTH);
					if (diaFeriado == diaLaborado) {
						diasFeriadoLaborados += 1;
					}
				}
			}
		}
		return diasFeriadoLaborados;
	}

	private int diasVacacionesVendidas(List<VacacionesVendidas> lsvacacionVendidas) {
		int diasVacacionesVendidas = 0;
		if (lsvacacionVendidas.size() > 0) {
			for (VacacionesVendidas vacacionesVendidas : lsvacacionVendidas) {
				diasVacacionesVendidas += vacacionesVendidas.getCantDias();
			}
		}
		return diasVacacionesVendidas;
	}

	private Parametro obtenerTolerancia(Empresa empresa) {
		EmpresaDTO empresaDTO = new EmpresaDTO();
		Parametro tolerancia = new Parametro();
		tolerancia.setCodigo("TIEMTOLE");
		empresaDTO.setEmpresa(empresa);
		empresaDTO.setParametro(tolerancia);
		return service_para.buscarPorCodigoAndEmpresa(empresaDTO);
	}

	private Parametro obtenerTardanzaCantDias(Empresa empresa) {
		EmpresaDTO empresaDTO = new EmpresaDTO();
		Parametro cantidadDias = new Parametro();
		cantidadDias.setCodigo("TARCNTDIAS");
		empresaDTO.setEmpresa(empresa);
		empresaDTO.setParametro(cantidadDias);
		return service_para.buscarPorCodigoAndEmpresa(empresaDTO);
	}

	private int obtenerTardanzaMinutos(List<Asistencia> lsasistencia, Parametro rangoMinuto, Horario horario,
			int toleranciaMinutos) {
		int tardanzaMininutos = 0;

		Calendar asistenciaInicio = Calendar.getInstance();
		Calendar horarioInicio = Calendar.getInstance();
		horarioInicio.setTime(horario.getHorIniDia());
		int horarioHora = horarioInicio.get(Calendar.HOUR_OF_DAY);
		int horarioMinuto = horarioInicio.get(Calendar.MINUTE);

		for (Asistencia asistencia : lsasistencia) {
			asistenciaInicio.setTime(asistencia.getHorIniDia());
			int asistHora = asistenciaInicio.get(Calendar.HOUR_OF_DAY);
			int asistMinuto = asistenciaInicio.get(Calendar.MINUTE);
			if (horarioHora == asistHora) {
				if (horarioMinuto < asistMinuto) {
					int tardanzaMin = asistMinuto - horarioMinuto;
					if (toleranciaMinutos < tardanzaMin) {
						if (rangoMinuto.getValor().equals("1")) {
							tardanzaMininutos += (tardanzaMin - toleranciaMinutos);
						} else {
							int valorSinRedondear = tardanzaMin - toleranciaMinutos;
							tardanzaMininutos += ((valorSinRedondear + 9) / 10) * 10;
						}
					}
				}
			} else if (horarioHora < asistHora) {
				tardanzaMininutos += (asistHora - horarioHora) * 60;
				if (horarioMinuto < asistMinuto) {
					if (rangoMinuto.getValor().equals("1")) {
						tardanzaMininutos += (asistMinuto - horarioMinuto) - toleranciaMinutos;
					} else {
						int valorSinRedondear = (asistMinuto - horarioMinuto) - toleranciaMinutos;
						tardanzaMininutos += ((valorSinRedondear + 9) / 10) * 10;
					}
				}
			}
		}

		return tardanzaMininutos;
	}

	private int obtenerTardanzaDias(List<Asistencia> lsasistencia, Empresa empresa, Horario horario,
			int toleranciaMinutos) {
		int tardCantDiasTotal = 0;
		Parametro tardanzaCantDias = obtenerTardanzaCantDias(empresa);
		int tardCantDias = Integer.parseInt(tardanzaCantDias.getValor());

		int tardanzaDias = 0;
		Calendar asistenciaInicio = Calendar.getInstance();
		Calendar horarioInicio = Calendar.getInstance();
		horarioInicio.setTime(horario.getHorIniDia());
		int horarioHora = horarioInicio.get(Calendar.HOUR_OF_DAY);
		int horarioMinuto = horarioInicio.get(Calendar.MINUTE);

		for (Asistencia asistencia : lsasistencia) {
			asistenciaInicio.setTime(asistencia.getHorIniDia());
			int asistHora = asistenciaInicio.get(Calendar.HOUR_OF_DAY);
			int asistMinuto = asistenciaInicio.get(Calendar.MINUTE);
			if (horarioHora == asistHora) {
				if (horarioMinuto < asistMinuto) {
					int tardanzaMin = asistMinuto - horarioMinuto;
					if (toleranciaMinutos < tardanzaMin) {
						tardanzaDias += 1;
					}
				}
			} else if (horarioHora < asistHora) {
				tardanzaDias += 1;
			}
		}

		if (tardanzaDias >= tardCantDias) {
			tardCantDiasTotal = tardanzaDias / tardCantDias;
		}

		return tardCantDiasTotal;
	}

	private double[] obtenerHorasExtras(List<Asistencia> lsasistencia, Horario horario) {

		double[] lshorasExtras = new double[2];
		Calendar asistenciaFin = Calendar.getInstance();
		Calendar horarioFin = Calendar.getInstance();
		horarioFin.setTime(horario.getHorFinDia());
		int horarioHora = horarioFin.get(Calendar.HOUR_OF_DAY);
		int horarioMinuto = horarioFin.get(Calendar.MINUTE);
		int minutosExtras25 = 0;
		int minutosExtras35 = 0;
		for (Asistencia asistencia : lsasistencia) {
			asistenciaFin.setTime(asistencia.getHorFinDia());
			int asistenciaHora = asistenciaFin.get(Calendar.HOUR_OF_DAY);
			int asistenciaMinuto = asistenciaFin.get(Calendar.MINUTE);
			if (horarioHora == asistenciaHora) {
				// debe superar 5min
				if (asistenciaMinuto >= (horarioMinuto + 5)) {
					minutosExtras25 += asistenciaMinuto - horarioMinuto;
				}
			} else if (horarioHora < asistenciaHora) {
				int horaMinutosExtra = (asistenciaHora - horarioHora) * 60;
				if (horaMinutosExtra >= 120) {
					minutosExtras25 += 120;
					minutosExtras35 += (horaMinutosExtra + asistenciaMinuto) - 120;
				} else {
					minutosExtras25 += horaMinutosExtra + asistenciaMinuto;
				}
			}
		}

		DecimalFormat df = new DecimalFormat("#.###");
		lshorasExtras[0] = Double.parseDouble(df.format(minutosExtras25 / 60.0));
		lshorasExtras[1] = Double.parseDouble(df.format(minutosExtras35 / 60.0));

		return lshorasExtras;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/buscarPlanilla")
	public ResponseWrapper buscarPlanilla(@RequestBody PlanillaDTO planillaDTO, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();

			}).collect(Collectors.toList());
			errors.remove(null);
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/generarPlanilla",
					"Error en la validacion: Lista de Errores:" + errors.toString(), planillaDTO);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();

			PlanillaHistorico res_planilla = service.encontrarPlanilla(planillaDTO);
			if (res_planilla != null) {
				response.setEstado(Constantes.valTransaccionOk);
				String pdo_mes = planillaDTO.getPdoMes().getDescripcion();
				Integer pdo_ano = planillaDTO.getPdoAno().getDescripcion();
				response.setMsg(Constantes.msgEncontroPlanilla + pdo_mes + "-" + pdo_ano);
				response.setDefaultObj(res_planilla);
			} else {
				response.setEstado(Constantes.valTransaccionNoEncontro);
				response.setMsg(Constantes.msgBuscarPlanillaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + "buscarPlanilla. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/buscarPlanilla",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					planillaDTO);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarBoletas")
	public ResponseWrapper listarBoletas(@RequestBody TrabajadorDTO trabajadorDTO) throws Exception {

		if (trabajadorDTO.getContrato().getIdContrato() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarBoletas",
					Constantes.msgListarBoletasError + " no se ha especificado un contrato valida", trabajadorDTO);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();

			List res_planillas = service.listarBoletas(trabajadorDTO.getContrato().getIdContrato());

			if (res_planillas != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarBoletasOk);
				response.setAaData(res_planillas);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarBoletasError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + "listarBoletas. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarBoletas",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					trabajadorDTO);
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarPHDsctRemu")
	public ResponseWrapper listarPHDsctRemu(@RequestBody PlanillaHistorico planillaHistorico) throws Exception {

		if (planillaHistorico.getIdPlanillaHistorico() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarPHDsctRemu",
					Constantes.msgListarBoletasError + " no se ha especificado un contrato valida", planillaHistorico);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();

			List res_planillas = service_planHDsctRemu.listarPorPlanHistorico(planillaHistorico);

			if (res_planillas != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarBoletasOk);
				response.setAaData(res_planillas);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarBoletasError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + "listarPHDsctRemu. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarPHDsctRemu",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							planillaHistorico);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DeleteMapping("/{idPlanillaHistorico}")
	public ResponseWrapper borrarBoleta(@PathVariable("idPlanillaHistorico") Integer idPlanilla) throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();

			Boolean resp = service.eliminar(idPlanilla);

			if (resp) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEliminarBoletaOK);
				response.setDefaultObj(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgEliminarBoletaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + "eliminarBoleta. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/" + idPlanilla,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					idPlanilla);
		}
	}

	public PlanillaHistorico crearPlanilla(PlanillaDTO planilladto, Double[] dsctRemu) throws Exception {
		try {
			PlanillaHistorico res_planilla_historico = new PlanillaHistorico();
			Empresa empresa = planilladto.getContrato().getTrabajador().getEmpresa();

			res_planilla_historico.setContrato(planilladto.getContrato());
			res_planilla_historico.setTipoTardanza(planilladto.getTardanza());
			res_planilla_historico.setValorTipoTard(planilladto.getTardanza().getValor());
			if (planilladto.getTardanza().getValor().compareTo("2") == 0) {
				res_planilla_historico.setClaseTipoTardanza(planilladto.getTipoRango());
				res_planilla_historico.setValorClaseTipoTard(planilladto.getTipoRango().getValor());
			}
			res_planilla_historico.setPdoAno(planilladto.getPlanilla().getPdo_ano());
			res_planilla_historico.setPdoMes(planilladto.getPlanilla().getPdo_mes());
			res_planilla_historico.setDiaFerdo(planilladto.getPlanilla().getDias_ferdo());
			res_planilla_historico.setDiaCompbl(planilladto.getPlanilla().getDias_computables());
			res_planilla_historico.setHoExt25(planilladto.getPlanilla().getHo_extra25());
			res_planilla_historico.setHoExt35(planilladto.getPlanilla().getHo_extra35());
			res_planilla_historico.setDiaFerdoLabo(planilladto.getPlanilla().getFerdo_laborad());
			res_planilla_historico.setDiaVaca(planilladto.getPlanilla().getDias_vacaciones());
			res_planilla_historico.setTiempo_tardanza(planilladto.getPlanilla().getTardanzas());
			// res_planilla_historico.setTiempoMinTardanza(planilladto.getPlanilla().getMinTardanzas());
			res_planilla_historico.setFaltaInjusti(planilladto.getPlanilla().getFaltas_injusti());
			res_planilla_historico.setFaltaJusti(planilladto.getPlanilla().getFaltas_justi());
			res_planilla_historico.setComiMix(planilladto.getContrato().getTrabajador().getComiMixta());
			if (planilladto.getContrato().getBancoSueldo() != null) {
				res_planilla_historico.setIdBanco(planilladto.getContrato().getBancoSueldo().getIdBanco());
				res_planilla_historico.setNroCuentaBanco(planilladto.getContrato().getNroCta());
			}
			res_planilla_historico.setSueldoBasico(planilladto.getContrato().getSueldoBase());
			res_planilla_historico.setRemComisiones(planilladto.getPlanilla().getComisiones());

			res_planilla_historico.setMovilidad(service.calculoMovTotal(planilladto));
			res_planilla_historico.setRemHoExt25(service.calculoHorExtra25(planilladto));
			res_planilla_historico.setRemHoExt35(service.calculoHorExtra35(planilladto));
			res_planilla_historico.setEssaludVida(service.calculoEssaludVida(planilladto));
			res_planilla_historico.setAsigFam(service.calculoAsigFam(planilladto));
			res_planilla_historico.setRemFerdo(service.calculoRemFerdo(planilladto));
			res_planilla_historico.setRemDiaFerdoLabo(service.calculoRemDiaFerdoLabo(planilladto));
			res_planilla_historico.setRemDiaVaca(service.calculoRemDiaVaca(planilladto));
			res_planilla_historico.setRemGrati(service.calculoRemGrat(planilladto, true));
			res_planilla_historico.setRemVacaVend(service.calculoRemVacaVend(planilladto));
			res_planilla_historico.setRemFaltaJusti(service.calculoRemDiasJusti(planilladto));

			Double rem_comp_normal = service.calculoRemJorNorm(planilladto, false)
					+ res_planilla_historico.getRemFerdo() + res_planilla_historico.getRemFaltaJusti()
					+ res_planilla_historico.getRemDiaVaca();

			res_planilla_historico.setRemJorNorm(rem_comp_normal);

			Double rem_ferdo_laborad = res_planilla_historico.getRemDiaFerdoLabo();
			Double rem_jor_lab = res_planilla_historico.getRemJorNorm();
			Double rem_asig_fam = res_planilla_historico.getAsigFam();
			Double rem_vacaciones = res_planilla_historico.getRemDiaVaca();
			Double rem_vacaciones_vendidas = res_planilla_historico.getRemVacaVend();
			Double rem_hrs_extra = res_planilla_historico.getRemHoExt25() + res_planilla_historico.getRemHoExt35();
			Double rem_ferd = res_planilla_historico.getRemFerdo();

			Double total_comp = rem_ferdo_laborad + rem_jor_lab + rem_asig_fam + rem_vacaciones
					+ rem_vacaciones_vendidas + rem_hrs_extra + rem_ferd;
			res_planilla_historico.setTot_comp(total_comp);
			// FeriadoLaborado + Remuneracion Jornada Normal + Asig Familiar + vacaciones +
			// hrs extra + feriados

			res_planilla_historico.setBonif29351(service.calculoBonif29351(planilladto));

			switch (planilladto.getContrato().getRegimenLaboral().getIdRegLaboral()) {
			case 1:
				res_planilla_historico.setCts(0.0);
				res_planilla_historico.setRemJorNorm(service.calculoRemJorNorm(planilladto, true));
				break;
			case 2:
				double ctsConsCivil = 0.0;
				if (empresa.getRegTrib().getIdRegTrib() == 3) {
					ctsConsCivil = service.calculoCTSConsCivil(planilladto, res_planilla_historico.getRemJorNorm());
				} else if (empresa.getRegTrib().getIdRegTrib() == 2) {
					ctsConsCivil = service.calculoCTSConsCivil(planilladto, res_planilla_historico.getRemJorNorm()) / 2;
				}
				res_planilla_historico.setCts(ctsConsCivil);
				break;
			case 10:
				res_planilla_historico.setCts(0.0);
				break;
			case 14:
				double ctsPequeEmp = 0.0;
				if (empresa.getRegTrib().getIdRegTrib() == 3) {
					ctsPequeEmp = service.calculoCTSPequeEmp(planilladto);
				} else if (empresa.getRegTrib().getIdRegTrib() == 2) {
					ctsPequeEmp = service.calculoCTSPequeEmp(planilladto) / 2;
				}
				res_planilla_historico.setCts(ctsPequeEmp);
				break;
			case 23:
				double ctsTrabHogar = 0.0;
				if (empresa.getRegTrib().getIdRegTrib() == 3) {
					ctsTrabHogar = service.calculoCTSTrabHogar(planilladto);
				} else if (empresa.getRegTrib().getIdRegTrib() == 2) {
					ctsTrabHogar = service.calculoCTSTrabHogar(planilladto) / 2;
				}
				res_planilla_historico.setCts(ctsTrabHogar);
				break;
			case 24:
				double ctsPesquero = 0.0;
				if (empresa.getRegTrib().getIdRegTrib() == 3) {
					ctsPesquero = service.calculoCTSPesquero(planilladto, total_comp);
				} else if (empresa.getRegTrib().getIdRegTrib() == 2) {
					ctsPesquero = service.calculoCTSPesquero(planilladto, total_comp) / 2;
				}
				res_planilla_historico.setCts(ctsPesquero);
				break;
			default:
				double ctsDefault = 0.0;
				if (empresa.getRegTrib().getIdRegTrib() == 3) {
					ctsDefault = service.calculoCTSDefault(planilladto);
				} else if (empresa.getRegTrib().getIdRegTrib() == 2) {
					ctsDefault = service.calculoCTSDefault(planilladto) / 2;
				}
				res_planilla_historico.setCts(ctsDefault);
			}

			res_planilla_historico.setAfp(planilladto.getContrato().getTrabajador().getAfp());
			res_planilla_historico.setTotIngre(0.00);

			if (planilladto.getContrato().getTrabajador().getAfp() != null) {
				if (isAfp(planilladto)) {
					res_planilla_historico.setDsctOnp(0.00);// si es afp es cero
					res_planilla_historico.setDsctFondObl(
							total_comp * planilladto.getContrato().getTrabajador().getAfp().getApoOblFndPen());
					res_planilla_historico.setDsctPriSeg(
							total_comp * planilladto.getContrato().getTrabajador().getAfp().getPrimaSeguro());
					// solo se llama a su valor no se hace calculo
					if (planilladto.getContrato().getTrabajador().getComiMixta() == 0) {
						res_planilla_historico.setDsctComSobFLu(service.calculoDsctComSobFLu(planilladto, total_comp));
						res_planilla_historico.setDsctComMixSobFlu(0.00);
					} else if (planilladto.getContrato().getTrabajador().getComiMixta() == 1) {
						res_planilla_historico.setDsctComSobFLu(0.00);
						res_planilla_historico
								.setDsctComMixSobFlu(service.calculoDsctComSobFLuMix(planilladto, total_comp));
					}
					Double totAfp = res_planilla_historico.getDsctFondObl() + res_planilla_historico.getDsctComSobFLu()
							+ res_planilla_historico.getDsctComMixSobFlu() + res_planilla_historico.getDsctPriSeg();
					res_planilla_historico.setTotAporAfp(totAfp);

				} else {
					res_planilla_historico.setDsctOnp(total_comp * 0.13);
					res_planilla_historico.setDsctFondObl(0.0);
					res_planilla_historico.setDsctComSobFLu(0.00);
					res_planilla_historico.setDsctComMixSobFlu(0.00);
					res_planilla_historico.setDsctPriSeg(0.00);
					res_planilla_historico.setTotAporAfp(0.0);

				}
			} else {
				res_planilla_historico.setDsctFondObl(0.0);
				res_planilla_historico.setDsctComSobFLu(0.00);
				res_planilla_historico.setDsctComMixSobFlu(0.00);
				res_planilla_historico.setDsctOnp(0.00);
				res_planilla_historico.setDsctPriSeg(0.00);
				res_planilla_historico.setTotAporAfp(0.0);
			}

			res_planilla_historico.setDsctComMixAnualSal(0.00);// siempre cero
			res_planilla_historico.setDsct5taCat(
					service.calculo5taCateg(planilladto, planilladto.getPlanilla().getPdo_mes().getIdPdoMes()));
			res_planilla_historico.setMonFalt(planilladto.getPlanilla().getFaltantes());
			res_planilla_historico.setMonAdela(planilladto.getPlanilla().getAdelanto());
			res_planilla_historico.setMonPrest(planilladto.getPlanilla().getPrestamos());
			res_planilla_historico.setDsctFaltaInjusti(service.calculoMonDiasInjusti(planilladto));
			res_planilla_historico.setDsctTardanza(service.calculoMonTardanza(planilladto));
			Double[] aporEssaYEps = service.calculoAporEps(planilladto);
			res_planilla_historico.setAporEssalud(aporEssaYEps[1] * total_comp);
			res_planilla_historico.setEps(aporEssaYEps[0] * total_comp);
			if (planilladto.getContrato().getHorNoc() == 1) {
				res_planilla_historico.setRemHoraNoctur(service.calculoMonNocturno(planilladto));
			} else {
				res_planilla_historico.setRemHoraNoctur(0.0);
			}
			res_planilla_historico.setSctr(0.00);

			Double total_ing = res_planilla_historico.getRemJorNorm() + res_planilla_historico.getAsigFam()
					+ res_planilla_historico.getCts() + res_planilla_historico.getRemDiaFerdoLabo()
					+ res_planilla_historico.getRemGrati() + res_planilla_historico.getRemHoExt25()
					+ res_planilla_historico.getRemHoExt35() + res_planilla_historico.getRemVacaVend()
					+ res_planilla_historico.getMovilidad() + res_planilla_historico.getRemComisiones() + dsctRemu[1];

			Double total_desc = res_planilla_historico.getDsct5taCat() + res_planilla_historico.getDsctComMixAnualSal()
					+ res_planilla_historico.getDsctComMixSobFlu() + res_planilla_historico.getDsctComSobFLu()
					+ res_planilla_historico.getDsctFaltaInjusti() + res_planilla_historico.getDsctFondObl()
					+ res_planilla_historico.getDsctOnp() + res_planilla_historico.getDsctPriSeg()
					+ res_planilla_historico.getDsctTardanza() + res_planilla_historico.getMonAdela()
					+ res_planilla_historico.getMonFalt() + res_planilla_historico.getMonPrest()
					+ res_planilla_historico.getEssaludVida() + dsctRemu[0];

			Double total_apor = res_planilla_historico.getAporEssalud() + res_planilla_historico.getSctr();

			res_planilla_historico.setTotIngre(total_ing);
			res_planilla_historico.setTotDsct(total_desc);
			res_planilla_historico.setTotApor(total_apor);
			res_planilla_historico.setTotPagado(total_ing - total_desc);
			res_planilla_historico.setNetPagPdt(0.0);

			return res_planilla_historico;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + "crearPlanilla. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName(),
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					planilladto);
		}

	}

	private boolean isAfp(PlanillaDTO planilladto) {
		if (planilladto.getContrato().getTrabajador().getAfp().getDescripcion().compareTo(Constantes.CODONP) == 0) {
			return false;
		} else {
			return true;
		}
	}

	public double pagarDeuda(PlanillaDTO planillaDTO) {

		Trabajador trab = planillaDTO.getContrato().getTrabajador();
		PdoAno pdoAno = planillaDTO.getPdoAno();
		PdoMes pdoMes = planillaDTO.getPdoMes();

		double montoTotal = 0.0;

		List<CuotaAdelanto> lsCuoAde = service_ca.listarXTrabPAnoPMes(trab, pdoAno, pdoMes);

		if (lsCuoAde.size() > 0) {

			for (int i = 0; i < lsCuoAde.size(); i++) {

				int cantCuoAde = 0;
				int cantPagado = 0;

				CuotaAdelanto cuoAdelanto = service_ca.EncontrarCuoAde(lsCuoAde.get(i).getIdCuotaAdelanto());

				if (cuoAdelanto.getEstado() == 0) {
					montoTotal = montoTotal + cuoAdelanto.getMontoCuota();
					service_ca.Pagado(cuoAdelanto);

				}

				AdelantoSueldo valAdeSue = cuoAdelanto.getAdelantoSueldo();

				List<CuotaAdelanto> lsCuotaAdelanto = service_ca.listarXAdeSue(valAdeSue);
				cantCuoAde = lsCuotaAdelanto.size();
				for (int j = 0; j < lsCuotaAdelanto.size(); j++) {
					if (lsCuotaAdelanto.get(j).getEstado() == 1) {
						cantPagado = cantPagado + 1;
					}
				}
				if (cantCuoAde == cantPagado) {
					service_as.deudaSaldada(valAdeSue);
				}
			}
			return montoTotal;
		} else {
			return montoTotal;
		}
	}
}
