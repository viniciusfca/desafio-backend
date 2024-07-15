package com.tiago.desafio.Entity;

import com.tiago.desafio.Entity.Endereco.Endereco;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "tb_cliente")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotNull(message = "Data é obrigatório")
    private LocalDate dataNascimento;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    private Boolean isIdoso;

    @OneToMany(mappedBy = "clienteId")
    private List<Endereco> enderecoId =new ArrayList<>();

    // Método para verificar se o cliente é idoso
    public int calculaIdade() {
        if (dataNascimento == null) {
            return 0;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        this.isIdoso = calculaIdade() >= 60;
    }
    public Cliente(String nome,String cpf,LocalDate dataNascimento){
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

}