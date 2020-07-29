package com.mitocode.test.unit.controller;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;

import com.mitocode.controller.UsuarioController;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.dto.UsuarioDTO;
import com.mitocode.exception.ExceptionResponse;
import com.mitocode.model.Perfil;
import com.mitocode.model.Usuario;
import com.mitocode.service.impl.UsuarioServiceImpl;
import com.mitocode.util.DataDuroComplementos;


public class TestUsuarioController {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	UsuarioController controller;
	
	@Mock
	UsuarioServiceImpl service;
	
	@Mock
	BCryptPasswordEncoder encode;
	
	@Mock
	BindingResult result;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testRegistrarUsuarioCorrecto() throws Exception {
		
		Usuario user = data.nuevoUsuario();
		Perfil perfil =data.nuevoPerfil();
		UsuarioDTO usuarioDTO  = new UsuarioDTO();
		usuarioDTO.setUsuario(user);
		usuarioDTO.setPerfil(perfil);
		user.setPerfil(perfil);
		when(service.registrar(user)).thenReturn(user);
		when(service.findbyUsername("junior")).thenReturn(null);
		when(encode.encode(user.getPassword())).thenReturn("encriptado");
		ResponseWrapper resp = controller.registrar(usuarioDTO,result);
		
		assertEquals((Usuario) resp.getDefaultObj(), user);
	}
	
	@Test
	public void testRegistrarUsuarioConErrores() throws Exception {
		
		Usuario user = data.nuevoUsuario();
		Perfil perfil =data.nuevoPerfil();
		UsuarioDTO usuarioDTO  = new UsuarioDTO();
		usuarioDTO.setUsuario(user);
		usuarioDTO.setPerfil(perfil);
		user.setPerfil(perfil);
		when(result.hasErrors()).thenReturn(true);
		ResponseWrapper resp = null;
		try {
		 resp = controller.registrar(usuarioDTO,result);
		}catch(ExceptionResponse e) {
			assertSame(e.getClass(), ExceptionResponse.class);
			assertEquals(resp,null);
		}
		
	}
	
	@Test
	public void testRegistrarUsuarioUsernameRepetido() throws Exception{
		Usuario user = data.nuevoUsuario();
		Perfil perfil =data.nuevoPerfil();
		UsuarioDTO usuarioDTO  = new UsuarioDTO();
		usuarioDTO.setUsuario(user);
		usuarioDTO.setPerfil(perfil);
		user.setPerfil(perfil);
		when(service.findbyUsername("junior")).thenReturn(new Usuario());
		ResponseWrapper resp = controller.registrar(usuarioDTO,result);
		assertEquals(2,(int) resp.getEstado());
		assertEquals("El nombre de usuario ya está en uso",resp.getMsg());
	}
	
	@Test
	public void testListarUsuarioCorrecto() throws Exception{
		
		List<Usuario> list_user = new ArrayList<>();
		list_user.add(data.nuevoUsuario());
		list_user.add(data.nuevoUsuario());
		list_user.add(data.nuevoUsuario());
		
		when(service.listar()).thenReturn(list_user);
		ResponseWrapper resp = controller.listar();
		assertTrue(resp.getAaData().size()>0);
	}
	
	@Test
	public void testUsuarioModificarContraseña() throws Exception{
		Usuario user = data.nuevoUsuario();
		Usuario modificado = user;
		Perfil perfil =data.nuevoPerfil();
		user.setPerfil(perfil);
		when(service.findbyUsername("junior")).thenReturn(user);
		when(encode.encode("12345")).thenReturn("encriptado");
		when(service.modificar(user)).thenReturn(modificado);
		ResponseWrapper resp = controller.modificar(user, result);
		assertEquals(((Usuario) resp.getDefaultObj()).getPassword(),"encriptado");
	}
	
	
	
}
