package com.mitocode.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.dto.PostulanteDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.TrabajadorDTO;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Contrato;
import com.mitocode.model.Empresa;
import com.mitocode.model.NivelEdu;
import com.mitocode.model.Ocupacion;
import com.mitocode.model.Postulante;
import com.mitocode.model.TipoDoc;
import com.mitocode.model.Trabajador;
import com.mitocode.service.ContratoService;
import com.mitocode.service.NivelEduService;
import com.mitocode.service.OcupacionService;
import com.mitocode.service.PostulanteService;
import com.mitocode.service.TipoDocService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/postulante")
public class PostulanteController {
	
	@Autowired
	ContratoService serviceContrato;
	
	@Autowired
	PostulanteService service_po;

	@Autowired
	TipoDocService service_td;
	
	@Autowired
	OcupacionService service_oc;
	
	@Autowired
	NivelEduService service_ne;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/registrar")
	public ResponseWrapper registrar(@Valid @RequestBody PostulanteDTO postulanteDTO, BindingResult result) throws Exception {

		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream().map(err -> {
						return err.getDefaultMessage();
					})
					.collect(Collectors.toList());
			throw new ExceptionResponse(new Date(),this.getClass().getSimpleName() + "/registrar","Error en la validacion: Lista de Errores:"+errors.toString(),postulanteDTO);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			Postulante nuevo_postulante = postulanteDTO.getPostulante();
			nuevo_postulante.setIdTipoDoc(postulanteDTO.getTipoDoc());
			nuevo_postulante.setIdNivelEdu(postulanteDTO.getNivelEdu());
			nuevo_postulante.setIdOcupacion(postulanteDTO.getOcupacion());
			nuevo_postulante.setEstado(Constantes.ConstaEstadoPostulante);
			
			nuevo_postulante = service_po.registrar(nuevo_postulante);
			if(nuevo_postulante!=null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgRegistrarPostulanteOK);
				response.setDefaultObj(nuevo_postulante);
			}else {			
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarPostulanteError);
			}
			return response;
		}catch(Exception e) {
			System.out.println(this.getClass().getSimpleName()+" registrarPostulante. ERROR : "+e.getMessage());
			throw new ExceptionResponse(new Date(),	this.getClass().getSimpleName() + "/registrar",
	 				e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => " + e.getClass() + " => message: " + 
	 				e.getMessage() + "=> linea nro: " + e.getStackTrace()[0].getLineNumber(),postulanteDTO);
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
	

	
}
