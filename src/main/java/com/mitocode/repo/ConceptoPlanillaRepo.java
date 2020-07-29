package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.ConceptoPlanilla;
import com.mitocode.model.CuentaContable;
import com.mitocode.model.Empresa;
import com.mitocode.model.PlanillaHistorico;


public interface ConceptoPlanillaRepo extends JpaRepository<ConceptoPlanilla, Integer> {
	
	List<ConceptoPlanilla> findByEmpresa (Empresa empresa);
	ConceptoPlanilla findBySdescripcion (String sdescripcion);
	
//	@Query(value ="select cuenta_debe_provision from concepto_planilla",nativeQuery=true)
//	List<ConceptoPlanilla> listarProvisionDebe(Empresa empresa);
	List<ConceptoPlanilla>  findByCuentaDebeProvision (CuentaContable ctaContable);
	List<ConceptoPlanilla> findByCuentaHaberProvision (CuentaContable ctaContable);

}
