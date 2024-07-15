package com.tiago.desafio.Service;

import com.tiago.desafio.Entity.Cliente;
import com.tiago.desafio.Repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente create(Cliente obj){
        return repository.save(obj);
    }
    public void delete(UUID id){
        Optional<Cliente> obj = repository.findById(id);
        repository.deleteById(id);
    }
    public Cliente getId(UUID id){
        Optional<Cliente> obj = repository.findById(id);
        return obj.get();
    }
    public Cliente getCpf(String cpf){
        Optional<Cliente> obj = repository.findByCpf(cpf);
        return obj.get();
    }
    public List<Cliente> getAll(){
        return repository.findAll();
    }
    public Cliente update(Cliente obj) {
        Optional<Cliente> optionalCliente = repository.findById(obj.getId());
        if (optionalCliente.isPresent()) {
            Cliente newObj = optionalCliente.get();
            updateCliente(newObj, obj);
            return repository.save(newObj);
        } else {
            // Lance uma exceção ou trate o caso em que o cliente não é encontrado
            throw new EntityNotFoundException("Cliente não encontrado com o ID: " + obj.getId());
        }
    }
    private void updateCliente(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setCpf(obj.getCpf());
        newObj.setEmail(obj.getEmail());
        newObj.setDataNascimento(obj.getDataNascimento());
    }
}
