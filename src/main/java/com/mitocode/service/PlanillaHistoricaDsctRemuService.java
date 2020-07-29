package com.mitocode.service;

import java.util.List;

import com.mitocode.model.PlanillaHistoricaDsctRemu;
import com.mitocode.model.PlanillaHistorico;

public interface PlanillaHistoricaDsctRemuService extends ICRUD<PlanillaHistoricaDsctRemu>{

	List<PlanillaHistoricaDsctRemu> listarPorPlanHistorico(PlanillaHistorico planillaHistorico);
	List<PlanillaHistoricaDsctRemu> registrarLista (List<PlanillaHistoricaDsctRemu> lsphDsctRemu);

}
