package com.mitocode.service;

import java.util.List;

import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Trabajador;
import com.mitocode.model.VacacionesVendidas;

public interface VacacionesVendidasService extends ICRUD<VacacionesVendidas> {
	
	List<VacacionesVendidas> listarPorTrabPeriodo (Trabajador trabajador, PdoAno ano, PdoMes mes);
	
}
