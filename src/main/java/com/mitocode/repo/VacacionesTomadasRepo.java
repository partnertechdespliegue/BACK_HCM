package com.mitocode.repo;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mitocode.model.VacacionesTomadas;

public interface VacacionesTomadasRepo extends JpaRepository<VacacionesTomadas,Integer>{
	
	@Query(value = "SELECT vt.id_vaca_tomada, vt.fecha_ini, vt.fecha_fin, vt.tipo_fecha, vt.pdo_mes_ini, \r\n" + 
			"vt.pdo_mes_fin, vt.pdo_ano_ini, vt.pdo_ano_fin, vt.id_vacacion\r\n" + 
			"from vaca_toma vt inner join pdo_vacacion pv on pv.id_vacacion = vt.id_vacacion \r\n" + 
			"where (vt.pdo_ano_ini=:idpdoano and vt.pdo_mes_ini=:idpdomes) or (vt.pdo_ano_fin=:idpdoano and vt.pdo_mes_fin=:idpdomes) and pv.id_trabajador=:idtrabajador", nativeQuery=true)
	List<VacacionesTomadas> encontrarVacacionTomada(@Param("idtrabajador") Integer idTrabajador, @Param("idpdoano") Integer idPdoAno, @Param("idpdomes") Integer idPdoMes);

}
