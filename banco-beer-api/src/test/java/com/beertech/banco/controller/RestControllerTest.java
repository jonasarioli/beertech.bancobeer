package com.beertech.banco.controller;

import com.beertech.banco.repository.OperacaoRepository;
import com.beertech.banco.service.BancoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import static org.hamcrest.Matchers.is;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		ContaController.class
})
public class RestControllerTest {

	//URL base para acesso desse controlador
	private final String BASE_URL = "/conta";

	//Instância do ObjectMapper para trabalhar com JSON
	private ObjectMapper objectMapper;

	//Controlador REST tratado por meio de injeção de dependências
	@Autowired
	private ContaController restController;

	@MockBean
	private BancoService bancoService;

	//Instância do MockMVC
	private MockMvc mockMvc;

	//Instância do mock repository
	@MockBean
	private OperacaoRepository mockRepository;

	@Before
	public void setUp() {
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders
				.standaloneSetup(restController)
				.build();
	}

	@Test
	public void buscarOperacaoDeposito() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("tipo", "DEPOSITO");
		jsonObject.put("valor", 10.0);

		mockMvc.perform(post(BASE_URL + "/operacao")
				.content(jsonObject.toString())
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void buscarSaldo() throws Exception {
		BigDecimal valorSaldo = new BigDecimal(30.0);

		when(bancoService.getSaldo()).thenReturn(valorSaldo);
		Integer saldoEsperado = 30;

		mockMvc.perform(get(BASE_URL + "/saldo"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.saldo", is(saldoEsperado)));
	}

	@Test
	public void buscarSaldo1() throws Exception {
		BigDecimal valorSaldo = new BigDecimal(30.5);

		when(bancoService.getSaldo()).thenReturn(valorSaldo);
		Double saldoEsperado = 30.5;

		mockMvc.perform(get(BASE_URL + "/saldo"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.saldo", is(saldoEsperado)));
	}
}
