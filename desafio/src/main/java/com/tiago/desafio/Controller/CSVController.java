package com.tiago.desafio.Controller;


import com.tiago.desafio.Service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/csv")
public class CSVController {
    @Autowired
    private CSVService csvService;


    @PostMapping("/import")
    public ResponseEntity<String> importCSV() {
        try {
            csvService.lerCSV("src/main/resources/data/clientes_enderecos.csv");

            return ResponseEntity.ok("Dados importados com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao importar dados: " + e.getMessage());
        }
    }

}

