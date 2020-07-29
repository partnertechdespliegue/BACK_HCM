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

import com.mitocode.dto.AreaDepartamentoEmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.Empresa;
import com.mitocode.service.AreaDepartamentoEmpresaService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/areaDepartamentoEmpresa")
public class AreaDepartamentoEmpresaController {
	
	@Autowired
	private AreaDepartamentoEmpresaService areaDepartamentoEmpresaService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/insertar")
	public ResponseWrapper guardarAreaDepartamentoEmpresa( @RequestBody AreaDepartamentoEmpresaDTO areaDepartamentoEmpresaDTO, BindingResult result) throws Exception{
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), areaDepartamentoEmpresaDTO);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			AreaDepartamentoEmpresa encAreaDepEmpr = areaDepartamentoEmpresaDTO.getAreaDepartamentoEmpresa();
			encAreaDepEmpr.setDepartamentoEmpresa(areaDepartamentoEmpresaDTO.getDepartamentoEmpresa());
			encAreaDepEmpr.setIestado(Constantes.ConsActivo);
			
			AreaDepartamentoEmpresa respAreaDepEmpre = areaDepartamentoEmpresaService.registrar(encAreaDepEmpr);
			if (respAreaDepEmpre!=null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgRegistrarAreaDepartamentoEmpresaOK);
				response.setDefaultObj(respAreaDepEmpre);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarAreaDepartamentoEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " registrarAreaDepartamentoEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
						areaDepartamentoEmpresaDTO);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarAreaDepartamentoEmpresa")
	public ResponseWrapper listarAreaDepartamentoEmpresa(@RequestBody Empresa depEmp) throws Exception {
		if (depEmp.getIdEmpresa() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarAreaDepartamentoEmpresa",
					Constantes.msgListarAreaDepartamentoEmpresaError + " no se ha especificado una Departamento Empresa valida", depEmp);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List resp = areaDepartamentoEmpresaService.listarPorEmpresa(depEmp);
			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarAreaDepartamentoEmpresaOk);
				response.setAaData(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarAreaDepartamentoEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " listarAreaDepartamentoEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarAreaDepartamentoEmpresa",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							depEmp);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	@DeleteMapping("/{idAreaDepartamentoEmpresa}")
	public ResponseWrapper eliminar (@PathVariable("idAreaDepartamentoEmpresa") Integer id) throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();
			Boolean cat = areaDepartamentoEmpresaService.eliminar(id);
			
			if(!cat) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEliminarAreaDepartamentoEmpresaOK);
				response.setDefaultObj(cat);
			}else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgEliminarAreaDepartamentoEmpresaError);
			}
			return response;
		}catch(Exception e) {
			System.out.println(this.getClass().getSimpleName() + " eliminarAreaDepartamentoEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/eliminarAreaDepartamentoEmpresa"+id,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					id);
		}				
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	@PostMapping ("/actualizarAreaDepartamentoEmpresa")
	public ResponseWrapper modificar(@Valid @RequestBody AreaDepartamentoEmpresa areaDepartamentoEmpresa, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/actualizar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), areaDepartamentoEmpresa);
		}
		
		try {
			ResponseWrapper response = new ResponseWrapper();
			AreaDepartamentoEmpresa respAreaDepEmp = areaDepartamentoEmpresaService.modificar(areaDepartamentoEmpresa);
			if(respAreaDepEmp!=null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgActualizarAreaDepartamentoEmpresaOK);
				response.setDefaultObj(respAreaDepEmp);
			}else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgActualizarAreaDepartamentoEmpresaError);
			}
			return response;
		}catch(Exception e) {
			System.out.println(this.getClass().getSimpleName() + " actualizarAreaDepartamentoEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/actualizarAreaDepartamentoEmpresa",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							areaDepartamentoEmpresa);
		}				
	}
	
}
