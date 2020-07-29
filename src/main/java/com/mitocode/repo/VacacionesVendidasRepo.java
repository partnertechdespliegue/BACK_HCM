package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.VacacionesVendidas;

public interface VacacionesVendidasRepo extends JpaRepository<VacacionesVendidas,Integer>{

	@Query(value = "select vv.id_vacacion_vendida, vv.fecha_venta, vv.pdo_mes_venta, vv.pdo_ano_venta, vv.cnt_dias, vv.id_vacacion\r\n" + 
			"from vaca_vend vv inner join pdo_vacacion pv on pv.id_vacacion = vv.id_vacacion\r\n" + 
			"where pv.id_trabajador=:idtrabajador and vv.pdo_ano_venta=:idpdoano and vv.pdo_mes_venta=:idpdomes", nativeQuery=true)
	List<VacacionesVendidas> encontrarVacacionVendida(@Param("idtrabajador") Integer idTrabajador,@Param("idpdoano") Integer idPdoAno,@Param("idpdomes") Integer idPdoMes);
}
