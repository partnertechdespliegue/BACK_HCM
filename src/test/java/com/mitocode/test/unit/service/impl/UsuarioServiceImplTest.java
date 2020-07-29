package com.mitocode.test.unit.service.impl;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mitocode.model.Perfil;
import com.mitocode.model.Usuario;
import com.mitocode.repo.UsuarioRepo;
import com.mitocode.service.impl.UsuarioServiceImpl;
import com.mitocode.util.DataDuroComplementos;

public class UsuarioServiceImplTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	UsuarioServiceImpl service;
	
	@Mock
	UsuarioRepo repo;


	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testLoadUserByUsernameNoEncontrado() throws Exception {
		
		Usuario user = data.nuevoUsuario();
		Perfil perfil =data.nuevoPerfil();
		user.setPerfil(perfil);
		when(repo.findByUsername(user.getUsername())).thenReturn(null);
		assertThrows(UsernameNotFoundException.class, ()->{
			service.loadUserByUsername(user.getUsername());
		});
		
	}
	
	@Test
	public void testLoadUserByUsernameEncontrado() throws Exception {
		
		Usuario user = data.nuevoUsuario();
		Perfil perfil =data.nuevoPerfil();
		user.setPerfil(perfil);
		when(repo.findByUsername(user.getUsername())).thenReturn(user);
		UserDetails resp = service.loadUserByUsername(user.getUsername());
		assertEquals(resp.getUsername(), user.getUsername());
		assertEquals(resp.getPassword(), user.getPassword());	
	}
}
