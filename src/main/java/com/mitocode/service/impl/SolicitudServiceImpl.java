package com.mitocode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.dto.SolicitudDTO;
import com.mitocode.model.Empresa;
import com.mitocode.model.Solicitud;
import com.mitocode.model.Trabajador;
import com.mitocode.repo.SolicitudRepo;
import com.mitocode.service.SolicitudService;

@Service
public class SolicitudServiceImpl implements SolicitudService {
	
	@Autowired
	SolicitudRepo repo;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);


	@Override
	public Solicitud registrar(Solicitud obj) {
		return repo.save(obj);
	}

	@Override
	public Solicitud modificar(Solicitud obj) {
		
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " modificarSolicitud. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Solicitud leer(Integer id) {
		
		return null;
	}


	@Override
	public Boolean eliminar(Integer id) {
		
		try {
			repo.deleteById(id);
			Boolean resp = repo.existsById(id);
			return resp;
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " eliminarSolicitud. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Solicitud> listarxTrabajador(Trabajador trabajador) {
		
		try {
			return repo.findByTrabajador(trabajador);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarSolicitudPorTrabajador. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Solicitud> listar() {
		try {
			return repo.findAll();
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarSolicitud. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Solicitud> listarxEmpresa(Empresa empresa) {
		
		try {
			return repo.listarSolicitudXEmpresa(empresa.getIdEmpresa());
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarSolicitudPorEmpresa. ERROR : " + e.getMessage());
			throw e;
		}
	}


}
