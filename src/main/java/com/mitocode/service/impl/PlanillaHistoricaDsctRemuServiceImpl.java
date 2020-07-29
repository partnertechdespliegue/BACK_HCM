package com.mitocode.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.PlanillaHistoricaDsctRemu;
import com.mitocode.model.PlanillaHistorico;
import com.mitocode.repo.PlanillaHistoricaDsctRemuRepo;
import com.mitocode.service.PlanillaHistoricaDsctRemuService;

@Service
public class PlanillaHistoricaDsctRemuServiceImpl implements PlanillaHistoricaDsctRemuService {

	@Autowired
	PlanillaHistoricaDsctRemuRepo repo;

	private static final Logger LOG = LoggerFactory.getLogger(Exception.class);

	@Override
	public PlanillaHistoricaDsctRemu registrar(PlanillaHistoricaDsctRemu obj) {
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " registrar. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public PlanillaHistoricaDsctRemu modificar(PlanillaHistoricaDsctRemu obj) {
		try {
			return repo.save(obj);
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " modificar. ERROR : " + e.getMessage());
			throw e;
		}
	}

	@Override
	public PlanillaHistoricaDsctRemu leer(Integer id) {
		
		return null;
	}

	@Override
	public List<PlanillaHistoricaDsctRemu> listar() {
		
		return null;
	}

	@Override
	public Boolean eliminar(Integer id) {
		try {
			repo.deleteById(id);
			Boolean resp = repo.existsById(id);
			return resp;
		} catch (Exception e) {
			LOG.error(this.getClass().getSimpleName() + " eliminar. ERROR : " + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public List<PlanillaHistoricaDsctRemu> listarPorPlanHistorico(PlanillaHistorico planillaHistorico) {
		return repo.findByPlanillaHistorico(planillaHistorico);
	}

	@Override
	public List<PlanillaHistoricaDsctRemu> registrarLista(List<PlanillaHistoricaDsctRemu> lsphDsctRemu) {
		
		return repo.saveAll(lsphDsctRemu);
	}

}
