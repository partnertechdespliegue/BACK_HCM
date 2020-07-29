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

import com.mitocode.dto.DepartamentoEmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.Empresa;
import com.mitocode.service.DepartamentoEmpresaService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/departamentoEmpresa")
public class DepartamentoEmpresaController {
	
	@Autowired
	private DepartamentoEmpresaService departamentoEmpresaService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/insertar")
	public ResponseWrapper guardarDepartamentoEmpresa(@RequestBody DepartamentoEmpresaDTO departamentoEmpresaDTO, BindingResult result) throws Exception{
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), departamentoEmpresaDTO);
		}
		
		try {
			
			ResponseWrapper response = new ResponseWrapper();
			DepartamentoEmpresa encDepaEmpr = departamentoEmpresaDTO.getDepartamentoEmpresa();
			encDepaEmpr.setEmpresa(departamentoEmpresaDTO.getEmpresa());
			encDepaEmpr.setGerente(departamentoEmpresaDTO.getTrabajador());
			encDepaEmpr.setIestado(Constantes.ConsActivo);
			
			DepartamentoEmpresa respDepEmpre = departamentoEmpresaService.registrar(encDepaEmpr);
		
			if (respDepEmpre!=null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgRegistrarDepartamentoXEmpresaOK);
				response.setDefaultObj(respDepEmpre);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarDepartamentoXEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " insertarDepartamentoEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/insertar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					departamentoEmpresaDTO);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/listarDepartamentoXEmpresa")
	public ResponseWrapper listarDepartamentoXEmpresa(@RequestBody Empresa emp) throws Exception {
		if (emp.getIdEmpresa() == null) {
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarDepartamentoXEmpresa",
					Constantes.msgListarDepartamentoXEmpresaError + " no se ha especificado una empresa valida", emp);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List resp = departamentoEmpresaService.listarDepartXEmpresa(emp);
			if (resp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarDepartamentoXEmpresaOk);
				response.setAaData(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarDepartamentoXEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(
					this.getClass().getSimpleName() + " listarDepartamentoXEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarDepartamentoXEmpresa",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							emp);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	@DeleteMapping("/{idDepartamentoEmpresa}")
	public ResponseWrapper eliminar (@PathVariable("idDepartamentoEmpresa") Integer id) throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();
			Boolean cat = departamentoEmpresaService.eliminar(id);
			
			if(!cat) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEliminarDepartamentoXEmpresaOK);
				response.setDefaultObj(cat);
			}else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgEliminarDepartamentoXEmpresaError);
			}
			return response;
		}catch(Exception e) {
			System.out.println(this.getClass().getSimpleName() + " eliminarDepartamentoXEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/eliminarDepartamentoXEmpresa"+id,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					id);
		}				
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	@PostMapping ("/actualizarDepartamentoXEmpresa")
	public ResponseWrapper actualizar(@Valid @RequestBody DepartamentoEmpresa departEmpresa, BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/actualizar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), departEmpresa);
		}
		
		try {
			ResponseWrapper response = new ResponseWrapper();
			DepartamentoEmpresa respDepEmp = departamentoEmpresaService.modificar(departEmpresa);
			if(respDepEmp!=null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgActualizarDepartamentoXEmpresaOK);
				response.setDefaultObj(respDepEmp);
			}else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgActualizarDepartamentoXEmpresaError);
			}
			return response;
		}catch(Exception e) {
			System.out.println(this.getClass().getSimpleName() + " actualizarDepartamentoXEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/actualizarDepartamentoXEmpresa",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
							departEmpresa);
		}				
	}
	
}
