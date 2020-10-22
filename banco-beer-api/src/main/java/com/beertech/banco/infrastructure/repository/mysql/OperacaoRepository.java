package com.beertech.banco.infrastructure.repository.mysql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.TipoOperacao;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlOperacao;

public interface OperacaoRepository extends JpaRepository<MySqlOperacao, Long> {

	Page<MySqlOperacao> findByConta_Id(Long contaId, Pageable page);
	Page<MySqlOperacao> findByTipoAndConta_Id(TipoOperacao tipo, Long contaId, Pageable page);
}
