package com.tiago.desafio.Repository;

import com.tiago.desafio.Entity.Cliente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get cliente from DB")
    void findByCpfSuccess() {
        String cpf = "14407258";
        Cliente c1 = new Cliente("Tiago",cpf,LocalDate.of(1995,10,5));
        this.createCliente("Tiago",cpf,LocalDate.of(1995,10,5));

        Optional <Cliente> cli = this.clienteRepository.findByCpf(cpf);

        assertThat(cli).isNotNull();
        assertThat(cli.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get cliente from DB when cliente not exists")
    void findByCpfFail() {
        String cpf = "14407258";

        Optional <Cliente> cli = this.clienteRepository.findByCpf(cpf);


        assertThat(cli.isEmpty()).isTrue();
    }


    private Cliente createCliente(String nome, String cpf, LocalDate dataNascimento){
        Cliente newCliente = new Cliente(nome, cpf, dataNascimento);
        this.entityManager.persist(newCliente);
        return newCliente;
    }

}