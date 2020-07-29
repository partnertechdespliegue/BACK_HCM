package com.mitocode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.Empresa;
import com.mitocode.repo.AreaDepartamentoEmpresaRepo;
import com.mitocode.service.AreaDepartamentoEmpresaService;

@Service
public class AreaDepartamentoEmpresaServiceImpl implements AreaDepartamentoEmpresaService{
	
	@Autowired
	AreaDepartamentoEmpresaRepo repo;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);
	
	

	@Override
	public AreaDepartamentoEmpresa registrar(AreaDepartamentoEmpresa obj) {
		try {		
			return repo.save(obj);
		}catch(Exception e){
			LOG.error(this.getClass().getSimpleName()+" guardarAreaDepartamentoEmpresa. ERROR : "+e.getMessage());
			throw e;
		}
	}

	@Override
	public AreaDepartamentoEmpresa modificar(AreaDepartamentoEmpresa obj) {
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " registrar. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public AreaDepartamentoEmpresa leer(Integer id) {
		
		return null;
	}

	@Override
	public List<AreaDepartamentoEmpresa> listar() {
		
		return null;
	}

	@Override
	public Boolean eliminar(Integer id) {
		try {
			repo.deleteById(id);
			Boolean resp = repo.existsById(id);
			return resp;
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " eliminarAreaDepartamentoEmpresa. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<AreaDepartamentoEmpresa> listarAreaDepartamentoEmpresa(DepartamentoEmpresa depEmp) {
		try {
			return repo.findByDepartamentoEmpresa(depEmp);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<AreaDepartamentoEmpresa> listarPorEmpresa(Empresa emp) {
		try {
			
		return repo.listarAreaDepartamentoXEmpresa(emp.getIdEmpresa());
		
		}catch(Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarAreaPorNombre. ERROR : " + e.getMessage());
			throw e;
		}

	}

	@Override
	public List<AreaDepartamentoEmpresa> eliminarAreaDepartamentoEmpresa(AreaDepartamentoEmpresa AreaDepEmp) {
		
		return null;
	}


}
