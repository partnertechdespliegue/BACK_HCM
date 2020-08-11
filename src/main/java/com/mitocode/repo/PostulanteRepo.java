package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Empresa;
import com.mitocode.model.Postulante;
import com.mitocode.model.Trabajador;


public interface PostulanteRepo extends JpaRepository<Postulante, Integer>{

}
