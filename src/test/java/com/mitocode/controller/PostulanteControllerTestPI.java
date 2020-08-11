package com.mitocode.controller;

import static org.testng.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.testng.annotations.Test;

import com.mitocode.dto.PostulanteDTO;
import com.mitocode.dto.ResponseWrapper;
import com.mitocode.model.Postulante;
import com.mitocode.util.DataDuroComplementos;


@SpringBootTest
public class PostulanteControllerTestPI extends AbstractTestNGSpringContextTests {
	
	
	DataDuroComplementos data = new DataDuroComplementos();
	
	@Autowired
	PostulanteController posController;
	
//	@Autowired (required = false)
//	BindingResult result;
	BindingResult result = new BeanPropertyBindingResult(null, null);

  @Test
  public void registrarTest() throws Exception {
	  
	PostulanteDTO posDTO = data.nuevoPostulanteDTO();
		
    ResponseWrapper resp = posController.registrar(posDTO, result);
    Postulante pos = (Postulante) resp.getDefaultObj();
    
    assertEquals(posDTO.getPostulante(), pos);
    
    
  }
}
