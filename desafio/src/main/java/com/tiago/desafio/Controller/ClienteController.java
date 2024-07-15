package com.tiago.desafio.Controller;

import com.tiago.desafio.Entity.Cliente;
import com.tiago.desafio.Service.ClienteService;
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
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente obj){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(obj));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/{cpf}")
    public ResponseEntity<Cliente> getCpf(@PathVariable String cpf){
        return ResponseEntity.ok().body(service.getCpf(cpf));
    }
    @GetMapping
    public ResponseEntity<List<Cliente>> getAll(){
        return ResponseEntity.ok().body(service.getAll());
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente> update(@PathVariable UUID id,  @RequestBody @Valid Cliente obj){
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