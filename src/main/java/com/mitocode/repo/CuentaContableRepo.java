package com.mitocode.repo;

import java.util.List;
import com.mitocode.model.CuentaContable;
import com.mitocode.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaContableRepo extends JpaRepository<CuentaContable, Integer>{

	List<CuentaContable> findByEmpresa (Empresa empresa);
	CuentaContable findBySdescripcion(String sdescripcion);
	
	
}
