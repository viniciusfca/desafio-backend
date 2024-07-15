package com.tiago.desafio.Controller;

import com.tiago.desafio.Entity.Endereco.Endereco;
import com.tiago.desafio.Service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService service;


    @PostMapping
    public ResponseEntity<Endereco> create(@Valid @RequestBody Endereco obj){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Endereco> getId(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.getId(id));
    }
    @GetMapping
    public ResponseEntity<List<Endereco>> getAll(){
        return ResponseEntity.ok().body(service.getAll());
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Endereco> update(@PathVariable UUID id, @Valid @RequestBody Endereco obj){
        obj.setId(id);
        return ResponseEntity.ok().body(service.update(obj));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleValidationException(MethodArgumentNotValidException ex){
        return ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage()
                ));
    }
}
