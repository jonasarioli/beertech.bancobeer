package com.beertech.banco.infrastructure.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.beertech.banco.domain.service.ContaService;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = OperacaoController.class)
@ActiveProfiles("test")
class OperacaoControllerTest {

	@Autowired                           
    private MockMvc mockMvc;
	
	@MockBean
	ContaService bancoService;
	
	@Test
	void getSaldo() throws Exception {		
		given(bancoService.saldo("hash")).willReturn(new BigDecimal(10.0));
		MvcResult andReturn = this.mockMvc.perform(get("/banco//saldo/hash"))
        .andExpect(status().isOk()).andReturn();
		String contentAsString = andReturn.getResponse().getContentAsString();
		assertEquals("10", contentAsString);
	}

}
