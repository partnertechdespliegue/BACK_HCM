package com.mitocode.service;

import java.util.List;

import com.mitocode.model.Empresa;
import com.mitocode.model.HuelleroDigital;

public interface HuelleroDigitalService extends ICRUD<HuelleroDigital>{

	List<HuelleroDigital> encontrarIpPublicaEmpresa(String sipPublica);
	HuelleroDigital encontrar(Integer id);
	List<HuelleroDigital> encontrarIpPublica(Empresa empresa);

}
