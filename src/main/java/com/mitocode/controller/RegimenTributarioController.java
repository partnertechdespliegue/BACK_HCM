package com.mitocode.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.service.RegimenTributarioService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/regimenTributario")
public class RegimenTributarioController {
	
	@Autowired
	RegimenTributarioService service;
	
	/*
	* Web Service para listar los regimenes tributarios 
	* registrados en la aplicación de PLANILLAS
	* 
	* Jesús Picardo
	* 
	* 
	* 
	* INPUT
	* ====================
	* NINGUNO
	* 
	* OUTPUT
	* ====================
	* Integer estado
    * String msg
    * List<RegimenTributario> aaData
    * 	Integer idRegTrib
    * 	String descripcion
	*
	* 
	*/
	@GetMapping("/listar")
	public ResponseWrapper listar() throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();
			List lsRegTrib = service.listar();
			if (lsRegTrib != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarRegimenTributarioOK);
				response.setAaData(lsRegTrib);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarRegimenTributarioError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarRegimenTributario. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					null);
		}
	}
}
