package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.Empresa;

public interface DepartamentoEmpresaRepo extends JpaRepository<DepartamentoEmpresa, Integer>{

	List<DepartamentoEmpresa> findByEmpresa(Empresa emp);
	
}
