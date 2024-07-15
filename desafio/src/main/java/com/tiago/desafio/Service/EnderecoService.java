package com.tiago.desafio.Service;


import com.tiago.desafio.Entity.Endereco.Endereco;
import com.tiago.desafio.Repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    public Endereco create(Endereco obj){
        String cepValidationResult = CepService.CepResponse(obj.getCep());
        obj.setCepResponse(cepValidationResult);
        return repository.save(obj);
    }

    public void delete(UUID id){
        Optional<Endereco> obj = repository.findById(id);
        repository.deleteById(id);
    }
    public Endereco getId(UUID id){
        Optional<Endereco> obj = repository.findById(id);
        return obj.get();
    }
    public List<Endereco> getAll(){
        return repository.findAll();
    }

    public Endereco update(Endereco obj) {
        Optional<Endereco> optionalEndereco = repository.findById(obj.getId());
        if (optionalEndereco.isPresent()) {
            Endereco newObj = optionalEndereco.get();
            updateEndereco(newObj, obj);
            return repository.save(newObj);
        } else {
            // Lance uma exceção ou trate o caso em que o Endereco não é encontrado
            throw new EntityNotFoundException("Endereco não encontrado com o ID: " + obj.getId());
        }
    }
    private void updateEndereco(Endereco newObj, Endereco obj) {
        newObj.setNumero(obj.getNumero());
        newObj.setLogradouro(obj.getLogradouro());
        newObj.setComplemento(obj.getComplemento());
        newObj.setBairro(obj.getBairro());
        newObj.setCidade(obj.getCidade());
        newObj.setEstado(obj.getEstado());
        newObj.setCep(obj.getCep());
        String cepValidationResult = CepService.CepResponse(obj.getCep());
        newObj.setCepResponse(cepValidationResult);
    }
}
