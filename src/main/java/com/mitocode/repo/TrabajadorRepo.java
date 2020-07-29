package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.Empresa;
import com.mitocode.model.Situacion;
import com.mitocode.model.Trabajador;

public interface TrabajadorRepo extends JpaRepository<Trabajador, Integer>{
	//public Integer situacion= 1;
	
	public Trabajador findByIdTrabajador(Integer id);

	//List<Trabajador> findByEmpresaAndSituacion (Empresa empresa, Integer situación)
	List<Trabajador>  findByEmpresaAndSituacion (Empresa empresa, Situacion situación);
	List<Trabajador> findByEmpresa(Empresa emp);
}
