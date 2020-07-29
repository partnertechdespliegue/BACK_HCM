package com.mitocode.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.dto.EmpresaDTO;
import com.mitocode.dto.PlanillaDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.ConceptoPlanilla;
import com.mitocode.model.CuentaContable;
import com.mitocode.model.Empresa;
import com.mitocode.model.Horario;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.PlanillaHistorico;
import com.mitocode.model.TipoPlanilla;
import com.mitocode.model.TipoPlanillaDetalle;
import com.mitocode.model.Trabajador;
import com.mitocode.service.ConceptoPlanillaService;
import com.mitocode.service.CuentaContableService;
import com.mitocode.service.HorarioService;
import com.mitocode.service.PlanillaHistoricoService;
import com.mitocode.service.TipoPlanillaDetalleService;
import com.mitocode.service.TrabajadorService;
import com.mitocode.util.Constantes;

@RestController
@RequestMapping("/api/generarExcel")
public class GenerarExcelController {

	@Autowired
	HorarioService serviceHorario;

	@Autowired
	TrabajadorService serviceTrabajador;

	@Autowired
	TipoPlanillaDetalleService service_tpd;

	@Autowired
	CuentaContableService serviceCtaContable;
	
	@Autowired
	ConceptoPlanillaService serviceConcpPlanilla;
	
	@Autowired	
	PlanillaHistoricoService servicePlanilla;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/generar")
	public ResponseWrapper generarExcel(@RequestBody EmpresaDTO empresaDTO, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/generar",
					"Error en la validacion: Lista de Errores:" + errors.toString(), empresaDTO);
		}
		try {
			ResponseWrapper resp = new ResponseWrapper();
			crearExcel(empresaDTO);
			resp.setEstado(Constantes.valTransaccionOk);
			return resp;
		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " generar. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/generar",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresaDTO);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@PostMapping("/generarReporteCtaCont")
	public ResponseWrapper generarReporteCtaContable(@RequestBody EmpresaDTO empresaDTO, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err -> {
				return err.getDefaultMessage();
			}).collect(Collectors.toList());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/generarReporteCtaCont",
					"Error en la validacion: Lista de Errores:" + errors.toString(), empresaDTO);
		}
		try {
			ResponseWrapper respReporte = new ResponseWrapper();
			crearReporteCta(empresaDTO);
			respReporte.setEstado(Constantes.valTransaccionOk);
			return respReporte;

		} catch (Exception e) {
			System.out.println(this.getClass().getSimpleName() + " generar. ERROR : " + e.getMessage());
			throw new ExceptionResponse(new Date(), this.getClass().getSimpleName() + "/generarReporteCtaCont",
					e.getStackTrace()[0].getFileName() + " => " + e.getStackTrace()[0].getMethodName() + " => "
							+ e.getClass() + " => message: " + e.getMessage() + "=> linea nro: "
							+ e.getStackTrace()[0].getLineNumber(),
					empresaDTO);
		}

	}

	private void crearReporteCta(EmpresaDTO empresaDTO) throws Exception {
		Empresa empresa = empresaDTO.getEmpresa();
			
		List<CuentaContable> lsCtaContable = new ArrayList<>();
		lsCtaContable=	serviceCtaContable.listarxEmpresa(empresa);
		
		FileInputStream input = new FileInputStream("src/main/resources/Excel/ReporteCtaContable.xls");
		HSSFWorkbook libro = new HSSFWorkbook(input);
		File file = new File("src/main/resources/Excel/ReporteCtaCont.xls");
		if (file.exists()) {
			file.delete();
		}

		HSSFSheet reporte = libro.getSheetAt(0);

		//Row filaCta = reporte.getRow(2);		
		
		int inicioRow = 2;
		//int contador=1;
				
		
		for( CuentaContable cta : lsCtaContable  ) {			
			
			Row filaCta = reporte.getRow(inicioRow++);			
			filaCta.getCell(0).setCellValue(cta.getIcodigoCuenta());
			filaCta.getCell(1).setCellValue(cta.getSdescripcion());	
				
			
			Double totCtaDebe = serviceConcpPlanilla.listarProvisionDebe(empresaDTO,cta);
			Double totCtaHaber = serviceConcpPlanilla.listarProvisionHaber(empresaDTO,cta);
			
			filaCta.getCell(2).setCellValue(totCtaDebe);
			filaCta.getCell(3).setCellValue(totCtaHaber);
		}
						
		input.close();
		FileOutputStream outPut = new FileOutputStream("src/main/resources/Excel/ReporteCtaCont.xls");
		libro.write(outPut);
		outPut.close();

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@GetMapping("/descargar/{nombreArchivo:.+}")
	public ResponseEntity<Resource> descargar(@PathVariable("nombreArchivo") String nombreArchivo) throws Exception {

		String rutaDescarga = "src/main/resources/Excel";
		Path rutaArchivo = Paths.get(rutaDescarga).resolve(nombreArchivo).toAbsolutePath();
		Resource recurso = null;
		recurso = new UrlResource(rutaArchivo.toUri());
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + recurso.getFilename() + "\"");
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}

	private void crearExcel(EmpresaDTO empresaDTO) throws Exception {

		Empresa empresa = empresaDTO.getEmpresa();
		PdoAno pdoAno = empresaDTO.getPdoAno();
		PdoMes pdoMes = empresaDTO.getPdoMes();
		TipoPlanilla tipoPlanilla = empresaDTO.getTipoPlanilla();

		List<Trabajador> lstrabajador = new ArrayList<>();

		if (tipoPlanilla == null) {
			lstrabajador = serviceTrabajador.listarPorEmpresa(empresa);
		} else {
			List<TipoPlanillaDetalle> lsdetalle = service_tpd.listarTrabAsignados(tipoPlanilla);
			for (TipoPlanillaDetalle detalle : lsdetalle) {
				lstrabajador.add(detalle.getTrabajador());
			}
		}

		List<Horario> lshorario = serviceHorario.listarPorEmpresa(empresa);

		FileInputStream input = new FileInputStream("src/main/resources/Excel/AttSettingAsistencia.xls");
		HSSFWorkbook libro = new HSSFWorkbook(input);
		File file = new File("src/main/resources/Excel/AttSetting.xls");
		if (file.exists()) {
			file.delete();
		}

		HSSFSheet horario = libro.getSheetAt(0);
		HSSFSheet turno = libro.getSheetAt(1);

		Row rowDias = turno.getRow(4);
		// actualizar(rowDias);

		/********************************************************************************************/

		int inicioRow = 5;
		int contador = 1;

		for (Horario hr : lshorario) {

			Calendar hInicio = Calendar.getInstance();
			hInicio.setTime(hr.getHorIniDia());
			Calendar hFin = Calendar.getInstance();
			hFin.setTime(hr.getHorFinDia());
			Calendar aInicio = Calendar.getInstance();
			aInicio.setTime(hr.getHorAlmuerIni());
			Calendar aFin = Calendar.getInstance();
			aFin.setTime(hr.getHorAlmuerFin());

			Row filaH = horario.getRow(inicioRow++);

			filaH.getCell(0).setCellValue(contador++);

			int hi = hInicio.get(Calendar.HOUR_OF_DAY);
			int mi = hInicio.get(Calendar.MINUTE);
			filaH.getCell(1).setCellValue((hi < 10 ? "0" + hi : "" + hi) + ":" + (mi < 10 ? "0" + mi : "" + mi));

			int hai = aInicio.get(Calendar.HOUR_OF_DAY);
			int mai = aInicio.get(Calendar.MINUTE);
			filaH.getCell(2).setCellValue((hai < 10 ? "0" + hai : "" + hai) + ":" + (mai < 10 ? "0" + mai : "" + mai));

			int haf = aFin.get(Calendar.HOUR_OF_DAY);
			int maf = aFin.get(Calendar.MINUTE);
			filaH.getCell(3).setCellValue((haf < 10 ? "0" + haf : "" + haf) + ":" + (maf < 10 ? "0" + maf : "" + maf));

			int hf = hFin.get(Calendar.HOUR_OF_DAY);
			int mf = hFin.get(Calendar.MINUTE);
			filaH.getCell(4).setCellValue((hf < 10 ? "0" + hf : "" + hf) + ":" + (mf < 10 ? "0" + mf : "" + mf));
		}

		/***********************************************************************************************************************/

		Row filaFecha = turno.getRow(2);
		filaFecha.getCell(4).setCellValue(pdoAno.getDescripcion()
				+ (pdoMes.getIdPdoMes() < 10 ? "-0" + pdoMes.getIdPdoMes() : "-" + pdoMes.getIdPdoMes()) + "-01");

		int inicioRowT = 5;
		TimeZone timezone = TimeZone.getDefault();
		Calendar diaSemana = new GregorianCalendar(timezone);

		List<Integer> lsferiado = new ArrayList<>();

		String feriados = pdoMes.getTxtDiasFeriados();
		if (feriados != null) {
			String[] sferiado = feriados.split(",");

			if (sferiado.length > 0) {
				for (int i = 0; i < sferiado.length; i++) {
					lsferiado.add(Integer.parseInt(sferiado[i]));
				}
			}
		}

		for (Trabajador tb : lstrabajador) {
			int contadorT = 4;
			int posicion = 1 + lshorario.indexOf(tb.getHorario());

			Row filaT = turno.getRow(inicioRowT++);
			filaT.getCell(0).setCellValue(tb.getIdTrabajador());
			filaT.getCell(1).setCellValue(tb.getNombres() + " " + tb.getApePater() + " " + tb.getApeMater());
			filaT.getCell(2).setCellValue("Empresa");

			for (int i = 1; i < (pdoMes.getCantidadDias() + 1); i++) {

				if (lsferiado.indexOf(i) > -1) {
					contadorT++;
				} else {
					diaSemana.set(pdoAno.getDescripcion(), pdoMes.getIdPdoMes() - 1, i);
					int ds = diaSemana.get(Calendar.DAY_OF_WEEK);

					switch (ds) {
					case 1:
						if (tb.getHorario().getCheckDomingo()) {
							filaT.getCell(contadorT++).setCellValue(posicion);
						} else {
							contadorT++;
						}
						break;
					case 2:
						if (tb.getHorario().getCheckLunes()) {
							filaT.getCell(contadorT++).setCellValue(posicion);
						} else {
							contadorT++;
						}
						break;
					case 3:
						if (tb.getHorario().getCheckMartes()) {
							filaT.getCell(contadorT++).setCellValue(posicion);
						} else {
							contadorT++;
						}
						break;
					case 4:
						if (tb.getHorario().getCheckMiercoles()) {
							filaT.getCell(contadorT++).setCellValue(posicion);
						} else {
							contadorT++;
						}
						break;
					case 5:
						if (tb.getHorario().getCheckJueves()) {
							filaT.getCell(contadorT++).setCellValue(posicion);
						} else {
							contadorT++;
						}
						break;
					case 6:
						if (tb.getHorario().getCheckViernes()) {
							filaT.getCell(contadorT++).setCellValue(posicion);
						} else {
							contadorT++;
						}
						break;
					case 7:
						if (tb.getHorario().getCheckSabado()) {
							filaT.getCell(contadorT++).setCellValue(posicion);
						} else {
							contadorT++;
						}
						break;
					}
				}
			}
		}

		/******************************************************************************************************************************/

		input.close();
		FileOutputStream outPut = new FileOutputStream("src/main/resources/Excel/AttSetting.xls");
		libro.write(outPut);
		outPut.close();
	}

	private void actualizar(Row rowDias) {

		Cell c4 = rowDias.getCell(4);
		// System.out.println("c4 "+ c4.getCellFormula());
		c4.setCellFormula("CHOOSE(WEEKDAY(E3,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c5 = rowDias.getCell(5);
		c5.setCellFormula("CHOOSE(WEEKDAY(E3+1,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c6 = rowDias.getCell(6);
		c6.setCellFormula("CHOOSE(WEEKDAY(E3+2,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c7 = rowDias.getCell(7);
		c7.setCellFormula("CHOOSE(WEEKDAY(E3+3,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c8 = rowDias.getCell(8);
		c8.setCellFormula("CHOOSE(WEEKDAY(E3+4,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c9 = rowDias.getCell(9);
		c9.setCellFormula("CHOOSE(WEEKDAY(E3+5,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c10 = rowDias.getCell(10);
		c10.setCellFormula("CHOOSE(WEEKDAY(E3+6,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c11 = rowDias.getCell(11);
		c11.setCellFormula("CHOOSE(WEEKDAY(E3+7,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c12 = rowDias.getCell(12);
		c12.setCellFormula("CHOOSE(WEEKDAY(E3+8,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c13 = rowDias.getCell(13);
		c13.setCellFormula("CHOOSE(WEEKDAY(E3+9,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c14 = rowDias.getCell(14);
		c14.setCellFormula("CHOOSE(WEEKDAY(E3+10,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c15 = rowDias.getCell(15);
		c15.setCellFormula("CHOOSE(WEEKDAY(E3+11,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c16 = rowDias.getCell(16);
		c16.setCellFormula("CHOOSE(WEEKDAY(E3+12,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c17 = rowDias.getCell(17);
		c17.setCellFormula("CHOOSE(WEEKDAY(E3+13,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c18 = rowDias.getCell(18);
		c18.setCellFormula("CHOOSE(WEEKDAY(E3+14,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c19 = rowDias.getCell(19);
		c19.setCellFormula("CHOOSE(WEEKDAY(E3+15,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c20 = rowDias.getCell(20);
		c20.setCellFormula("CHOOSE(WEEKDAY(E3+16,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c21 = rowDias.getCell(21);
		c21.setCellFormula("CHOOSE(WEEKDAY(E3+17,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c22 = rowDias.getCell(22);
		c22.setCellFormula("CHOOSE(WEEKDAY(E3+18,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c23 = rowDias.getCell(23);
		c23.setCellFormula("CHOOSE(WEEKDAY(E3+19,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c24 = rowDias.getCell(24);
		c24.setCellFormula("CHOOSE(WEEKDAY(E3+20,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c25 = rowDias.getCell(25);
		c25.setCellFormula("CHOOSE(WEEKDAY(E3+21,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c26 = rowDias.getCell(26);
		c26.setCellFormula("CHOOSE(WEEKDAY(E3+22,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c27 = rowDias.getCell(27);
		c27.setCellFormula("CHOOSE(WEEKDAY(E3+23,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c28 = rowDias.getCell(28);
		c28.setCellFormula("CHOOSE(WEEKDAY(E3+24,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c29 = rowDias.getCell(29);
		c29.setCellFormula("CHOOSE(WEEKDAY(E3+25,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c30 = rowDias.getCell(30);
		c30.setCellFormula("CHOOSE(WEEKDAY(E3+26,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c31 = rowDias.getCell(31);
		c31.setCellFormula("CHOOSE(WEEKDAY(E3+27,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c32 = rowDias.getCell(32);
		c32.setCellFormula("CHOOSE(WEEKDAY(E3+28,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c33 = rowDias.getCell(33);
		c33.setCellFormula("CHOOSE(WEEKDAY(E3+29,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");

		Cell c34 = rowDias.getCell(34);
		c34.setCellFormula("CHOOSE(WEEKDAY(E3+30,2),$AJ$1,$AK$1,$AL$1,$AM$1,$AN$1,$AO$1,$AP$1)");
	}
}
