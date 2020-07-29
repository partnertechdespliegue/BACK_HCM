package com.mitocode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.NivelEdu;
import com.mitocode.repo.NivelEduRepo;
import com.mitocode.service.NivelEduService;


@Service
public class NivelEduServiceImpl implements NivelEduService{

	@Autowired
	NivelEduRepo repo;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);
	
	
	@Override
	public NivelEdu registrar(NivelEdu obj) {
		
		return null;
	}

	@Override
	public NivelEdu modificar(NivelEdu obj) {
		
		return null;
	}

	@Override
	public NivelEdu leer(Integer id) {
		
		return null;
	}

	@Override
	public List<NivelEdu> listar() {
		try {
			return repo.findAll();
		}catch(Exception e) {
			LOG.error(this.getClass().getSimpleName()+" listarNivelEdu. ERROR : "+e.getMessage());
			throw e;
		}
	}

	@Override
	public Boolean eliminar(Integer id) {
		
		return null;
	}

}
