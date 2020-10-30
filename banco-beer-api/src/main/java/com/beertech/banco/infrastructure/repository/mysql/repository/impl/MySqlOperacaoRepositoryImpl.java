package com.beertech.banco.infrastructure.repository.mysql.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.beertech.banco.domain.model.Operacao;
import com.beertech.banco.domain.model.TipoOperacao;
import com.beertech.banco.domain.repository.OperacaoRepository;
import com.beertech.banco.infrastructure.repository.mysql.model.MySqlOperacao;

@Repository
public class MySqlOperacaoRepositoryImpl implements OperacaoRepository {

    private com.beertech.banco.infrastructure.repository.mysql.OperacaoRepository operacaoRepository;

    @Autowired
    public MySqlOperacaoRepositoryImpl(
            com.beertech.banco.infrastructure.repository.mysql.OperacaoRepository operacaoRepository) {
        this.operacaoRepository = operacaoRepository;
    }

    @Override
    public Page<Operacao> getByContaId(Long contaId, Pageable page) {
        Page<MySqlOperacao> findByContaId = operacaoRepository.findByConta_Id(contaId, page);
        return findByContaId.map(new MySqlOperacao()::toDomain);
    }

    @Override
    public Page<Operacao> getByContaIdAndTipo(Long contaId, TipoOperacao tipo, Pageable page) {
        Page<MySqlOperacao> findByContaId = operacaoRepository.findByTipoAndConta_Id(tipo, contaId, page);
        return findByContaId.map(new MySqlOperacao()::toDomain);
    }

    @Override
    public Operacao save(Operacao operacao) {
        return new MySqlOperacao().toDomain(operacaoRepository.save(new MySqlOperacao().fromDomain(operacao)));
    }

}
