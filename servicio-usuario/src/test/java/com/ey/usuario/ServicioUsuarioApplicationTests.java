package com.ey.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ey.usuario.clases.TelefonoRegistro;
import com.ey.usuario.clases.UsuarioRegistro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class ServicioUsuarioApplicationTests {

	protected MockMvc mvc;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void crearUsuario() throws Exception {
	   String uri = "/registro";
	   UsuarioRegistro _usuario = new UsuarioRegistro();
	   _usuario.setName("Juan Rodriguez Test");
	   _usuario.setEmail("juan@rodriguezTest.org");
	   _usuario.setPassword("Test22");
	   List<TelefonoRegistro> _telefonos = new ArrayList<TelefonoRegistro>();
	   TelefonoRegistro _telefono = new TelefonoRegistro();
	   _telefono.setNumber("1234567");
	   _telefono.setCitycode("1");
	   _telefono.setContrycode("57");
	   _telefonos.add(_telefono);
	   _usuario.setPhones(_telefonos);
	   
	   String inputJson = mapToJson(_usuario);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   assertEquals(content, "Product is created successfully");
	}
	
	protected String mapToJson(Object obj) throws JsonProcessingException {
	      ObjectMapper objectMapper = new ObjectMapper();
	      return objectMapper.writeValueAsString(obj);
	   }
}
