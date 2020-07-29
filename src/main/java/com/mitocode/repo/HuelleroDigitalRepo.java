package com.mitocode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Empresa;
import com.mitocode.model.HuelleroDigital;

public interface HuelleroDigitalRepo extends JpaRepository<HuelleroDigital, Integer>{
	
	List<HuelleroDigital> findBySipPublicaAndEmpresa (String sipPublica, Empresa empresa);
	HuelleroDigital findByIidHuelleroDigital(Integer id);
	
	@Query(value="SELECT * FROM huellero_digital where sip_publica = :ipPublica and iid_empresa = :idEmpresa \r\n" + 
			"order by iid_huellero_digital desc;", nativeQuery = true)
	List<HuelleroDigital> encontrarIpPublica(@Param("ipPublica") String ipPublica, @Param("idEmpresa") Integer idEmpresa);
}
