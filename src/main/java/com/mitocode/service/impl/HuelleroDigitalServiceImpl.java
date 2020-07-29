package com.mitocode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.Empresa;
import com.mitocode.model.HuelleroDigital;
import com.mitocode.repo.HuelleroDigitalRepo;
import com.mitocode.service.HuelleroDigitalService;

@Service
public class HuelleroDigitalServiceImpl implements HuelleroDigitalService{
	
	@Autowired
	HuelleroDigitalRepo repo;

	@Override
	public HuelleroDigital registrar(HuelleroDigital obj) {
		return repo.save(obj);
	}

	@Override
	public HuelleroDigital modificar(HuelleroDigital obj) {
		return repo.save(obj);
	}

	@Override
	public HuelleroDigital leer(Integer id) {
		
		return null;
	}
	
	@Override
	public HuelleroDigital encontrar(Integer id) {
		return repo.findByIidHuelleroDigital(id);
	}

	@Override
	public List<HuelleroDigital> listar() {
		
		return null;
	}

	@Override
	public Boolean eliminar(Integer id) {
		
		return null;
	}
	
	@Override
	public List<HuelleroDigital> encontrarIpPublicaEmpresa(String sipPublica) {
		return repo.findBySipPublicaAndEmpresa(sipPublica, null);
	}

	@Override
	public List<HuelleroDigital> encontrarIpPublica(Empresa empresa) {
		// IpPublica viaja en RUC
		return repo.encontrarIpPublica(empresa.getRuc(), empresa.getIdEmpresa());
	}

}
