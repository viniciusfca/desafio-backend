package com.tiago.desafio.Entity;

import com.tiago.desafio.Repository.ClienteRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClienteTest {

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return isidoso true because age more than 60")
    void setIsIdosoSucess() {
        LocalDate dataNascimnto = LocalDate.of(1900,10,5);
        Cliente c1 = new Cliente("Tiago","123456789",dataNascimnto);

        c1.setDataNascimento(dataNascimnto);

        assertThat(c1).isNotNull();
        assertTrue(c1.getIsIdoso());
    }

    @Test
    @DisplayName("Should return isidoso false because age less than 60")
    void setIsIdosoFail() {
        LocalDate dataNascimnto = LocalDate.of(1995,10,5);
        Cliente c1 = new Cliente("Tiago","123456789",dataNascimnto);
        this.createCliente("Tiago","123456789",dataNascimnto);

        c1.setDataNascimento(dataNascimnto);

        assertThat(c1).isNotNull();
        assertFalse(c1.getIsIdoso());
    }

    private Cliente createCliente(String nome, String cpf, LocalDate dataNascimento){
        Cliente newCliente = new Cliente(nome, cpf, dataNascimento);
        this.entityManager.persist(newCliente);
        return newCliente;
    }
}