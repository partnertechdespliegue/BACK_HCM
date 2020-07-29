package com.mitocode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.ProyeccionPuesto;
import com.mitocode.model.Puesto;
import com.mitocode.repo.ProyeccionPuestoRepo;
import com.mitocode.service.ProyeccionPuestoService;

@Service
public class PuestoProyeccionServiceImpl implements ProyeccionPuestoService {

	@Autowired
	ProyeccionPuestoRepo repo_proy;

	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);

	@Override
	public ProyeccionPuesto registrar(ProyeccionPuesto obj) {
		try {

			// Puesto puesto = new Puesto();
			// Puesto puestoJefe = new Puesto();
			ProyeccionPuesto res_proy = repo_proy.save(obj);
			// crearProyeccion2(puesto, puestoJefe);
			return res_proy;

		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " guardarProyeccionPuesto. ERROR : " + e.getMessage());
			throw e;
		}
	}

//	private void crearProyeccion2(Puesto puesto, Puesto puestoJefe) {
//		
//		ProyeccionPuesto proyPuesto = new ProyeccionPuesto();
//		//Puesto puesto = new Puesto();
//		
//		int orden=0;
//		proyPuesto= repo_proy.findByPuesto(puestoJefe);
//		orden = proyPuesto.getOrden();
//		proyPuesto.setPuesto(puesto);
//		proyPuesto.setPuestoProyeccion(puestoJefe);	
//		proyPuesto.setOrden(orden + 1 );		
//		repo_proy.save(proyPuesto);	
//	}

	@Override
	public ProyeccionPuesto modificar(ProyeccionPuesto obj) {
		try {
			return repo_proy.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " registrar. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public ProyeccionPuesto leer(Integer id) {
		return null;
	}

	@Override
	public List<ProyeccionPuesto> listar() {
		return null;
	}

	@Override
	public Boolean eliminar(Integer id) {

		try {
			if (repo_proy.existsById(id)) {
				repo_proy.deleteById(id);
				return false;
			}
			return true;
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " eliminarProyeccionPuesto. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public ProyeccionPuesto crearProyeccion(Puesto puesto, Puesto puestoJefe) {

		ProyeccionPuesto res_proy = new ProyeccionPuesto();

		int orden = 0;
		ProyeccionPuesto proyPuesto = null;
		if (puestoJefe != null) {
			proyPuesto = repo_proy.findByPuesto(puestoJefe);
			orden = proyPuesto.getIorden();
		}

		res_proy.setPuesto(puesto);
		res_proy.setPuestoProyeccion(puestoJefe);
		res_proy.setIorden(orden + 1);
		return repo_proy.save(res_proy);

	}

	@Override
	public ProyeccionPuesto actualizarProyeccion(ProyeccionPuesto proy) {
		return repo_proy.save(proy);
	}
	
	@Override
	public ProyeccionPuesto buscarPorId(Integer iid_proyeccion) {
		return repo_proy.findByIidProyeccion(iid_proyeccion);
	}

}
