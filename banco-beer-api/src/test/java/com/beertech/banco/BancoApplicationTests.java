package com.beertech.banco;

import com.beertech.banco.entity.Operacao;
import com.beertech.banco.entity.TipoOperacao;
import com.beertech.banco.repository.OperacaoRepository;
import com.beertech.banco.service.BancoService;
import com.beertech.banco.service.impl.BancoServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BancoServiceImpl.class})
public class BancoApplicationTests  {

	@Autowired
	private BancoService bancoService;

	@MockBean
	private OperacaoRepository mockRepository;

	@Test
	public void buscarOperacaoDeposito() {
		Operacao operacao = new Operacao(new BigDecimal("40.0"), TipoOperacao.DEPOSITO);

		when(mockRepository.save(operacao)).thenReturn(operacao);
		Operacao operacaoRetorno = bancoService.salvaOperacao(operacao);

		assertEquals(operacao.getValor(), operacaoRetorno.getValor());
		assertEquals(operacao.getTipo(), operacaoRetorno.getTipo());
	}

	@Test
	public void buscarOperacaoSaque() {
		Operacao operacao = new Operacao(new BigDecimal("10.0"), TipoOperacao.SAQUE);

		when(mockRepository.save(operacao)).thenReturn(operacao);
		Operacao operacaoRetorno = bancoService.salvaOperacao(operacao);

		assertEquals(operacao.getValor(), operacaoRetorno.getValor());
		assertEquals(operacao.getTipo(), operacaoRetorno.getTipo());
	}

	@Test
	public void buscarSaldo() {
		List<Operacao> listaOperacao = new ArrayList();
		listaOperacao.add(new Operacao(new BigDecimal("20.0"), TipoOperacao.DEPOSITO));
		listaOperacao.add(new Operacao(new BigDecimal("30.0"), TipoOperacao.DEPOSITO));
		listaOperacao.add(new Operacao(new BigDecimal("10.0"), TipoOperacao.SAQUE));

		when(mockRepository.findAll()).thenReturn(listaOperacao);

		BigDecimal resultadoSaldoRetorno = bancoService.getSaldo();

		assertEquals(resultadoSaldoRetorno, new BigDecimal("40.0"));
	}
}
