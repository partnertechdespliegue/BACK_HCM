package com.mitocode.repo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Banco;
import com.mitocode.model.Empresa;
import com.mitocode.util.DataDuroComplementos;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class TestBancoRepo {

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	BancoRepo banco_repo;
	
	@Test
	@Transactional
	public void testFindAllBanco() throws Exception {
		
		List<Banco> lsbanco = banco_repo.findAll();
		assertEquals(0, lsbanco.size());
	}

}
