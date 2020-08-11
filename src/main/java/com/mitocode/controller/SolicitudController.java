package com.mitocode.controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.PuestoDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.SolicitudDTO;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Empresa;
import com.mitocode.model.ProyeccionPuesto;
import com.mitocode.model.Puesto;
import com.mitocode.model.Reclutamiento;
import com.mitocode.model.RegimenTributario;
import com.mitocode.model.Solicitud;
import com.mitocode.model.Trabajador;
import com.mitocode.service.ReclutamientoService;
import com.mitocode.service.SolicitudService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/solicitud")
public class SolicitudController {

	@Autowired
	SolicitudService solicitudService;
	
	@Autowired
	ReclutamientoService reclutamientoService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/registrar")
	public ResponseWrapper registrar(@Valid @RequestBody SolicitudDTO solicitudDTO, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), solicitudDTO);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			
			Date hoy = new Date();
			Timestamp fechahoy = new Timestamp(hoy.getTime());
			
			Solicitud solicitud =solicitudDTO.getSolicitud();
			solicitud.setTfechaInicio(fechahoy);
			solicitud.setPuesto(solicitudDTO.getPuesto());
			solicitud.setTrabajador(solicitudDTO.getTrabajador());
			solicitud.setIestado(Constantes.ConsActivo);
			Solicitud resp = solicitudService.registrar(solicitud);

			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgRegistrarSolicitudOK);
				response.setDefaultObj(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarSolicitudError);
			}

			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " registrarSolicitud. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					solicitudDTO);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarPorSupervisor")
	public ResponseWrapper listarSolicitudxSupervisor(@RequestBody Trabajador trabajador) throws Exception {
		if (trabajador.getIdTrabajador() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarSolicitudxSupervisor",
					Constantes.msgListarSolicitudxSupervisorError + " no se ha especificado un Trabajador valido",
					trabajador);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List resp = solicitudService.listarxTrabajador(trabajador);
			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarSolicitudxSupervisorOK);
				response.setAaData(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarSolicitudxSupervisorError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " listarSolicitudxSupervisor. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarSolicitudxSupervisor",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					trabajador);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@GetMapping("/listar")
	public ResponseWrapper listar() throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();
			List lsemp = solicitudService.listar();
			if (lsemp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarEmpresaOK);
				response.setAaData(lsemp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarsolicitud. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					null);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarPorEmpresa")
	public ResponseWrapper listarSolicitudxEmpresa(@RequestBody Empresa emp) throws Exception {
		
		try {
			ResponseWrapper response = new ResponseWrapper();
			List resp = solicitudService.listarxEmpresa(emp);
			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarSolicitudxEmpresaOK);
				response.setAaData(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarSolicitudxEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarSolicitudxEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarSolicitudxEmpresa",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					emp);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DeleteMapping("/{idSolicitud}")
	public ResponseWrapper eliminar(@PathVariable("idSolicitud") Integer id) throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();
			Boolean cat = solicitudService.eliminar(id);

			if (!cat) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEliminarSolicitudOK);
				response.setDefaultObj(cat);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgEliminarSolicitudError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " eliminarSolicitud. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/eliminarSolicitud" + id,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					id);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/cancelarSolicitud")
	public ResponseWrapper cancelarSolitud(@Valid @RequestBody Solicitud solicitud, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cancelarSolicitud",
					"Error en la validacion: Lista de Errores:" + errors.toString(), solicitud);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			solicitud.setIestado(Constantes.ConsSolicitudCancelada);
			solicitud.setTfechaInicio(solicitud.getTfechaInicio());
								
			Solicitud respSolicitud = solicitudService.modificar(solicitud);
			if (respSolicitud != null) {				
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgCancelarSolicitudOK);
					response.setDefaultObj(respSolicitud);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgCancelarSolicitudError);
				}
			
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " cancelarSolicitud. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cancelarSolicitud",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							solicitud);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/cambiarEstado")
	public ResponseWrapper cambiarEstado(@Valid @RequestBody Solicitud solicitud, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cambiarEstadoSolicitud",
					"Error en la validacion: Lista de Errores:" + errors.toString(), solicitud);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();	
			Reclutamiento reclu = new Reclutamiento();
				
			Solicitud respSolicitud = solicitudService.modificar(solicitud);	
			Integer estado = solicitud.getIestado();
			
			switch(estado) {			
				case -1:
					solicitud.setIestado(Constantes.ConsSolicitudRechazada);
					solicitud.setTfechaInicio(solicitud.getTfechaInicio());
					break;
				case 2:
					solicitud.setIestado(Constantes.ConsSolicitudRevision);
					solicitud.setTfechaInicio(solicitud.getTfechaInicio());
					break;
				case 3:
					solicitud.setIestado(Constantes.ConsSolicitudAprobada);
					solicitud.setTfechaInicio(solicitud.getTfechaInicio());
					reclu.setSolicitud(respSolicitud);
					Reclutamiento respRecl = reclutamientoService.registrar(reclu);
					solicitud.setReclutamiento(respRecl);
					break;
			}										
					
			if (respSolicitud != null) {			
					switch(estado) {				
					case -1:
						response.setEstado(Constantes.valTransaccionOk);
						response.setMsg(Constantes.msgSolicitudRechazadaOK);
						response.setDefaultObj(respSolicitud);
						break;
					case 2:
						response.setEstado(Constantes.valTransaccionOk);
						response.setMsg(Constantes.msgSolicitudRevisionOK);
						response.setDefaultObj(respSolicitud);
						break;
					case 3:
						response.setEstado(Constantes.valTransaccionOk);
						response.setMsg(Constantes.msgSolicitudAprobadaOK);
						response.setDefaultObj(respSolicitud);
						break;
					}
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgSolicitudEstadoError);
				}			
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " cambiarEstadoSolicitud. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cambiarEstadoSolicitud",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							solicitud);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/cambiarEstadoRevision")
	public ResponseWrapper revisionSolicitud(@Valid @RequestBody Solicitud solicitud, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cambiarEstadoSolicitudRevision",
					"Error en la validacion: Lista de Errores:" + errors.toString(), solicitud);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();			
			solicitud.setIestado(Constantes.ConsSolicitudRevision);
			solicitud.setTfechaInicio(solicitud.getTfechaInicio());
								
			Solicitud respSolicitud = solicitudService.modificar(solicitud);
			if (respSolicitud != null) {				
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgSolicitudRevisionOK);
					response.setDefaultObj(respSolicitud);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgSolicitudRevisionError);
				}
			
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " cancelarSolicitud. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cambiarEstadoSolicitudRevision",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							solicitud);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/cambiarEstadoAprobar")
	public ResponseWrapper aprobarSolicitud(@Valid @RequestBody Solicitud solicitud, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cambiarEstadoSolicitudAprobada",
					"Error en la validacion: Lista de Errores:" + errors.toString(), solicitud);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			Reclutamiento reclu = new Reclutamiento();
			solicitud.setIestado(Constantes.ConsSolicitudAprobada);
			solicitud.setTfechaInicio(solicitud.getTfechaInicio());
			
			Solicitud respSolicitud = solicitudService.modificar(solicitud);
						
			reclu.setSolicitud(respSolicitud);
			Reclutamiento respRecl = reclutamientoService.registrar(reclu);
			solicitud.setReclutamiento(respRecl);		
			Solicitud respSolicitud2 = solicitudService.modificar(respSolicitud);
			if (respSolicitud2 != null) {				
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgSolicitudAprobadaOK);
					response.setDefaultObj(respSolicitud2);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgSolicitudAprobadaError);
				}
			
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " aprobarSolicitud. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cambiarEstadoSolicitudAprobada",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							solicitud);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/cambiarEstadoRechazar")
	public ResponseWrapper rechazarSolicitud(@Valid @RequestBody Solicitud solicitud, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cambiarEstadoSolicitudRechazado",
					"Error en la validacion: Lista de Errores:" + errors.toString(), solicitud);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			solicitud.setIestado(Constantes.ConsSolicitudRechazada);
			solicitud.setTfechaInicio(solicitud.getTfechaInicio());
			
			Solicitud respSolicitud = solicitudService.modificar(solicitud);	
			if (respSolicitud != null) {				
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgSolicitudRechazadaOK);
					response.setDefaultObj(respSolicitud);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgSolicitudRechazadaError);
				}
			
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " rechazarSolicitud. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cambiarEstadoSolicitudRechazado",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							solicitud);
		}
	}

}
