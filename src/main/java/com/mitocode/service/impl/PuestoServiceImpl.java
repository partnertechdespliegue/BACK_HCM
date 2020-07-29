package com.mitocode.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.AreaDepartamentoEmpresa;
import com.mitocode.model.ConceptoPlanilla;
import com.mitocode.model.Empresa;
import com.mitocode.model.ProyeccionPuesto;
import com.mitocode.model.Puesto;
import com.mitocode.repo.PuestoRepo;
import com.mitocode.service.PuestoService;
import com.mitocode.repo.ContratoRepo;
import com.mitocode.repo.ProyeccionPuestoRepo;

@Service
public class PuestoServiceImpl  implements PuestoService{
	@Autowired
	PuestoRepo repo;
	
	@Autowired 
	ContratoRepo repoContrato;
	
	@Autowired
	ProyeccionPuestoRepo repoProy;
	
	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);

	@Override
	public Puesto registrar(Puesto obj) {
		try {		
			 
			return repo.save(obj);
			
		}catch(Exception e){
			LOG.error(this.getClass().getSimpleName()+" guardarPuesto. ERROR : "+e.getMessage());
			throw e;
		}
	}


	@Override
	public Puesto modificar(Puesto obj) {
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " registrar. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Puesto leer(Integer id) {
		
		return null;
	}

	@Override
	public List<Puesto> listar() {
		
		return null;
	}

	@Override
	public Boolean eliminar(Integer id) {
		try {
			repo.deleteById(id);
			Boolean resp = repo.existsById(id);
			return resp;
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " eliminarPuesto. ERROR : " + e.getMessage());
			throw e;
		}
	}

	
	@Override
	public List<Puesto> listarPuesto(AreaDepartamentoEmpresa areaDepEmp) {
		try {
			return repo.findByAreaDepartamentoEmpresa(areaDepEmp);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<Puesto> listarPuestoXOrden(Integer idAreaDepartamento, Integer iorden) {
		try {
			return repo.listarPuestoXOrden(idAreaDepartamento, iorden);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Puesto> listarPorEmpresa(Empresa emp) {
		try {
			
			return repo.listarPuestoXEmpresa(emp.getIdEmpresa());
			
			}catch(Exception e) {
				LOG.error(this.getClass().getSimpleName() + " listarPuestoPorNombre. ERROR : " + e.getMessage());
				throw e;
			}
	}

	@Override
	public List<ProyeccionPuesto> listarProyeccionPuesto(Empresa emp) {
		
		try {
			
			return repoProy.listarProyPuestoXEmpresa(emp.getIdEmpresa());
			
			}catch(Exception e) {
				LOG.error(this.getClass().getSimpleName() + " listarProyeccionPuestoPorNombre. ERROR : " + e.getMessage());
				throw e;
			}
		}
	

}
