package com.tiago.desafio.Entity.Endereco;

import com.tiago.desafio.Entity.Cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "Numero é obrigatório")
    private String numero;

    //pode ser blank, não é obrigatório
    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatório")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    //method
    private String cepResponse;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente clienteId;


    public Endereco(String numero, String bairro, String cep){
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
    }
}
