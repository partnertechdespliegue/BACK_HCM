package com.mitocode.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.UsuarioDTO;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Empresa;
import com.mitocode.model.Perfil;
import com.mitocode.model.RegimenTributario;
import com.mitocode.model.TipoEmpresa;
import com.mitocode.model.Usuario;
import com.mitocode.service.AnoMesService;
import com.mitocode.service.EmpresaService;
import com.mitocode.service.IUsuarioService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

	@Autowired
	private EmpresaService service_empresa;

	@Autowired
	private AnoMesService service_anomes;

	@Autowired
	IUsuarioService service;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	/*
	* Web Service para registrar una empresa dentro de la 
	* aplicación de PLANILLAS, con su regimen tributario.
	* 
	* Jesús Picardo
	* 
	* 
	* 
	* INPUT
	* ====================
	* Empresa empresa
	* 	String razonSocial
	*	String ruc
	*	String ubicacion
	* Regimen Tributario regTributario
	* 	Integer idRegTrib
	* 
	* OUTPUT
	* ====================
	* Integer estado
	* String msg
	* Empresa defaultObj
	* 	Integer idEmpresa
	* 	String razonSocial
	* 	String ruc
	* 	String ubicacion
	* 	Integer estado
	* 	String fechaRegistro
	* 	Integer sector
	* 	String urlImagen
	* 	String logo
	* 	Integer companyLimit
	* 	RegimenTributario regTrib
	* 		Integer idRegTrib
	* 		String descripcion
	* 	Integer limiteAutorizacion
	*
	*  
	*/
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/registrar")
	public ResponseWrapper registrar(@Valid @RequestBody EmpresaDTO empresaDTO, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), empresaDTO);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			Empresa tmp_emp = empresaDTO.getEmpresa();
			//TipoEmpresa tmp_tipoemp = empresaDTO.getTipoEmpresa();
			RegimenTributario tmp_regTrib = empresaDTO.getRegTributario();
			//tmp_emp.setTipoEmp(tmp_tipoemp);
			tmp_emp.setRegTrib(tmp_regTrib);
			Empresa emp = service_empresa.registrar(tmp_emp);
			Boolean reg_ano = service_anomes.registrarAnoDefecto(emp);
			if (emp != null && reg_ano) {
				boolean registro = registrarUsuario(emp);
				if (registro) {
					response.setEstado(Constantes.valTransaccionOk);
					response.setMsg(Constantes.msgRegistrarEmpresaOK);
					response.setDefaultObj(emp);
				} else {
					response.setEstado(Constantes.valTransaccionError);
					response.setMsg(Constantes.msgRegistrarUsuarioError);
				}
				
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " registrarEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresaDTO);
		}
	}

	public boolean registrarUsuario(Empresa empresa) throws Exception {

		Usuario usuario = new Usuario();
		Perfil perfil = new Perfil();
		perfil.setIdPerfil(2);

		usuario.setUsername(empresa.getRuc());
		usuario.setEmail(empresa.getRuc() + "@admin.com");
		String encrip_pass = passEncoder.encode("admin");
		usuario.setPassword(encrip_pass);
		usuario.setEstado(true);
		usuario.setPerfil(perfil);
		usuario.setEmpresa(empresa);
		Usuario buscar_repetido = service.findbyUsername(usuario.getUsername());
		if (buscar_repetido != null) {
			return false;
		} else {
			Usuario resp_user = service.registrar(usuario);
			if (resp_user != null) {
				return true;
			} else {
				return false;
			}
		}
	}

	// @Secured({ "ROLE_ADMIN" })
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@GetMapping("/listar")
	public ResponseWrapper listar() throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();
			List lsemp = service_empresa.listar();
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
			System.out.println(this.getClass().getSimpleName() + " listarEmpresas. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					null);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/modificar")
	public ResponseWrapper modificar(@Valid @RequestBody EmpresaDTO empresaDTO, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), empresaDTO);
		}
		try {
			ResponseWrapper response = new ResponseWrapper();
			Empresa empresa = empresaDTO.getEmpresa();
			empresa.setTipoEmp(empresaDTO.getTipoEmpresa());
			empresa.setRegTrib(empresaDTO.getRegTributario());
			Empresa emp = service_empresa.modificar(empresa);
			if (emp != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgModificarEmpresaOK);
				response.setDefaultObj(emp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgModificarEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " modificarEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresaDTO);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DeleteMapping("/{idEmpresa}")
	public ResponseWrapper eliminar(@PathVariable("idEmpresa") Integer id) throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();
			
			Empresa emp = service_empresa.leer(id);
			if (emp.getLogo() != null) {
				File file = new File("src/main/resources/Logo/"+ emp.getLogo());
				if (file.exists()) file.delete();
			}

			Boolean resp = service_empresa.eliminar(id);

			if (resp) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEliminarEmpresaOK);
				response.setDefaultObj(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgEliminarEmpresaError);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " eliminarEmpresa. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/" + id,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					id);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@PostMapping("/subirLogo")
	public ResponseEntity<?> subirLogo(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Integer id)
			throws Exception {

		try {
			Map<String, Object> response = new HashMap<>();
			Empresa empresa  = service_empresa.leer(id);
			
			if (!archivo.isEmpty()) {
				String nombreArchivo = UUID.randomUUID().toString()+"_"+ archivo.getOriginalFilename().replace(" ","");
				Path rutaArchivo = Paths.get("src/main/resources/Logo").resolve(nombreArchivo).toAbsolutePath();
				Files.copy(archivo.getInputStream(), rutaArchivo);
				
				String nombreFotoAnterior = empresa.getLogo();
				if(nombreFotoAnterior!=null && nombreFotoAnterior.length()>0){
					Path rutaFotoAnterior = Paths.get("src/main/resources/Logo").resolve(nombreFotoAnterior).toAbsolutePath();
					File archivoFotoAnterior = rutaFotoAnterior.toFile();
					if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
						archivoFotoAnterior.delete();
					}
				}
				empresa.setLogo(nombreArchivo);
				Empresa resp = service_empresa.modificar(empresa);
				response.put("empresa",resp);
			}else {
				response.put("empresa",empresa);
			}
			response.put("estado", Constantes.valTransaccionOk);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " listarUsuarios. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					null);
		}
	}
	
	@GetMapping("/uploadImage/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) throws Exception{
		Path rutaArchivo = Paths.get("src/main/resources/Logo").resolve(nombreFoto).toAbsolutePath();
		Resource recurso = null;
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
			if(!recurso.exists() && !recurso.isReadable()){
				throw new RuntimeException("Error no se pudo cargar la imagen "+nombreFoto);
			}
			
			HttpHeaders cabecera = new HttpHeaders();
			cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"");
			return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);

		} catch (MalformedURLException e) {
			System.out.println(this.getClass().getSimpleName() + " mostrarFoto. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/uploadImage/img/"+nombreFoto,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					nombreFoto);
		}
	}

}