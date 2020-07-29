package com.mitocode.service;


import com.mitocode.model.ProyeccionPuesto;
import com.mitocode.model.Puesto;

public interface ProyeccionPuestoService extends ICRUD<ProyeccionPuesto>{

	public ProyeccionPuesto crearProyeccion (Puesto puesto,Puesto puestoJefe);
	
	public ProyeccionPuesto actualizarProyeccion (ProyeccionPuesto proy);

	ProyeccionPuesto buscarPorId(Integer iid_proyeccion);
}
