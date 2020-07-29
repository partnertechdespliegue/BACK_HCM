package com.mitocode.service;


import java.util.List;

import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.Trabajador;
import com.mitocode.model.VacacionesTomadas;

public interface VacacionesTomadasService extends ICRUD<VacacionesTomadas>{
	
	List<VacacionesTomadas> encontrarVacacionTomada (Trabajador trabajador, PdoAno pdoAno, PdoMes pdoMes);


}
