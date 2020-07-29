package com.mitocode.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mitocode.model.CuentaContable;
import com.mitocode.model.Empresa;
import com.mitocode.repo.CuentaContableRepo;
import com.mitocode.service.CuentaContableService;

@Service
public class CuentaContableServiceImpl implements CuentaContableService{
	
	@Autowired
	CuentaContableRepo repo;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);
	@Override
	public CuentaContable registrar(CuentaContable obj) {
		//CuentaContable cueContable=repo.save(obj);		
		//return cueContable;
		try {
			return repo.save(obj);			
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + "registrarCuentaContable. ERROR : " + e.getMessage());
			throw e;

		}	
	}

	@Override
	public CuentaContable modificar(CuentaContable obj) {
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+ "modificarCuentaContable. ERROR : " + e.getMessage());
			throw e;
		}
		
	}

	@Override
	public CuentaContable leer(Integer id) {
		
		return null;
	}

	@Override
	public List<CuentaContable> listar() {
		
		//return repo.findAll();
		return null;
	}

	@Override
	public Boolean eliminar(Integer id) {
		try {
			Boolean existe = repo.existsById(id);
			if(existe) {
				repo.deleteById(id);
			}return existe;
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+ "eliminarCuentaContable. ERROR : " + e.getMessage());
			throw e;		
		}
	}

	@Override
	public List<CuentaContable> listarxEmpresa(Empresa empresa) {
		
		try {
			return repo.findByEmpresa(empresa);
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+ "listarCuentaContablePorEmpresa. ERROR : " + e.getMessage());
			throw e;		
		}
	}

	@Override
	public CuentaContable buscarCuentaContablexDesc(String cuentaContable) {
		try {
			return repo.findBySdescripcion(cuentaContable);
		}catch (Exception e) {
			LOG.error(this.getClass().getSimpleName()+ "buscarCuentaContablePorDescripcion. ERROR : " + e.getMessage());
			throw e;
		}			
	}

}
