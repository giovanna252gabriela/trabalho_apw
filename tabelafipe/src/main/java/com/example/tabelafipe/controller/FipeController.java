package com.example.tabelafipe.controller;

import com.example.tabelafipe.model.FipeEntity;
import com.example.tabelafipe.service.FipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("marcas")
public class FipeController {
    @Autowired
    private FipeService fipeService;

    @GetMapping
    public String consultarMarcas(){
        return fipeService.consultarMarcasESalvar();
    }

    @GetMapping("/todos")
    public List<FipeEntity> obterTodos(){
        return fipeService.obterTodos();
    }

    @PostMapping
    public FipeEntity inserir(@RequestBody FipeEntity clima){
        return fipeService.inserir(clima);
    }

    @PutMapping("{id}")
    public FipeEntity atualizar(@PathVariable String id, @RequestBody FipeEntity fipeEntity){
        return fipeService.atualizar(id, fipeEntity);
    }

    @DeleteMapping("{id}")
    public void excluir(@PathVariable String id){
        fipeService.excluir(id);
    }




}
