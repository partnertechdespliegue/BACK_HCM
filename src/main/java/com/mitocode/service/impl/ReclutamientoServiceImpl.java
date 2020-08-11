package com.mitocode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.Empresa;
import com.mitocode.model.Reclutamiento;
import com.mitocode.repo.ReclutamientoRepo;
import com.mitocode.service.ReclutamientoService;

@Service
public class ReclutamientoServiceImpl implements ReclutamientoService {
	
	@Autowired
	ReclutamientoRepo repo;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);

	@Override
	public Reclutamiento registrar(Reclutamiento obj) {
		
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " registrarReclutamiento. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Reclutamiento modificar(Reclutamiento obj) {
		
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " modificarReclutamiento. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Reclutamiento leer(Integer id) {
		
		return null;
	}

	@Override
	public List<Reclutamiento> listar() {
		
		return null;
	}

	@Override
	public Boolean eliminar(Integer id) {
		
		try {
			Boolean resp = repo.existsById(id);
			if (resp) {
				repo.deleteById(id);
			}
			return resp;
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " eliminarReclutamiento. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Reclutamiento> listarxEmpresa(Empresa empresa) {
		
		try {
			return repo.listarReclutamientoXEmpresa(empresa.getIdEmpresa());
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarReclutamientoPorEmpresa. ERROR : " + e.getMessage());
			throw e;
		}
	}

}
