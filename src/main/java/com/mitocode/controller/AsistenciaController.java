package com.mitocode.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.icu.text.SimpleDateFormat;
import com.mitocode.dto.PeriodoDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Asistencia;
import com.mitocode.model.Empresa;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Trabajador;
import com.mitocode.service.AnoMesService;
import com.mitocode.service.AsistenciaService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

	@Autowired
	AsistenciaService service;

	@Autowired
	AnoMesService serviceAnoMes;

	@PostMapping("/listarXTrabajadorAnoMes")
	public ResponseWrapper listarXTrabajadorXAnoXMes(@Valid @RequestBody Asistencia asistencia, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();

			}).collect(Collectors.toList());
			errors.remove(null);
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarXTrabajadorAnoMes",
					"Error en la validacion: Lista de Errores:" + errors.toString(), asistencia);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			List lsasist = service.buscarPorTrabajador(asistencia.getTrabajador(), asistencia.getPdoAno(),
					asistencia.getPdoMes());
			if (lsasist != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgListarAsistenciaOK);
				response.setAaData(lsasist);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgListarAsistenciaError);
			}

			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + "listarAsistenciasPorTrabajadorAnoMes. ERROR : "
					+ e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/listarXTrabajadorAnoMes",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					asistencia);
		}
	}

	@PostMapping("/buscarFecha")
	public ResponseWrapper buscarFecha(@Valid @RequestBody Asistencia asistencia, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();

			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/buscarFecha",
					"Error en la validacion: Lista de Errores:" + errors.toString(), asistencia);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			Boolean resp = service.buscarPorFechaYTrabajador(asistencia.getFecha(), asistencia.getTrabajador());
			response.setDefaultObj(resp);
			return response;

		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + "buscarFecha. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/buscarFecha",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					asistencia);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/registrar")
	public ResponseWrapper registrar(@RequestBody Asistencia asistencia, BindingResult result) throws Exception {
		ResponseWrapper response = new ResponseWrapper();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), asistencia);
		}
		try {
			Asistencia res_asistencia = service.registrar(asistencia);
			if (res_asistencia != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgRegistrarAsistenciaOK);
				response.setDefaultObj(res_asistencia);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgRegistrarAsistenciaError);
			}
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " registrarAsistencia. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/registrar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					asistencia);
		}

		return response;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PutMapping("/modificar")
	public ResponseWrapper modificar(@Valid @RequestBody Asistencia asistencia, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), asistencia);
		}

		try {
			ResponseWrapper response = new ResponseWrapper();
			Asistencia resp_asist = service.modificar(asistencia);
			if (resp_asist != null) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgActualizarAsistenciaOK);
				response.setDefaultObj(resp_asist);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgActualizarAsistenciaError);
			}

			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " modificarAsistencia. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/modificar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					asistencia);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@DeleteMapping("/{idAsistencia}")
	public ResponseWrapper eliminar(@PathVariable("idAsistencia") Integer id) throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();

			Boolean resp = service.eliminar(id);

			if (resp) {
				response.setEstado(Constantes.valTransaccionOk);
				response.setMsg(Constantes.msgEliminarAsistenciaOK);
				response.setDefaultObj(resp);
			} else {
				response.setEstado(Constantes.valTransaccionError);
				response.setMsg(Constantes.msgEliminarAsistenciaError);
				response.setDefaultObj(resp);
			}
			return response;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " eliminarAsistencia. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/" + id,
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					id);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/cargarAsistencia/{idEmpresa}")
	public ResponseWrapper cargarAsistencia(@RequestParam("file") MultipartFile file,
			@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
		try {
			ResponseWrapper response = new ResponseWrapper();

			FileInputStream input = (FileInputStream) file.getInputStream();
			HSSFWorkbook libro = new HSSFWorkbook(input);

			HSSFSheet reporteAsistencia = libro.getSheetAt(2);

			Row fila = reporteAsistencia.getRow(0);
			int cantFilas = reporteAsistencia.getLastRowNum();
			int cantCeldas = fila.getLastCellNum();

			Row ffecha = reporteAsistencia.getRow(2);
			PeriodoDTO periodoDTO = retornarPeriodo(ffecha.getCell(2).getStringCellValue().substring(0, 10), idEmpresa);

			for (int i = 4; i < cantFilas; i++) {
				Row filaId = reporteAsistencia.getRow(i++);
				Trabajador trabajador = new Trabajador();
				trabajador.setIdTrabajador(Integer.parseInt(filaId.getCell(2).getStringCellValue()));

				Row filaDia = reporteAsistencia.getRow(i);
				for (int j = 0; j < cantCeldas; j++) {

					String valor = filaDia.getCell(j).getStringCellValue();
					if (!valor.isEmpty()) {
						int dia = j + 1;
						String fecha = ffecha.getCell(2).getStringCellValue().substring(0, 8)
								+ (dia < 10 ? "0" + dia : "" + dia);
						Date dateAsistencia = obtenerDate(fecha);

						boolean existe = service.buscarPorFechaYTrabajador(dateAsistencia, trabajador);

						if (!existe) {
							String tiempoInicio = valor.substring(0, 5);
							Timestamp timeInicio = obtenerTimestamp(fecha + " " + tiempoInicio);
							Timestamp timeFin = null;
							if (valor.length() > 6) {
								String tiempoFin = valor.substring(5, 10);
								timeFin = obtenerTimestamp(fecha + " " + tiempoFin);
							}
							Asistencia asistencia = new Asistencia();
							asistencia.setFecha(dateAsistencia);
							asistencia.setHorIniDia(timeInicio);
							asistencia.setHorFinDia(timeFin);
							asistencia.setTrabajador(trabajador);
							asistencia.setPdoAno(periodoDTO.getPdoAno());
							asistencia.setPdoMes(periodoDTO.getPdoMes());
							service.registrar(asistencia);
						}
					}
				}

			}
			response.setMsg(Constantes.msgRegistrarAsistenciaOK);
			response.setEstado(Constantes.valTransaccionOk);
			return response;

		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + "cargarAsistencia. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/cargarAsistencia/",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					file);
		}
	}

	private PeriodoDTO retornarPeriodo(String fecha, int idEmpresa) {
		PeriodoDTO periodoDTO = new PeriodoDTO();
		Empresa empresa = new Empresa();
		empresa.setIdEmpresa(idEmpresa);
		PdoAno ano = new PdoAno();
		ano.setDescripcion(Integer.parseInt(fecha.substring(0, 4)));
		periodoDTO.setPdoAno(serviceAnoMes.buscarSiExistePorDesc(empresa, ano));
		periodoDTO.setPdoMes(serviceAnoMes.encontrarMes(Integer.parseInt(fecha.substring(5, 7))));
		return periodoDTO;
	}

	public static Date obtenerDate(String fecha) {
		Date fechaDate = null;
		try {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			fechaDate = formato.parse(fecha);
		} catch (ParseException ex) {
			System.out.println(ex);
		}
		return fechaDate;
	}

	public static Timestamp obtenerTimestamp(String fecha) {
		Timestamp timestamp = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date parsedDate = dateFormat.parse(fecha);
			timestamp = new Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			System.out.println(e);
		}
		return timestamp;
	}
}
