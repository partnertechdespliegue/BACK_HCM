package com.mitocode.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.mitocode.model.Modulo;
import com.mitocode.model.Pagina;
import com.mitocode.model.Perfil;
import com.mitocode.repo.ModuloRepo;
import com.mitocode.repo.PaginaRepo;
import com.mitocode.repo.PerfilRepo;
import com.mitocode.service.ModuloService;

@Service
public class ModuloServiceImpl implements ModuloService {

	@Autowired
	private ModuloRepo repo;

	@Autowired
	private PerfilRepo repo_perfil;

	@Autowired
	private PaginaRepo repo_pagina;

	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);

	@Override
	public Modulo registrar(Modulo obj) {

		return null;
	}

	@Override
	public Modulo modificar(Modulo obj) {

		return null;
	}

	@Override
	public Modulo leer(Integer id) {

		return null;
	}

	@Override
	public List<Modulo> listar() {

		return null;

	}

	@Override
	public List<Pagina> listarPaginas() {
		return repo_pagina.findAll();
	}

	@Override
	public Boolean eliminar(Integer id) {

		return null;
	}

	@Override
	public boolean asignarPagina(Integer idPerfil, Integer idPagina) {
		try {
			repo_pagina.asignarPagina(idPerfil, idPagina);
			return true;
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public boolean quitarPagina(Integer idPerfil, Integer idPagina) {
		try {
			repo_pagina.quitarPagina(idPerfil, idPagina);
			return true;
		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public boolean existe(Integer idPerfil, Integer idPagina) {
		List ls = repo_pagina.buscarRegistro(idPerfil, idPagina);
		if (ls.size() > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<Modulo> listarModuloPorPerfil(Perfil perfil) {
		try {
			Perfil p = repo_perfil.findByIdPerfil(perfil.getIdPerfil());
			List<Modulo> lsmodulo = repo.listarModuloPorPerfil(perfil.getIdPerfil());

			List<Modulo> lsmoduloFinal = new ArrayList<>();
			for (Modulo modulo : lsmodulo) {
				modulo.setLspagina(null);
				List<Pagina> lspaginaFinal = new ArrayList<>();
				for (Pagina pagina : p.getLspaginas()) {
					if (pagina.getModulo().getIdModulo() == modulo.getIdModulo()) {
						lspaginaFinal.add(0, pagina);
					}
				}
				modulo.setLspagina(lspaginaFinal);
				lsmoduloFinal.add(modulo);
			}
			return lsmoduloFinal;
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarModuloPorPerfil. ERROR : " + e.getMessage());
			throw e;
		}
	}
}
