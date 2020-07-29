package com.mitocode.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mitocode.model.DerechoHabientes;
import com.mitocode.model.Trabajador;


public interface DerechoHabientesRepo extends JpaRepository<DerechoHabientes, Integer>{

	public DerechoHabientes findByIdDerechoHabiente (Integer id);
	public List<DerechoHabientes>  findByTrabajadorAndEstado(Trabajador trabajador, Integer estado);


}
