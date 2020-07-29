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
import com.mitocode.model.ConceptoPlanilla;
import com.mitocode.model.Empresa;
import com.mitocode.service.ConceptoPlanillaService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("api/conceptoPlanilla")
public class ConceptoPlanillaController {
	@Autowired
	ConceptoPlanillaService service;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/insertar")
	public ResponseWrapper insertar(@Valid @RequestBody EmpresaDTO empresadto, BindingResult result) throws Exception{
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), empresadto);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			ConceptoPlanilla conceptoPlanilla =empresadto.getConceptoPlanilla();
			conceptoPlanilla.setEmpresa(empresadto.getEmpresa());
			
			ConceptoPlanilla conceptoPlanilla_resp=service.registrar(conceptoPlanilla);
			if (conceptoPlanilla_resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgRegistrarConceptoPlanillaOK);
				response.setDefaultObj(conceptoPlanilla_resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarConceptoPlanillaError);
			}return response;
		
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " insertarConceptoPlanilla. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresadto);
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/actualizar")
	
	public ResponseWrapper actualizar(@Valid @RequestBody ConceptoPlanilla conceptoPlanilla, BindingResult result) throws Exception{

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/actualizar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), conceptoPlanilla);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			ConceptoPlanilla conceptoPlanilla_resp= service.modificar(conceptoPlanilla);
			if (conceptoPlanilla_resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgModificarConceptoPlanillaOK);
				response.setDefaultObj(conceptoPlanilla_resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgModificarConceptoPlanillaError);
			}return response;
			}catch (Exception e) {
				System.out.println(this.getClass().getSimpleName() + " modificarConceptoPlanilla. ERROR : " + e.getMessage());
				throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
						e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
								+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
								+ e.getStackTrace()[0].getLineNumber(),
								conceptoPlanilla);
		
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DeleteMapping("/{idConceptoPlanilla}")
	public ResponseWrapper eliminar(@PathVariable ("idConceptoPlanilla") Integer id) throws Exception{
	
		try {
			ResponseWrapper response = new ResponseWrapper();
			Boolean conceptoPlanilla_resp = service.eliminar(id);
			if (conceptoPlanilla_resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEliminarConceptoPlanillaOK);
				response.setDefaultObj(conceptoPlanilla_resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgEliminarConceptoPlanillaError);
			}return response;
		}catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " eliminaConceptoPlanilla. ERROR : " + e.getMessage());
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
				response.setMsg(Constantes.msgListarConceptoPlanillaOK);
				response.setAaData(lsres);
				}else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgListarConceptoPlanillaError);
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
