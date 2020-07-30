package com.mitocode.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mitocode.model.Modulo;
import com.mitocode.model.Pagina;
import com.mitocode.model.Perfil;

public interface ModuloService extends ICRUD<Modulo> {

	List<Modulo> listarModuloPorPerfil(Perfil perfil);

	boolean asignarPagina(Integer idPerfil, Integer idPagina);

	boolean quitarPagina(Integer idPerfil, Integer idPagina);

	List<Pagina> listarPaginas();

	boolean existe(Integer idPerfil, Integer idPagina);

}
