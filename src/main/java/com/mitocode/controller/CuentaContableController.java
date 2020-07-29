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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.CuentaContable;
import com.mitocode.model.Empresa;
import com.mitocode.service.CuentaContableService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/cuentaContable")
public class CuentaContableController {
	@Autowired
	CuentaContableService service;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/insertar")
	public ResponseWrapper insertar(@Valid @RequestBody EmpresaDTO empresadto, BindingResult result) throws Exception{
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), empresadto);
		}
		
		try {
			ResponseWrapper response = new ResponseWrapper();
			CuentaContable cuentaContable =empresadto.getCuentaContable();
			cuentaContable.setEmpresa(empresadto.getEmpresa());
			
			CuentaContable cuentaContable_resp=service.registrar(cuentaContable);
			if (cuentaContable_resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgRegistrarCuentaContableOK);
				response.setDefaultObj(cuentaContable_resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarCuentaContableError);
			}return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " insertarCuentaContable. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresadto);
		}		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/actualizar")
	
	public ResponseWrapper actualizar(@Valid @RequestBody CuentaContable cuentaContable, BindingResult result) throws Exception{

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/actualizar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), cuentaContable);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			CuentaContable cuentaContable_resp= service.modificar(cuentaContable);
			if (cuentaContable_resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgModificarCuentaContableOK);
				response.setDefaultObj(cuentaContable_resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgModificarCuentaContableError);
			}return response;
			}catch (Exception e) {
				System.out.println(this.getClass().getSimpleName() + " modificarCuentaContable. ERROR : " + e.getMessage());
				throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
						e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
								+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
								+ e.getStackTrace()[0].getLineNumber(),
								cuentaContable);
		
		}

	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DeleteMapping("/{idCuentaContable}")
	public ResponseWrapper eliminar(@PathVariable ("idCuentaContable") Integer id) throws Exception{
		
		try {
			ResponseWrapper response = new ResponseWrapper();
			Boolean cuentaContable_resp = service.eliminar(id);
			if (cuentaContable_resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEliminarCuentaContableOK);
				response.setDefaultObj(cuentaContable_resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgEliminarCuentaContableError);
			}return response;
		}catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " eliminarCuentaContable. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/" + id,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							id);
		}
					
		
	} 
	
	@Transactional(propagation = Propagation.REQUIRED , rollbackFor= Exception.class)
	@PostMapping("/listar")
	public ResponseWrapper listar(@RequestBody  Empresa empresa) throws Exception {
	
		try {
			ResponseWrapper response = new ResponseWrapper();
			List lsres = service.listarxEmpresa(empresa);
			if(lsres.size()> 0) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarCuentaContableOK);
				response.setAaData(lsres);
				}else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgListarCuentaContableError);
				} return response;
			}catch (Exception e) {
				System.out.println(this.getClass().getSimpleName() + "listarporEmpresa. ERROR : " + e.getMessage());
				throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listar",
						e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
								+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
								+ e.getStackTrace()[0].getLineNumber(),
								empresa);
			}
		
		}
	
	
	}
