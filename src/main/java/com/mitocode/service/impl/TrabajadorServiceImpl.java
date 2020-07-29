package com.mitocode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.Empresa;

import com.mitocode.model.Situacion;
import com.mitocode.model.Trabajador;
import com.mitocode.repo.TrabajadorRepo;
import com.mitocode.service.TrabajadorService;
import com.mitocode.util.Constantes;

@Service
public class TrabajadorServiceImpl implements TrabajadorService {

	@Autowired
	TrabajadorRepo repo;

	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);

	@Override
	public Trabajador registrar(Trabajador obj) {
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " registrarTrabajador. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Trabajador modificar(Trabajador obj) {
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " modificarTrabajador. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Trabajador leer(Integer id) {
		
		return null;
	}

	@Override
	public List<Trabajador> listar() {
		try {
			return repo.findAll();
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarTrabajadores. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Boolean eliminar(Integer id) {
		
		return null;
	}
	
	@Override
	public List<Trabajador> encontrarTrab(Empresa emp) {
		try {
			Situacion tmp = new Situacion();
			tmp.setIdSituacion(Constantes.situacion);
			return repo.findByEmpresaAndSituacion(emp, tmp);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " encontrarTrabajador. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Trabajador encontrarTrab(Integer id) {
		
		return null;
	}
	
	@Override
	public List<Trabajador> listarGerencial(Empresa emp) {
		try {
			return repo.findByEmpresa(emp);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarG. ERROR : " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public List<Trabajador> listarPorEmpresa(Empresa empresa) {
		try {
			return repo.findByEmpresa(empresa);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarTrabajadores. ERROR : " + e.getMessage());
			throw e;
		}
	}
}
