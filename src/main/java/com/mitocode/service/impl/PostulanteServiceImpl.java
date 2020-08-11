package com.mitocode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.Empresa;
import com.mitocode.model.Postulante;
import com.mitocode.repo.PostulanteRepo;
import com.mitocode.service.PostulanteService;

@Service
public class PostulanteServiceImpl implements PostulanteService{

	@Autowired
	PostulanteRepo repo;

	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);
	
	@Override
	public Postulante registrar(Postulante obj) {
		try {
			return repo.save(obj);

		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " registrarPostulante. ERROR : " + e.getMessage());
			throw e;
		}

	}

	@Override
	public Postulante modificar(Postulante obj) {
		return null;
	}

	@Override
	public Postulante leer(Integer id) {
		return null;
	}

	
	
	@Override
	public List<Postulante> listar() {
		try {
			return repo.findAll();
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarP. ERROR : " + e.getMessage());
			throw e;
		}
	}

	
	
	
	@Override
	public Boolean eliminar(Integer id) {
		return null;
	}

	@Override
	public List<Postulante> listarPorEmpresa(Empresa empresa) {
		return null;
	}

}
