package com.mitocode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.DepartamentoEmpresa;
import com.mitocode.model.Empresa;
import com.mitocode.repo.DepartamentoEmpresaRepo;
import com.mitocode.service.DepartamentoEmpresaService;

@Service
public class DepartamentoEmpresaServiceImpl implements DepartamentoEmpresaService{
	
	@Autowired
	DepartamentoEmpresaRepo repo;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);

	@Override
	public DepartamentoEmpresa registrar(DepartamentoEmpresa obj) {
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " registrarDepartamentoEmpresa. ERROR : " + e.getMessage()+ e.getLocalizedMessage());
			
			throw e;
		}
	}

	@Override
	public DepartamentoEmpresa modificar(DepartamentoEmpresa obj) {
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " modificarDepartamentoEmpresa. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public DepartamentoEmpresa leer(Integer id) {
		
		return null;
	}

	@Override
	public List<DepartamentoEmpresa> listar() {
		
		return null;
	}

	@Override
	public Boolean eliminar(Integer id) {
		try {
			repo.deleteById(id);
			Boolean resp = repo.existsById(id);
			return resp;
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " eliminarDepartamentoXEmpresa. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<DepartamentoEmpresa> listarDepartXEmpresa(Empresa emp) {
		try {
			return repo.findByEmpresa(emp);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " listarDepartamentoXEmpresa. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<DepartamentoEmpresa> eliminardeDepartamentoEmpresas(DepartamentoEmpresa depEmp) {
		
		return null;
	}


}
