package com.mitocode.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Contrato;
import com.mitocode.model.PdoAno;
import com.mitocode.model.PdoMes;
import com.mitocode.model.PlanillaHistorico;

public interface PlanillaHistoricaRepo extends JpaRepository<PlanillaHistorico, Integer>{
		
	@Query(value="select  * from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes and id_contrato = :idContrato",nativeQuery = true)
	PlanillaHistorico obtenerPlanilla(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes , @Param("idContrato") Integer idContrato);
	
	PlanillaHistorico findByPdoAnoAndPdoMesAndContrato(PdoAno pdoAno, PdoMes pdoMes, Contrato contrato);
	
	@Query(value="select * from planilla_historico where id_contrato =:idContrato order by id_pdo_ano DESC, id_pdo_mes DESC FETCH NEXT 5 ROWS ONLY ",nativeQuery = true)
	List<PlanillaHistorico> listarBoletas(@Param("idContrato") Integer idContrato); 
	
	@Query(value="select * from planilla_historico where id_contrato =:idContrato and rem_grati !=0 and id_pdo_ano = :idAno and id_pdo_mes =:idMes",nativeQuery=true)
	PlanillaHistorico obtenerPlanGrati(@Param("idContrato") Integer idContrato,@Param("idAno") Integer idAno, @Param("idMes") Integer idMes);

//	@Query(value ="select sum(:columnaBD) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
//	Double sumColumnaBD(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes, @Param("columnaBD") String columnaBD);
	
	@Query(value ="select sum(rem_dia_ferdo_labo) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumRemDiaFerLab(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);	
	
	@Query(value ="select sum(rem_ho_ext25) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumRemHoExt25(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(rem_ho_ext35)rem_ho_ext35 from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumRemHoExt35(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(rem_grati) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumRemGrati(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(rem_comisiones) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumRemComi(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(rem_vaca_vend) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumRemVacaVend(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);

	@Query(value ="select sum(rem_jor_norm) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumRemJorNorm(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(asig_fam) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumAsigFam(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(rem_dia_vaca) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumRemDiaVaca(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(bonif29351) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumBonif29351(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(movilidad) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumMovilidad(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(rem_cts) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumRemCts(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(tot_aport_afp) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumTotAporAfp(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(dsct_onp) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumDsctOnp(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(dsct_tardanza) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumDsctTardan(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(dsct_5ta_cat) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumDsct5taCat(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(essalud_vida) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumEssaludVida(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(aport_eps) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumAportEps(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(mon_adela) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumMontAdelanto(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(apor_essalud) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumAportEssalud(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
	
	@Query(value ="select sum(sctr) from planilla_historico where id_pdo_ano = :idPdoAno and id_pdo_mes = :idPdoMes",nativeQuery=true)
	Double sumSctr(@Param("idPdoAno") Integer idAno, @Param("idPdoMes") Integer idMes);
}
