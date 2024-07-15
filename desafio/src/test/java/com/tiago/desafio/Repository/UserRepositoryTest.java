package com.tiago.desafio.Repository;


import com.tiago.desafio.Entity.User.User;
import com.tiago.desafio.Entity.User.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfully from DB")
    void findByLoginSuccess() {
        String login = "Tiago";
        User u1 = new User(login,"123456789",UserRole.ADMIN);
        this.createUser(login,"123456789",UserRole.ADMIN);

        var user = this.userRepository.findByLogin(login);

        assertThat(user).isNotNull();
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    @DisplayName("Should not get user from DB when not exists")
    void findByLoginFail() {
        String login = "Tiago";

        var user = this.userRepository.findByLogin(login);

        assertThat(user).isNull();
    }




    private User createUser(String login, String password, UserRole role){
        User newUser = new User(login, password, role);
        this.entityManager.persist(newUser);
        return newUser;
    }
}