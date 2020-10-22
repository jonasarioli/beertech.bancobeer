package com.beertech.banco.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.TipoOperacao;

public interface OperacaoRepository {
	Page<Operacao> getByContaId(Long contaId, Pageable page);
	Page<Operacao> getByContaIdAndTipo(Long contaId, TipoOperacao tipo, Pageable page);
	Operacao save(Operacao operacao);
	
}
