package com.mitocode.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.internet.InternetAddress;

import org.apache.commons.codec.binary.Base64;

import com.ibm.icu.util.Calendar;
import com.mitocode.Thread.EnviarMailThread;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.TrabajadorDTO;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Contrato;
import com.mitocode.model.Empresa;
import com.mitocode.model.Perfil;
import com.mitocode.model.Trabajador;
import com.mitocode.model.Usuario;
import com.mitocode.service.ContratoService;
import com.mitocode.service.DepartamentoEmpresaService;
import com.mitocode.service.EnviarMailService;
import com.mitocode.service.IUsuarioService;
import com.mitocode.service.TrabajadorService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/trabajador")
public class TrabajadorController {

	@Autowired
	TrabajadorService serviceTrabajador;

	@Autowired
	DepartamentoEmpresaService departamentoEmpresaService;

	@Autowired
	ContratoService serviceContrato;

	@Autowired
	IUsuarioService serviceUsuario;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@Autowired
	EnviarMailService mailService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/registrar")
	public ResponseWrapper registrar(@Valid @RequestBody TrabajadorDTO trabajadorDTO, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), trabajadorDTO);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			Trabajador res_traba = new Trabajador();
			Trabajador tmp_traba = this.crearTrabajador(trabajadorDTO);
			Contrato tmp_contra = this.crearContrato(trabajadorDTO);

			res_traba = serviceTrabajador.registrar(tmp_traba);
			Contrato res_contra = new Contrato();
			tmp_contra.setTrabajador(res_traba);
			res_contra = serviceContrato.registrar(encriptarContrato(tmp_contra));
			if (res_contra != null) {
				if (registrarUsuario(res_traba)) {
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgRegistrarTrabajadorOK);
					response.setDefaultObj(res_traba);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgRegistrarUsuarioError);
				}
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarTrabajadorError);
			}
			return response;

		} catch (Exception e) {
			System.out.println(e);
			enviarMailError(e, trabajadorDTO.getTrabajador());
			System.out.println(this.getClass().getSimpleName() + " registrarTrabajador. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					trabajadorDTO);
		}
	}

	private void enviarMailError(Exception e, Trabajador trabajador) {
		String msgGeneral = e.getCause().getCause().getMessage().toString();
		if (msgGeneral.indexOf("Ya existe") >= 0) {
			String[] msgError = e.getCause().getCause().getMessage().toString().split("  ");
			String msg = msgError[1].replace("Detail:", "Detalle del problema:\n").replace(" Ya", "Ya")
					.replace("la llave", "un registro con el").replace("(", "").replace(")", "").replace("=", " ");
			EnviarMailThread thread = new EnviarMailThread(mailService, trabajador, msg, 0);
			thread.start();
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarGerencial")
	public ResponseWrapper listarGerencial(@RequestBody Empresa emp) throws Exception {
		if (emp.getIdEmpresa() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarGerencial",
					Constantes.msgListarGerencial + " no se ha especificado una empresa valida", emp);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			List resp = serviceTrabajador.listarGerencial(emp);
			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarTrabajadorOk);
				response.setAaData(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarTrabajadorError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarGerencial. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarGerencial",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					emp);
		}
	}

	public boolean registrarUsuario(Trabajador trabajador) throws Exception {

		Usuario usuario = new Usuario();
		Perfil perfil = new Perfil();
		perfil.setIdPerfil(3);

		usuario.setUsername(obtenerUsername(trabajador).toUpperCase());
		String encrip_pass = passEncoder.encode("12345");
		usuario.setPassword(encrip_pass);
		usuario.setEstado(true);
		usuario.setPerfil(perfil);
		if (trabajador.getCorreo() != null) {
			usuario.setEmail(trabajador.getCorreo());
		}
		usuario.setTrabajador(trabajador);
		usuario.setEmpresa(trabajador.getEmpresa());
		Usuario resp_user = serviceUsuario.registrar(usuario);
		if (resp_user != null) {
			EnviarMailThread thread = new EnviarMailThread(mailService, trabajador, usuario, 1);
			thread.start();
			return true;
		} else {
			return false;
		}
	}

	private String obtenerUsername(Trabajador trabajador) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(trabajador.getFecNac());
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH) + 1;
		String sdia = dia < 10 ? "0" + dia : "" + dia;
		String smes = mes < 10 ? "0" + mes : "" + mes;
		String username = (trabajador.getNombres().substring(0, 1) + trabajador.getApePater()
				+ trabajador.getApeMater().substring(0, 1) + sdia).toUpperCase();
		Usuario buscar_repetido = serviceUsuario.findbyUsername(username);
		if (buscar_repetido != null) {
			username = trabajador.getNombres().substring(0, 1) + trabajador.getApePater()
					+ trabajador.getApeMater().substring(0, 2) + sdia;
			Usuario buscar_repetido2 = serviceUsuario.findbyUsername(username);
			if (buscar_repetido2 != null) {
				username = trabajador.getNombres().substring(0, 2) + trabajador.getApePater()
						+ trabajador.getApeMater().substring(0, 2) + sdia;
				Usuario buscar_repetido3 = serviceUsuario.findbyUsername(username);
				if (buscar_repetido3 != null) {
					username = trabajador.getNombres().substring(0, 2) + trabajador.getApePater()
							+ trabajador.getApeMater().substring(0, 2) + sdia + smes;
					return username;
				} else {
					return username;
				}
			} else {
				return username;
			}
		} else {
			return username;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/modificar")
	public ResponseWrapper modificar(@Valid @RequestBody TrabajadorDTO trabajadorDTO, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), trabajadorDTO);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			Trabajador res_traba = new Trabajador();
			Trabajador tmp_trabajador = new Trabajador();
			tmp_trabajador = trabajadorDTO.getTrabajador();
			tmp_trabajador.setHorario(trabajadorDTO.getHorario());
			tmp_trabajador.setTipoDoc(trabajadorDTO.getTipoDoc());
			tmp_trabajador.setPais(trabajadorDTO.getPais());
			tmp_trabajador.setEstadoCivil(trabajadorDTO.getEstadoCivil());
			tmp_trabajador.setDepartamento(trabajadorDTO.getDepartamento());
			tmp_trabajador.setProvincia(trabajadorDTO.getProvincia());
			tmp_trabajador.setDistrito(trabajadorDTO.getDistrito());
			tmp_trabajador.setTipoZona(trabajadorDTO.getTipoZona());
			tmp_trabajador.setNivelEdu(trabajadorDTO.getNivelEdu());
			tmp_trabajador.setOcupacion(trabajadorDTO.getOcupacion());
			tmp_trabajador.setEmpresa(trabajadorDTO.getEmpresa());
			tmp_trabajador.setAfp(trabajadorDTO.getAfp());
			tmp_trabajador.setEps(trabajadorDTO.getEpsRegSalud());
			tmp_trabajador.setRegSalud(trabajadorDTO.getRegSalud());
			tmp_trabajador.setSituacion(trabajadorDTO.getSituacion());
			Contrato tmp_contra = this.obtenerContrato(trabajadorDTO);
			tmp_contra.setBancoCts(trabajadorDTO.getBancoCts());
			tmp_contra.setBancoSueldo(trabajadorDTO.getBancoSueldo());
			tmp_contra.setPuesto(trabajadorDTO.getPuesto());
			tmp_contra.setAreaDepEmp(trabajadorDTO.getAreaDepEmp());
			tmp_contra.setCentroCosto(trabajadorDTO.getCentroCosto());
			tmp_contra.setRegimenLaboral(trabajadorDTO.getRegimenLaboral());
			tmp_contra.setSctrPension(trabajadorDTO.getSctrPension());
			tmp_contra.setSctrSalud(trabajadorDTO.getSctrSalud());
			tmp_contra.setEpsSalud(trabajadorDTO.getEpsSalud());
			tmp_contra.setEpsPension(trabajadorDTO.getEpsPension());
			tmp_contra.setTipoContrato(trabajadorDTO.getTipoContrato());
			tmp_contra.setTipoPago(trabajadorDTO.getTipoPago());
			tmp_contra.setTrabajador(trabajadorDTO.getTrabajador());

			res_traba = serviceTrabajador.modificar(tmp_trabajador);
			if (res_traba != null) {
				Contrato res_contra = new Contrato();
				res_contra = serviceContrato.modificar(encriptarContrato(tmp_contra));
				if (res_contra != null) {
					Usuario usuario = serviceUsuario.findbyTrabajador(res_traba);
					if (!usuario.getEmail().equals(res_traba.getCorreo())) {
						Usuario usuarioAnt = new Usuario();
						usuarioAnt.setEmail(usuario.getEmail());
						usuario.setEmail(res_traba.getCorreo());
						if (serviceUsuario.modificar(usuario) != null) {
							EnviarMailThread thread = new EnviarMailThread(mailService, res_traba, usuarioAnt, 2);
							thread.start();
							response.setEstado(Constantes.valTransaccionOk);
							response.setMsg(Constantes.msgModificarTrabajadorOK);
							response.setDefaultObj(res_traba);
						} else {
							response.setEstado(Constantes.valTransaccionError);
							response.setMsg(Constantes.msgActualizarUsuarioError);
						}
					} else {
						response.setEstado(Constantes.valTransaccionOk);
						response.setMsg(Constantes.msgModificarTrabajadorOK);
						response.setDefaultObj(res_traba);
					}

				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgModificarTrabajadorError);
				}
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgModificarTrabajadorError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " modificarTrabajador. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					trabajadorDTO);
		}
	}
	

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/porEmpresa") // noseusa
	public ResponseWrapper listarPorEmpresa(@RequestBody Empresa empresa) throws Exception {

		if (empresa.getIdEmpresa() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/porEmpresa",
					Constantes.msgListarTrabajadorError + " no se ha especificado una empresa valida", empresa);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List lstra = serviceContrato.listarPorEmpresa(empresa);
			if (lstra != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarTrabajadorOk);
				response.setAaData(lstra);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarTrabajadorError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " listarTrabajadorPorEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/porEmpresa",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresa);
		}
	}

	// @Secured({ "ROLE_ADMIN" })
	@PostMapping("/porEmpresayTipoComprobante/{idComprobante}")
	public ResponseWrapper listarPorEmpresaYTipoComprobante(@RequestBody Empresa empresa,
			@PathVariable("idComprobante") Integer tipoComp) throws Exception {

		if (empresa.getIdEmpresa() == null) {
			throw new ExceptionResponse(new Date(),
					this.getClass().getSimpleName() + "/porEmpresayTipoComprobante/" + tipoComp,
					Constantes.msgListarTrabajadorError + " no se ha especificado una empresa valida", empresa);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List<Contrato> lstra = serviceContrato.listarPorEmpresaYTipoComp(empresa, tipoComp);

			if (lstra != null) {

				List<Contrato> lsDesencriptada = new ArrayList();
				for (Contrato contrato : lstra) {
					lsDesencriptada.add(desencriptarContrato(contrato));
				}

				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarTrabajadorOk);
				response.setAaData(lsDesencriptada);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarTrabajadorError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarTrabajadorPorEmpresayTipoComprobante. ERROR : "
					+ e.getMessage());
			throw new ExceptionResponse(new Date(),
					this.getClass().getSimpleName() + "/porEmpresayTipoComprobante/" + tipoComp,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresa);
		}
	}

	public Trabajador crearTrabajador(TrabajadorDTO trabajadorDTO) {
		try {
			Trabajador res_trabajador = new Trabajador();
			res_trabajador = trabajadorDTO.getTrabajador();
			res_trabajador.setTipoDoc(trabajadorDTO.getTipoDoc());
			res_trabajador.setPais(trabajadorDTO.getPais());
			res_trabajador.setHorario(trabajadorDTO.getHorario());
			res_trabajador.setEstadoCivil(trabajadorDTO.getEstadoCivil());
			res_trabajador.setDepartamento(trabajadorDTO.getDepartamento());
			res_trabajador.setProvincia(trabajadorDTO.getProvincia());
			res_trabajador.setDistrito(trabajadorDTO.getDistrito());
			res_trabajador.setTipoZona(trabajadorDTO.getTipoZona());
			res_trabajador.setNivelEdu(trabajadorDTO.getNivelEdu());
			res_trabajador.setOcupacion(trabajadorDTO.getOcupacion());
			res_trabajador.setEmpresa(trabajadorDTO.getEmpresa());
			res_trabajador.setAfp(trabajadorDTO.getAfp());
			res_trabajador.setEps(trabajadorDTO.getEpsRegSalud());
			res_trabajador.setRegSalud(trabajadorDTO.getRegSalud());
			res_trabajador.setSituacion(trabajadorDTO.getSituacion());
			return res_trabajador;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " crearTrabajador. ERROR : " + e.getMessage());
			throw e;
		}
	}

	public Contrato crearContrato(TrabajadorDTO trabajadorDTO) {
		try {
			Contrato res_contrato = new Contrato();
			res_contrato = trabajadorDTO.getContrato();
			res_contrato.setRegimenLaboral(trabajadorDTO.getRegimenLaboral());
			res_contrato.setTipoContrato(trabajadorDTO.getTipoContrato());
			res_contrato.setCentroCosto(trabajadorDTO.getCentroCosto());
			res_contrato.setTipoPago(trabajadorDTO.getTipoPago());
			res_contrato.setBancoSueldo(trabajadorDTO.getBancoSueldo());
			res_contrato.setBancoCts(trabajadorDTO.getBancoCts());
			res_contrato.setSctrPension(trabajadorDTO.getSctrPension());
			res_contrato.setSctrSalud(trabajadorDTO.getSctrSalud());
			res_contrato.setEpsSalud(trabajadorDTO.getEpsSalud());
			res_contrato.setEpsPension(trabajadorDTO.getEpsPension());
			res_contrato.setAreaDepEmp(trabajadorDTO.getAreaDepEmp());
			res_contrato.setPuesto(trabajadorDTO.getPuesto());
			return res_contrato;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " crearContrato. ERROR : " + e.getMessage());
			throw e;
		}
	}

	public Contrato obtenerContrato(TrabajadorDTO trabajadorDTO) {
		try {
			Contrato cont = new Contrato();
			cont = trabajadorDTO.getContrato();
			return cont;

		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " obtenerContrato. ERROR : " + e.getMessage());
			throw e;
		}
	}

	private Contrato desencriptarContrato(Contrato contrato) {
		String password = armarPassword(contrato.getTrabajador());
		try {
			if (contrato.getNroCta() != null)
				contrato.setNroCta(desencriptar(contrato.getNroCta(), password));
			if (contrato.getNroCci() != null)
				contrato.setNroCci(desencriptar(contrato.getNroCci(), password));
			if (contrato.getCuentaCTS() != null)
				contrato.setCuentaCTS(desencriptar(contrato.getCuentaCTS(), password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contrato;
	}

	private Contrato encriptarContrato(Contrato contrato) {
		String password = armarPassword(contrato.getTrabajador());
		if (contrato.getNroCta() != null)
			contrato.setNroCta(encriptar(contrato.getNroCta(), password));
		if (contrato.getNroCci() != null)
			contrato.setNroCci(encriptar(contrato.getNroCci(), password));
		if (contrato.getCuentaCTS() != null)
			contrato.setCuentaCTS(encriptar(contrato.getCuentaCTS(), password));
		return contrato;
	}

	private String armarPassword(Trabajador trabajador) {
		String nombre = trabajador.getNombres().substring(0, 3);
		String nroDocumento = trabajador.getNroDoc();
		String apellidoPa = trabajador.getApePater().substring(0, 2);
		String apellidoMa = trabajador.getApeMater().substring(0, 2);
		String password = nombre + nroDocumento + apellidoPa + apellidoMa;
		return password;
	}

	public static String encriptar(String texto, String secretKey) {

		String base64EncryptedString = "";

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception e) {
			System.out.println(e);
		}
		return base64EncryptedString;
	}

	public static String desencriptar(String textoEncriptado, String secretKey) throws Exception {

		String base64EncryptedString = "";

		try {
			byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");

			Cipher decipher = Cipher.getInstance("DESede");
			decipher.init(Cipher.DECRYPT_MODE, key);

			byte[] plainText = decipher.doFinal(message);

			base64EncryptedString = new String(plainText, "UTF-8");

		} catch (Exception e) {
			System.out.println(e);
		}
		return base64EncryptedString;
	}

	// *************************************************************************************************************************************************************

	/*
	 * @Autowired AfpService serviceAfp;
	 * 
	 * @Autowired ParametroService serviceParametro;
	 * 
	 * @Autowired VacacionesService serviceVacaciones;
	 * 
	 * @Autowired PlanillaHistoricoService service;
	 * 
	 * @Autowired ParametroService service_para;
	 * 
	 * @Autowired FaltasService service_falta;
	 * 
	 * @Autowired VacacionesTomadasService service_vactoma;
	 * 
	 * @Autowired VacacionesVendidasService service_vacven;
	 * 
	 * @Autowired AsistenciaService service_asistencia;
	 * 
	 * @Autowired CuotaAdelantoService service_ca;
	 * 
	 * @Autowired AdelantoSueldoService service_as;
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	 * Exception.class)
	 * 
	 * @PostMapping("/liquidar") public ResponseWrapper liquidar(@RequestBody
	 * ContratoDTO contratoDto, BindingResult result) throws Exception {
	 * 
	 * if (result.hasErrors()) { List<String> errors =
	 * result.getFieldErrors().stream().map(err -> { return err.getDefaultMessage();
	 * }).collect(Collectors.toList()); throw new ExceptionResponse(new Date(),
	 * this.getClass().getSimpleName() + "/liquidar",
	 * "Error en la validacion: Lista de Errores:" + errors.toString(),
	 * contratoDto); } try { ResponseWrapper response = new ResponseWrapper();
	 * 
	 * Double sueldoBase = contratoDto.getContrato().getSueldoBase();
	 * 
	 * double cts = calculoCts(contratoDto); double vacaTruncas =
	 * calculoVacaTrunca(contratoDto);
	 * 
	 * response.setEstado(Constantes.valTransaccionOk); return response; } catch
	 * (Exception e) { System.out.println(this.getClass().getSimpleName() +
	 * " liquidarTrabajador. ERROR : " + e.getMessage()); throw new
	 * ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/liquidar",
	 * e.getStackTrace()[0].getFileName() + " => " +
	 * e.getStackTrace()[0].getMethodName() + " => " + e.getClass() +
	 * " => message: " + e.getMessage() + "=> linea nro: " +
	 * e.getStackTrace()[0].getLineNumber(), contratoDto); } }
	 * 
	 * private double calculoVacaTrunca(ContratoDTO contratoDto) {
	 * 
	 * Contrato contrato = contratoDto.getContrato(); Trabajador trabajador =
	 * contrato.getTrabajador(); Afp afp = trabajador.getAfp(); // PdoMes mes =
	 * contratoDto.getPdoMes();
	 * 
	 * double sueldoBase = contrato.getSueldoBase(); Parametro interesCts =
	 * serviceParametro.buscarPorCodigoAndEmpresa(Constantes.CODPORCINCTS,
	 * trabajador.getEmpresa()); double inteCts =
	 * Double.parseDouble(interesCts.getValor());
	 * 
	 * Parametro diasComputables =
	 * serviceParametro.buscarPorCodigoAndEmpresa(Constantes.CODDIASCOMPTBASE,
	 * null); int diasComp = Integer.parseInt(diasComputables.getValor());
	 * 
	 * int cantidadDias = 0; List<Vacaciones> listVacacionesLibres =
	 * serviceVacaciones.buscarPorTrabyEstado(trabajador, 1); for (Vacaciones
	 * vacaciones : listVacacionesLibres) { cantidadDias += 15; }
	 * 
	 * List<Vacaciones> listVacacionesParcial =
	 * serviceVacaciones.buscarPorTrabyEstado(trabajador, 2); for (Vacaciones
	 * vacaciones : listVacacionesParcial) { cantidadDias += 15 -
	 * (vacaciones.getDiasTomados() + vacaciones.getDiasVendidos()); }
	 * 
	 * double totalVacaTrun = cantidadDias * (sueldoBase / diasComp);
	 * 
	 * double totalInteCts = inteCts * 1400; // "Total pagar CTS"
	 * 
	 * double totalAfpOnp = 0.0; if (afp.getIdAfp() == 1) { double porcentaje =
	 * afp.getApoOblFndPen(); totalAfpOnp = sueldoBase * porcentaje; } else { double
	 * porcentaje = afp.getApoOblFndPen() + afp.getComSobFlu() +
	 * afp.getPrimaSeguro(); totalAfpOnp = sueldoBase * porcentaje; }
	 * 
	 * return (totalVacaTrun - totalAfpOnp) + totalInteCts; }
	 * 
	 * private double calculoCts(ContratoDTO contratoDTO) throws Exception {
	 * 
	 * Empresa empresa = contratoDTO.getContrato().getTrabajador().getEmpresa();
	 * PlanillaDTO planilladto = new PlanillaDTO();
	 * planilladto.setContrato(contratoDTO.getContrato());
	 * planilladto.setPdoAno(contratoDTO.getPdoAno());
	 * planilladto.setPdoMes(contratoDTO.getPdoMes());
	 * planilladto.setTrabajador(contratoDTO.getContrato().getTrabajador());
	 * planilladto.setPlanilla(crearObjetoPlanilla(contratoDTO));
	 * 
	 * Double rem_comp_normal = service.calculoRemJorNorm(planilladto, true);
	 * 
	 * double ctsDefault = 0.0; if (empresa.getRegTrib().getIdRegTrib() == 3) {
	 * ctsDefault = service.calculoCTSDefault(planilladto); } else if
	 * (empresa.getRegTrib().getIdRegTrib() == 2) { ctsDefault =
	 * service.calculoCTSDefault(planilladto) / 2; } return ctsDefault;
	 * 
	 * }
	 * 
	 * private Planilla crearObjetoPlanilla(ContratoDTO contratoDTO) {
	 * 
	 * Planilla planilla = new Planilla(); Contrato contrato =
	 * contratoDTO.getContrato(); PdoAno pdoAno = contratoDTO.getPdoAno(); PdoMes
	 * pdoMes = contratoDTO.getPdoMes(); Empresa empresa =
	 * contrato.getTrabajador().getEmpresa(); Parametro tipoTardanza =
	 * service_para.buscarTardanzaPorEmpresa(empresa); Parametro tolerancia =
	 * obtenerTolerancia(empresa); int toleranciaMinutos =
	 * Integer.parseInt(tolerancia.getValor()); Parametro rango =
	 * service_para.buscarRangoPorEmpresa(empresa);
	 * 
	 * planilla.setPdo_ano(pdoAno); planilla.setPdo_mes(pdoMes);
	 * 
	 * List<Falta> lsfaltaInjustificadas =
	 * service_falta.buscarFaltas(Constantes.ConsInActivo, pdoAno, pdoMes,
	 * contrato.getTrabajador()); List<Falta> lsfaltaJustificadas =
	 * service_falta.buscarFaltas(Constantes.ConsInActivo, pdoAno, pdoMes,
	 * contrato.getTrabajador()); List<VacacionesTomadas> lsvacacionTomada =
	 * service_vactoma.encontrarVacacionTomada(contrato.getTrabajador(), pdoAno,
	 * pdoMes); int cantDiasVT = vacacionesTomadas(lsvacacionTomada, pdoAno,
	 * pdoMes); List<Asistencia> lsasistencia =
	 * service_asistencia.buscarPorTrabajador(contrato.getTrabajador(), pdoAno,
	 * pdoMes); int cantDiasAsistidos = diasComputables(lsasistencia, pdoMes); int
	 * diasFeriadoLaborados = feriadosLaborado(lsasistencia, pdoMes);
	 * List<VacacionesVendidas> lsvacacionVendidas =
	 * service_vacven.listarPorTrabPeriodo(contrato.getTrabajador(), pdoAno,
	 * pdoMes); int diasVacacionesVendidas =
	 * diasVacacionesVendidas(lsvacacionVendidas); Horario horario =
	 * contrato.getTrabajador().getHorario(); int tardCantDiasTotal = 0; int
	 * tardanzaMininutos = 0; if (tipoTardanza.getValor().equals("1")) {
	 * tardCantDiasTotal = obtenerTardanzaDias(lsasistencia, empresa, horario,
	 * toleranciaMinutos); } else { Parametro rangoMinuto = rango; tardanzaMininutos
	 * = obtenerTardanzaMinutos(lsasistencia, rangoMinuto, horario,
	 * toleranciaMinutos); } double[] lshorasExtras =
	 * obtenerHorasExtras(lsasistencia, horario); double deudaAdelantoSueldo =
	 * pagarDeuda(contratoDTO);
	 * 
	 * planilla.setFaltas_injusti(lsfaltaInjustificadas.size());
	 * planilla.setFaltas_justi(lsfaltaJustificadas.size());
	 * planilla.setDias_vacaciones(cantDiasVT);
	 * planilla.setDias_computables(cantDiasAsistidos);
	 * planilla.setFerdo_laborad(diasFeriadoLaborados);
	 * planilla.setVacaciones_vend(diasVacacionesVendidas);
	 * planilla.setHo_extra25(lshorasExtras[0]);
	 * planilla.setHo_extra35(lshorasExtras[1]);
	 * planilla.setTardanzas(tardCantDiasTotal); //
	 * planilla.setMinTardanzas(tardanzaMininutos);
	 * planilla.setAdelanto(deudaAdelantoSueldo);
	 * planilla.setDias_ferdo(pdoMes.getDiasFeriadoCalend());
	 * planilla.setFaltantes(0.0); planilla.setPrestamos(0.0);
	 * planilla.setComisiones(0.0);
	 * 
	 * return planilla; }
	 * 
	 * private Parametro obtenerTolerancia(Empresa empresa) { EmpresaDTO empresaDTO
	 * = new EmpresaDTO(); Parametro tolerancia = new Parametro();
	 * tolerancia.setCodigo("TIEMTOLE"); empresaDTO.setEmpresa(empresa);
	 * empresaDTO.setParametro(tolerancia); return
	 * service_para.buscarPorCodigoAndEmpresa(empresaDTO); }
	 * 
	 * private int vacacionesTomadas(List<VacacionesTomadas> lsvacacionTomada,
	 * PdoAno pdoAno, PdoMes pdoMes) { int cantDiasVT = 0; if
	 * (lsvacacionTomada.size() > 0) { for (VacacionesTomadas vacacionesTomadas :
	 * lsvacacionTomada) { if (vacacionesTomadas.getTipo() == 0) { cantDiasVT +=
	 * vacacionesTomadas.getPdoVacacion().getDiasTomados(); } else { if
	 * (vacacionesTomadas.getPdoMesIni().getIdPdoMes() == pdoMes.getIdPdoMes() &&
	 * vacacionesTomadas.getPdoAnoIni().getIdPdoAno() == pdoAno.getIdPdoAno()) {
	 * Calendar calendar = Calendar.getInstance();
	 * calendar.setTime(vacacionesTomadas.getFechaIni()); int diasInicio =
	 * calendar.get(Calendar.DAY_OF_MONTH); cantDiasVT += pdoMes.getCantidadDias() -
	 * diasInicio + 1; } else if (vacacionesTomadas.getPdoMesFin().getIdPdoMes() ==
	 * pdoMes.getIdPdoMes() && vacacionesTomadas.getPdoAnoFin().getIdPdoAno() ==
	 * pdoAno.getIdPdoAno()) { Calendar calendar = Calendar.getInstance();
	 * calendar.setTime(vacacionesTomadas.getFechaFin()); cantDiasVT +=
	 * calendar.get(Calendar.DAY_OF_MONTH); } } } } return cantDiasVT; }
	 * 
	 * private int diasComputables(List<Asistencia> lsasistencia, PdoMes pdoMes) {
	 * int cantDiasAsistidos = lsasistencia.size();
	 * 
	 * switch (pdoMes.getIdPdoMes()) { case 1: case 3: case 5: case 7: case 8: case
	 * 10: case 12: cantDiasAsistidos -= 1; break; case 2: if
	 * (pdoMes.getCantidadDias() == 29) { cantDiasAsistidos += 1; } else {
	 * cantDiasAsistidos += 2; } break; default: break; } if (cantDiasAsistidos < 0)
	 * { return 0; } else { return cantDiasAsistidos; } }
	 * 
	 * private int feriadosLaborado(List<Asistencia> lsasistencia, PdoMes pdoMes) {
	 * int diasFeriadoLaborados = 0; if (pdoMes.getTxtDiasFeriados() != null) {
	 * String[] feriados = pdoMes.getTxtDiasFeriados().split(","); for (int i = 0; i
	 * < feriados.length; i++) { int diaFeriado = Integer.parseInt(feriados[i]);
	 * Calendar calendar = Calendar.getInstance(); for (Asistencia asistencia :
	 * lsasistencia) { calendar.setTime(asistencia.getFecha()); int diaLaborado =
	 * calendar.get(Calendar.DAY_OF_MONTH); if (diaFeriado == diaLaborado) {
	 * diasFeriadoLaborados += 1; } } } } return diasFeriadoLaborados; }
	 * 
	 * private int diasVacacionesVendidas(List<VacacionesVendidas>
	 * lsvacacionVendidas) { int diasVacacionesVendidas = 0; if
	 * (lsvacacionVendidas.size() > 0) { for (VacacionesVendidas vacacionesVendidas
	 * : lsvacacionVendidas) { diasVacacionesVendidas +=
	 * vacacionesVendidas.getCantDias(); } } return diasVacacionesVendidas; }
	 * 
	 * private int obtenerTardanzaDias(List<Asistencia> lsasistencia, Empresa
	 * empresa, Horario horario, int toleranciaMinutos) { int tardCantDiasTotal = 0;
	 * Parametro tardanzaCantDias = obtenerTardanzaCantDias(empresa); int
	 * tardCantDias = Integer.parseInt(tardanzaCantDias.getValor());
	 * 
	 * int tardanzaDias = 0; Calendar asistenciaInicio = Calendar.getInstance();
	 * Calendar horarioInicio = Calendar.getInstance();
	 * horarioInicio.setTime(horario.getHorIniDia()); int horarioHora =
	 * horarioInicio.get(Calendar.HOUR_OF_DAY); int horarioMinuto =
	 * horarioInicio.get(Calendar.MINUTE);
	 * 
	 * for (Asistencia asistencia : lsasistencia) {
	 * asistenciaInicio.setTime(asistencia.getHorIniDia()); int asistHora =
	 * asistenciaInicio.get(Calendar.HOUR_OF_DAY); int asistMinuto =
	 * asistenciaInicio.get(Calendar.MINUTE); if (horarioHora == asistHora) { if
	 * (horarioMinuto < asistMinuto) { int tardanzaMin = asistMinuto -
	 * horarioMinuto; if (toleranciaMinutos < tardanzaMin) { tardanzaDias += 1; } }
	 * } else if (horarioHora < asistHora) { tardanzaDias += 1; } }
	 * 
	 * if (tardanzaDias >= tardCantDias) { tardCantDiasTotal = tardanzaDias /
	 * tardCantDias; }
	 * 
	 * return tardCantDiasTotal; }
	 * 
	 * private Parametro obtenerTardanzaCantDias(Empresa empresa) { EmpresaDTO
	 * empresaDTO = new EmpresaDTO(); Parametro cantidadDias = new Parametro();
	 * cantidadDias.setCodigo("TARCNTDIAS"); empresaDTO.setEmpresa(empresa);
	 * empresaDTO.setParametro(cantidadDias); return
	 * service_para.buscarPorCodigoAndEmpresa(empresaDTO); }
	 * 
	 * private int obtenerTardanzaMinutos(List<Asistencia> lsasistencia, Parametro
	 * rangoMinuto, Horario horario, int toleranciaMinutos) { int tardanzaMininutos
	 * = 0;
	 * 
	 * Calendar asistenciaInicio = Calendar.getInstance(); Calendar horarioInicio =
	 * Calendar.getInstance(); horarioInicio.setTime(horario.getHorIniDia()); int
	 * horarioHora = horarioInicio.get(Calendar.HOUR_OF_DAY); int horarioMinuto =
	 * horarioInicio.get(Calendar.MINUTE);
	 * 
	 * for (Asistencia asistencia : lsasistencia) {
	 * asistenciaInicio.setTime(asistencia.getHorIniDia()); int asistHora =
	 * asistenciaInicio.get(Calendar.HOUR_OF_DAY); int asistMinuto =
	 * asistenciaInicio.get(Calendar.MINUTE); if (horarioHora == asistHora) { if
	 * (horarioMinuto < asistMinuto) { int tardanzaMin = asistMinuto -
	 * horarioMinuto; if (toleranciaMinutos < tardanzaMin) { if
	 * (rangoMinuto.getValor().equals("1")) { tardanzaMininutos += (tardanzaMin -
	 * toleranciaMinutos); } else { int valorSinRedondear = tardanzaMin -
	 * toleranciaMinutos; tardanzaMininutos += ((valorSinRedondear + 9) / 10) * 10;
	 * } } } } else if (horarioHora < asistHora) { tardanzaMininutos += (asistHora -
	 * horarioHora) * 60; if (horarioMinuto < asistMinuto) { if
	 * (rangoMinuto.getValor().equals("1")) { tardanzaMininutos += (asistMinuto -
	 * horarioMinuto) - toleranciaMinutos; } else { int valorSinRedondear =
	 * (asistMinuto - horarioMinuto) - toleranciaMinutos; tardanzaMininutos +=
	 * ((valorSinRedondear + 9) / 10) * 10; } } } }
	 * 
	 * return tardanzaMininutos; }
	 * 
	 * private double[] obtenerHorasExtras(List<Asistencia> lsasistencia, Horario
	 * horario) {
	 * 
	 * double[] lshorasExtras = new double[2]; Calendar asistenciaFin =
	 * Calendar.getInstance(); Calendar horarioFin = Calendar.getInstance();
	 * horarioFin.setTime(horario.getHorFinDia()); int horarioHora =
	 * horarioFin.get(Calendar.HOUR_OF_DAY); int horarioMinuto =
	 * horarioFin.get(Calendar.MINUTE); int minutosExtras25 = 0; int minutosExtras35
	 * = 0; for (Asistencia asistencia : lsasistencia) {
	 * asistenciaFin.setTime(asistencia.getHorFinDia()); int asistenciaHora =
	 * asistenciaFin.get(Calendar.HOUR_OF_DAY); int asistenciaMinuto =
	 * asistenciaFin.get(Calendar.MINUTE); if (horarioHora == asistenciaHora) { //
	 * debe superar 5min if (asistenciaMinuto >= (horarioMinuto + 5)) {
	 * minutosExtras25 += asistenciaMinuto - horarioMinuto; } } else if (horarioHora
	 * < asistenciaHora) { int horaMinutosExtra = (asistenciaHora - horarioHora) *
	 * 60; if (horaMinutosExtra >= 120) { minutosExtras25 += 120; minutosExtras35 +=
	 * (horaMinutosExtra + asistenciaMinuto) - 120; } else { minutosExtras25 +=
	 * horaMinutosExtra + asistenciaMinuto; } } }
	 * 
	 * DecimalFormat df = new DecimalFormat("#.###"); lshorasExtras[0] =
	 * Double.parseDouble(df.format(minutosExtras25 / 60.0)); lshorasExtras[1] =
	 * Double.parseDouble(df.format(minutosExtras35 / 60.0));
	 * 
	 * return lshorasExtras; }
	 * 
	 * public double pagarDeuda(ContratoDTO contratoDTO) {
	 * 
	 * Trabajador trab = contratoDTO.getContrato().getTrabajador(); PdoAno pdoAno =
	 * contratoDTO.getPdoAno(); PdoMes pdoMes = contratoDTO.getPdoMes();
	 * 
	 * double montoTotal = 0.0;
	 * 
	 * List<CuotaAdelanto> lsCuoAde = service_ca.listarXTrabPAnoPMes(trab, pdoAno,
	 * pdoMes);
	 * 
	 * if (lsCuoAde.size() > 0) {
	 * 
	 * for (int i = 0; i < lsCuoAde.size(); i++) {
	 * 
	 * int cantCuoAde = 0; int cantPagado = 0;
	 * 
	 * CuotaAdelanto cuoAdelanto =
	 * service_ca.EncontrarCuoAde(lsCuoAde.get(i).getIdCuotaAdelanto());
	 * 
	 * if (cuoAdelanto.getEstado() == 0) { montoTotal = montoTotal +
	 * cuoAdelanto.getMontoCuota(); service_ca.Pagado(cuoAdelanto);
	 * 
	 * }
	 * 
	 * AdelantoSueldo valAdeSue = cuoAdelanto.getAdelantoSueldo();
	 * 
	 * List<CuotaAdelanto> lsCuotaAdelanto = service_ca.listarXAdeSue(valAdeSue);
	 * cantCuoAde = lsCuotaAdelanto.size(); for (int j = 0; j <
	 * lsCuotaAdelanto.size(); j++) { if (lsCuotaAdelanto.get(j).getEstado() == 1) {
	 * cantPagado = cantPagado + 1; } } if (cantCuoAde == cantPagado) {
	 * service_as.deudaSaldada(valAdeSue); } } return montoTotal; } else { return
	 * montoTotal; } }
	 */
}