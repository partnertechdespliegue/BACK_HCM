package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Pagina;

public interface PaginaRepo extends JpaRepository<Pagina, Integer> {

	@Query(value = "insert into perfiles_pagina (id_perfil, id_pagina) VALUES (:idPerfil, :idPagina)", nativeQuery = true)
	void asignarPagina(@Param("idPerfil") Integer idPerfil, @Param("idPagina") Integer idPagina);

	@Query(value = "DELETE FROM perfiles_pagina WHERE id_perfil= :idPerfil AND id_pagina= :idPagina", nativeQuery = true)
	void quitarPagina(@Param("idPerfil") Integer idPerfil, @Param("idPagina") Integer idPagina);
	
	@Query(value = "SELECT perfiles_pagina.id_pagina FROM perfiles_pagina\r\n" + 
			"WHERE id_perfil =:idPerfil AND id_pagina =:idPagina", nativeQuery = true)
	List buscarRegistro(@Param("idPerfil") Integer idPerfil, @Param("idPagina") Integer idPagina);
}
