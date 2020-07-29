package com.mitocode.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mitocode.model.Empresa;
import com.mitocode.model.Trabajador;

public class DataDuroPrincipal {
	
	Timestamp f_1=null;
	Timestamp f_2=null;
	Timestamp f_3=null;	
	Timestamp f_4=null;
	
	public void iniFecha() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fecha_1="2018-05-10";
		String fecha_2="2019-08-25";
		String fecha_3="2019-04-08";
		String fecha_4="2019-06-15";
		
		Date fec_1=sdf.parse(fecha_1);
		Date fec_2=sdf.parse(fecha_2);
		Date fec_3=sdf.parse(fecha_3);	
		Date fec_4=sdf.parse(fecha_4);
		
		f_1=new Timestamp(fec_1.getTime());
		f_2=new Timestamp(fec_2.getTime());
		f_3=new Timestamp(fec_3.getTime());
		f_4=new Timestamp(fec_4.getTime());
	}
	
	public Trabajador nuevoTrabajador() throws Exception {
		this.iniFecha();
		Trabajador trab = new Trabajador();
		DataDuroComplementos ddc= new DataDuroComplementos();
		trab.setAfilAseguPens(0);
		trab.setApePater("Picardo");
		trab.setApeMater("Mendoza");
		trab.setCorreo("jesuspicardo95@gmail.com");
		trab.setDireccion("Jr. Corbeta La Union 150 Blck. B Dpto. 403");
		trab.setEpsServProp(0);
		trab.setEssaludVida(0);
		trab.setFecIngrSalud(f_1);
		trab.setFecNac(f_2);
		trab.setFecRegPens(f_3);
		trab.setNombres("Jesús Picardo");
		trab.setNomZona("Las Praderas de Surco");
		trab.setNroCel("933807711");
		trab.setNroCuspp("RFERF45582");
		trab.setNroDoc("70876196");
		trab.setNroEssalud("7855695725");
		trab.setNroHij(0);
		trab.setReferencia("A 1 cuadra del mercado Santiago Apostol");
		trab.setSexo("M");
		
		trab.setTipoZona(ddc.nuevoTipoZona());
		trab.setTipoDoc(ddc.nuevoTipoDoc());
		trab.setSituacion(ddc.nuevoSituacion());
		trab.setRegSalud(ddc.nuevoRegSalud());
		trab.setProvincia(ddc.nuevoProvincia());
		trab.setPais(null);
		trab.setOcupacion(ddc.nuevoOcupacion());
		trab.setNivelEdu(ddc.nuevoNivelEdu());
		trab.setEstadoCivil(ddc.nuevoEstadoCivil());
		trab.setEps(null);
		trab.setEmpresa(ddc.nuevaEmpresa());
		trab.setDistrito(ddc.nuevoDistrito());
		trab.setDepartamento(ddc.nuevoDepartamento());
		trab.setAfp(null);
		return trab;
	}
}
