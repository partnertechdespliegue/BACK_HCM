package com.mitocode.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import com.mitocode.dto.PostulanteDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Postulante;
import com.mitocode.service.PostulanteService;
import com.mitocode.util.DataDuroComplementos;

import org.mockito.Mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostulanteControllerTest {
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@InjectMocks
	PostulanteController postulanteController;
	
	@Mock
	PostulanteService posService;
	
	@Mock
	BindingResult result;

	@Test
	public void testRegistrar() throws Exception {
		PostulanteDTO posDTO = data.nuevoPostulanteDTO();
		Postulante resp = posDTO.getPostulante();
		resp.setId_postulante(1);
		when(posService.registrar(posDTO.getPostulante())).thenReturn(resp);
		ResponseWrapper respWrapper = postulanteController.registrar(posDTO, result);
		
		Integer uno=1;
		assertEquals(respWrapper.getEstado(), uno);
	}
	
//	@Test
//	public void testRegistrarError() throws Exception {
//		PostulanteDTO posDTO = data.nuevoPostulanteDTO();
//		posDTO.setTipoDoc(null);
//		Postulante resp = posDTO.getPostulante();
//		resp.setId_postulante(1);
//		when(posService.registrar(posDTO.getPostulante())).thenReturn(resp);
//		ResponseWrapper respWrapper = postulanteController.registrar(posDTO, result);
//		
//		Integer dos=2;
//		assertEquals(respWrapper.getEstado(), dos);
//	}

}
