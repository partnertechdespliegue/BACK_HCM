package com.mitocode.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.dto.PuestoDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.Empresa;
import com.mitocode.model.ProyeccionPuesto;
import com.mitocode.model.Puesto;
import com.mitocode.service.ProyeccionPuestoService;
import com.mitocode.service.PuestoService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/puestoAreaEmpresa")
public class PuestoController {

	@Autowired
	private PuestoService puestoService;

	@Autowired
	private ProyeccionPuestoService proyService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/insertar")
	public ResponseWrapper insertarProyeccionPuesto(@RequestBody PuestoDTO puestoDTO, BindingResult result)
			throws Exception {
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), puestoDTO);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			Puesto encPuestoAreaEmpr = puestoDTO.getPuesto();
			Puesto puestoJefe = puestoDTO.getPuestoJefe();
			encPuestoAreaEmpr.setAreaDepartamentoEmpresa(puestoDTO.getAreaDepartamentoEmpresa());
			encPuestoAreaEmpr.setIestado(Constantes.ConsActivo);

			Puesto respPuestoAreaEmpre = puestoService.registrar(encPuestoAreaEmpr);

			if (respPuestoAreaEmpre != null) {
				ProyeccionPuesto respProy = proyService.crearProyeccion(respPuestoAreaEmpre, puestoJefe);
				if (respProy != null) {
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgRegistrarPuestoAreaEmpresaOK);
					response.setDefaultObj(respPuestoAreaEmpre);
				}

			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarPuestoAreaEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " registrarPuestoAreaEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					puestoDTO);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/actualizarPuestoAreaEmpresa")
	public ResponseWrapper modificarProyeccionPuesto(@Valid @RequestBody PuestoDTO puestoDTO, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/actualizarPuestoAreaEmpresa",
					"Error en la validacion: Lista de Errores:" + errors.toString(), puestoDTO);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			Puesto puesto = puestoDTO.getPuesto();
			puesto.setAreaDepartamentoEmpresa(puestoDTO.getAreaDepartamentoEmpresa());
			
			Puesto respPuestoAreaEmp = puestoService.modificar(puesto);
			if (respPuestoAreaEmp != null) {
				ProyeccionPuesto proy = puestoDTO.getProyeccion();
				proy.setPuesto(respPuestoAreaEmp);
				proy.setPuestoProyeccion(puestoDTO.getPuestoJefe());
				ProyeccionPuesto respProy = proyService.actualizarProyeccion(proy);
				if (respProy != null) {
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgActualizarAreaDepartamentoEmpresaOK);
					response.setDefaultObj(respPuestoAreaEmp);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgActualizarAreaDepartamentoEmpresaError);
				}
			}
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " actualizarAreaDepartamentoEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/actualizarPuestoAreaEmpresa",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					puestoDTO);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarPuesto")
	public ResponseWrapper listarPuestoAreaEmpresa(@RequestBody Empresa empr) throws Exception {
		if (empr.getIdEmpresa() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarPuesto",
					Constantes.msgListarPuestoAreaEmpresaError + " no se ha especificado una Area Empresa valida",
					empr);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List resp = puestoService.listarPorEmpresa(empr);
			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarPuestoAreaEmpresaOk);
				response.setAaData(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarPuestoAreaEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarPuestoAreaEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarPuesto",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empr);
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarPuestoPorOrden")
	public ResponseWrapper listarPuestoPorOrden(@RequestBody ProyeccionPuesto proyeccionPuesto) throws Exception {
		if (proyeccionPuesto.getIidProyeccion() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarPuestoPorOrden",
					Constantes.msgListarPuestoAreaEmpresaError + " no se ha especificado una Area Empresa valida",
					proyeccionPuesto);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List resp = puestoService.listarPuestoXOrden(proyeccionPuesto.getPuesto().getAreaDepartamentoEmpresa().getIidAreaDepartamentoEmpresa(), proyeccionPuesto.getIorden());
			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarPuestoAreaEmpresaOk);
				response.setAaData(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarPuestoAreaEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarPuestoPorOrden. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarPuestoPorOrden",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							proyeccionPuesto);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DeleteMapping("/{idPuestoAreaEmpresa}")
	public ResponseWrapper eliminar(@PathVariable("idPuestoAreaEmpresa") Integer id) throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();
			ProyeccionPuesto proyeccionPuesto = proyService.buscarPorId(id);
			proyeccionPuesto.setPuestoProyeccion(null);
			proyService.modificar(proyeccionPuesto);

			Boolean cat = puestoService.eliminar(proyeccionPuesto.getPuesto().getIidPuesto());

			if (!cat) {

				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEliminarPuestoAreaEmpresaOK);
				response.setDefaultObj(cat);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgEliminarPuestoAreaEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out
					.println(this.getClass().getSimpleName() + " eliminarPuestoAreaEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/eliminarPuestoAreaEmpresa" + id,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					id);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarProyeccionPuesto")
	public ResponseWrapper listarProyeccionPuesto(@RequestBody Empresa empr) throws Exception {
		if (empr.getIdEmpresa() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarProyPuesto",
					Constantes.msgListarProyPuestoEmpresaError + " no se ha especificado una Empresa valida", empr);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List resp = puestoService.listarProyeccionPuesto(empr);
			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarProyPuestoEmpresaOk);
				response.setAaData(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarProyPuestoEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarProyPuestoEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarProyPuesto",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empr);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarPuestoXArea")
	public ResponseWrapper listarPuestoXArea(@RequestBody AreaDepartamentoEmpresa area) throws Exception {
		if (area.getIidAreaDepartamentoEmpresa() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarPuestoXArea",
					Constantes.msgListarPuestoAreaEmpresaError + " no se ha especificado una Area Empresa valida",
					area);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List resp = puestoService.listarPuesto(area);
			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarPuestoAreaEmpresaOk);
				response.setAaData(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarPuestoAreaEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarPuestoXArea. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarPuestoXArea",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					area);
		}
	}


	/*
	 * @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	 * Exception.class)
	 * 
	 * @PostMapping("/porOrden") public ResponseWrapper listarPorArea(@RequestBody
	 * AreaDepartamentoEmpresa area) throws Exception { if
	 * (area.getIid_areaDepartamentoEmpresa() == null) { throw new
	 * ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/porOrden",
	 * Constantes.msgListarPuestoAreaEmpresaError +
	 * " no se ha especificado una Area Empresa valida", area); }
	 * 
	 * try { ResponseWrapper response = new ResponseWrapper(); List resp =
	 * puestoService.listarPuesto(area); if (resp != null) {
	 * response.setEstado(Constantes.valTransaccionOk);
	 * response.setMsg(Constantes.msgListarPuestoAreaEmpresaOk);
	 * response.setAaData(resp); } else {
	 * response.setEstado(Constantes.valTransaccionError);
	 * response.setMsg(Constantes.msgListarPuestoAreaEmpresaError); } return
	 * response; } catch (Exception e) { System.out.println(
	 * this.getClass().getSimpleName() + " porOrden. ERROR : " + e.getMessage());
	 * throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() +
	 * "/porOrden", e.getStackTrace()[0].getFileName() + " => " +
	 * e.getStackTrace()[0].getMethodName() + " => " + e.getClass() +
	 * " => message: " + e.getMessage() + "=> linea nro: " +
	 * e.getStackTrace()[0].getLineNumber(), area); } }
	 */

}
