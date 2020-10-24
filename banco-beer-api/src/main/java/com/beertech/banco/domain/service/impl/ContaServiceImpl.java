package com.beertech.banco.domain.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.beertech.banco.domain.exception.ContaException;
import com.beertech.banco.domain.model.Conta;
import com.beertech.banco.domain.model.EPerfil;
import com.beertech.banco.domain.model.Profile;
import com.beertech.banco.domain.repository.ContaRepository;
import com.beertech.banco.domain.service.ContaService;
import com.beertech.banco.domain.service.ProfileService;

public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;

    @Autowired
    private ProfileService profileService;

    public ContaServiceImpl(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Conta criarConta(Conta conta, EPerfil perfil) {
        Optional<Conta> findByHash = contaRepository.findByHash(conta.getHash());
        if (findByHash.isPresent())
            throw new ContaException("Ja existe uma conta com esse valor de Hash");
        Optional<Profile> profileUser = profileService.findByName(perfil.name());
        if (!profileUser.isPresent()) {
            throw new ContaException("Não existe um profile com nome: " + perfil.name());
        }
        conta.addProfile(profileUser.get());
        return contaRepository.save(conta);
    }

    @Override
    public BigDecimal saldo(String hash) {
        Optional<Conta> conta = contaRepository.findByHash(hash);
        if (!conta.isPresent()) {
            throw new ContaException("O hash da conta não existe!");
        }
        return conta.get().getSaldo();
    }

    @Override
    public void atualizaConta(Conta conta) {
        contaRepository.save(conta);
    }

    @Override
    public Page<Conta> listaTodasAsContas(Pageable page) {
        return contaRepository.findAll(page);
    }

    @Override
    public Page<Conta> listaTodasAsContasUsuarios(Pageable page) {
        return new PageImpl<Conta>(contaRepository.findAll(page).stream()
                .filter(c -> !c.getProfiles().iterator().next().getName().equals("ROLE_ADMIN"))
                .collect(Collectors.toList()));
    }

    @Override
    public Conta contaPeloId(Long id) {
        Optional<Conta> findById = contaRepository.findById(id);
        if (!findById.isPresent()) {
            throw new ContaException("O id da conta não existe!");
        }
        return findById.get();
    }

    @Override
    public Conta contaPeloHash(String hash) {
        Optional<Conta> conta = contaRepository.findByHash(hash);
        if (!conta.isPresent()) {
            throw new ContaException("O id da conta não existe!");
        }
        return conta.get();
    }

    @Override
    public Conta contaPeloEmail(String email) {
        Optional<Conta> conta = contaRepository.findByEmail(email);
        if (!conta.isPresent()) {
            throw new ContaException("O id da conta não existe!");
        }
        return conta.get();
    }

}
