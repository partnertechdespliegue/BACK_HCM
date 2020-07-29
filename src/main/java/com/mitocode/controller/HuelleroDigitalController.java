package com.mitocode.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Empresa;
import com.mitocode.model.HuelleroDigital;
import com.mitocode.service.HuelleroDigitalService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/huellaDigital")
public class HuelleroDigitalController {

	@Autowired
	HuelleroDigitalService service;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/registrar")
	public ResponseWrapper registrar(@RequestBody HuelleroDigital huelleroDigital, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), huelleroDigital);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			HuelleroDigital res = service.registrar(huelleroDigital);

			if (res != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgRegistrarHuelleroOK);
				response.setDefaultObj(res);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarHuelleroError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " registrarHuellaDigital. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					huelleroDigital);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/modificar")
	public ResponseWrapper modificar(@RequestBody HuelleroDigital huelleroDigital, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), huelleroDigital);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			HuelleroDigital encontrado = service.encontrar(huelleroDigital.getIidHuelleroDigital());
			if (encontrado != null) {
				encontrado.setSipPrivada(huelleroDigital.getSipPrivada());
				encontrado.setSipPublica(huelleroDigital.getSipPublica());
				HuelleroDigital res = service.modificar(encontrado);
				if (res != null) {
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgModificarHuelleroOK);
					response.setDefaultObj(res);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgModificarHuelleroError);
				}
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgModificarHuelleroError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " modificarHuellaDigital. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					huelleroDigital);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/asignarHuellero")
	public ResponseWrapper asignarHuellero(@RequestBody Empresa empresa, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/asociar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), empresa);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			// La ipPublica se trae en el atributo RUC
			List lshuellero = service.encontrarIpPublicaEmpresa(empresa.getRuc());
			if (lshuellero.size() > 0) {
				HuelleroDigital huelleroDigital = (HuelleroDigital) lshuellero.get(0);
				huelleroDigital.setEmpresa(empresa);
				HuelleroDigital resp = service.modificar(huelleroDigital);
				if (resp != null) {
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgAsignarHuelleroOK);
					response.setDefaultObj(resp);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgAsignarHuelleroError);
				}
			} else {
				response.setEstado(Constantes.valTransaccionNoEncontro);
				response.setMsg(Constantes.msgAsignarHuelleroVacio);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " asociarHuellero. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/asociar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresa);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/buscarHuellero")
	public ResponseWrapper buscarHuellero(@RequestBody Empresa empresa, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/buscar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), empresa);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			// La ipPublica se trae en el atributo RUC
			List resp = service.encontrarIpPublica(empresa);
			if (resp.size() > 0) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEncontrarHuelleroOK);
				response.setDefaultObj(resp.get(0));
			} else {
				response.setEstado(Constantes.valTransaccionNoEncontro);
				response.setMsg(Constantes.msgEncontrarHuelleroVacio);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " buscarHuellero. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/buscar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresa);
		}
	}

}
