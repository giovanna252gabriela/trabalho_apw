package com.example.climaBD.controller;

import com.example.climaBD.model.ClimaEntity;
import com.example.climaBD.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clima")
public class Controller {
    @Autowired
    private ClimaService climaService;

    @GetMapping
    public String preverTempo(){
        return climaService.preverTempoESalvar() + " Dados meteorológicos salvos no banco de dados";
    }

    @GetMapping("/{id}")
    public ClimaEntity obterPorId(@PathVariable String id) {
        return climaService.obterPorId(id);
    }

    @GetMapping("/todos")
    public List<ClimaEntity> obterTodos(){
        return climaService.obterTodos();
    }

    @PostMapping
    public ClimaEntity inserir(@RequestBody ClimaEntity clima){
        return climaService.inserir(clima);
    }

    @PutMapping("/{id}")
    public ClimaEntity atualizar(@PathVariable String id, @RequestBody ClimaEntity clima){
        return climaService.atualizar(id, clima);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable String id){
        climaService.excluir(id);
    }

}