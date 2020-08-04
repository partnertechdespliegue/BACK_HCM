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
import com.mitocode.model.RegimenTributario;
import com.mitocode.model.Solicitud;
import com.mitocode.model.Trabajador;
import com.mitocode.service.SolicitudService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/solicitud")
public class SolicitudController {

	@Autowired
	SolicitudService solicitudService;

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
	public ResponseWrapper modificarProyeccionPuesto(@Valid @RequestBody Solicitud solicitud, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cancelarSolicitud",
					"Error en la validacion: Lista de Errores:" + errors.toString(), solicitud);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();			
			//Solicitud sol = solicitudDTO.getSolicitud();
			//sol.setTrabajador(solicitudDTO.getTrabajador());
			//sol.setPuesto(solicitudDTO.getPuesto());
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

}
