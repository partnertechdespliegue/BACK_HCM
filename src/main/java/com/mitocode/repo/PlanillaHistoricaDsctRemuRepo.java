package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitocode.model.PlanillaHistoricaDsctRemu;
import com.mitocode.model.PlanillaHistorico;

public interface PlanillaHistoricaDsctRemuRepo extends JpaRepository<PlanillaHistoricaDsctRemu, Integer>{
	
	List<PlanillaHistoricaDsctRemu> findByPlanillaHistorico (PlanillaHistorico planillaHistorico);

}
