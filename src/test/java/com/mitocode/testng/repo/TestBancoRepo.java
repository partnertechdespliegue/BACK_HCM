package com.mitocode.testng.repo;

import static org.testng.Assert.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.mitocode.model.Banco;
import com.mitocode.model.Empresa;
import com.mitocode.repo.BancoRepo;
import com.mitocode.util.DataDuroComplementos;

@SpringBootTest
public class TestBancoRepo extends AbstractTestNGSpringContextTests{

	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	BancoRepo banco_repo;
	
	@Test
	@Transactional
	public void testFindAllBanco() throws Exception {
		
		List<Banco> lsbanco = banco_repo.findAll();
		assertTrue(lsbanco.size() >= 0);
	}

}
