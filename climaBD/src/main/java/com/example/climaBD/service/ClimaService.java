package com.example.climaBD.service;

import com.example.climaBD.model.ClimaEntity;
import com.example.climaBD.repository.ClimaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClimaService {

    @Autowired
    private ClimaRepository climaRepository;

    public String preverTempoESalvar() {
        String apiURL = "https://apiadvisor.climatempo.com.br/api/v1/anl/synoptic/locale/BR?token=9fe25332679ebce952fdd9f7f9a83c3e";
        String dadosMeteorologicos = "";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiURL, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            dadosMeteorologicos = responseEntity.getBody();
            List<ClimaEntity> climaEntities = extrairDadosEDisponibilizar(dadosMeteorologicos);
            salvarDados(climaEntities);
        } else {
            dadosMeteorologicos =  "Falha ao obter dados meteorológicos. Código de status " + responseEntity.getStatusCode();
        }

        return dadosMeteorologicos;
    }

    private List<ClimaEntity> extrairDadosEDisponibilizar(String dadosMeteorologicos) {
        List<ClimaEntity> climaEntities = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(dadosMeteorologicos);
            if (root.isArray()) {
                for (JsonNode node : root) {
                    ClimaEntity climaEntity = new ClimaEntity();
                    climaEntity.setCountry(node.get("country").asText());
                    climaEntity.setDate(node.get("date").asText());
                    climaEntity.setText(node.get("text").asText());
                    climaEntities.add(climaEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return climaEntities;
    }

    private void salvarDados(List<ClimaEntity> climaEntities) {
        climaRepository.saveAll(climaEntities);
    }

    public List<ClimaEntity> obterTodos(){
        return climaRepository.findAll();
    }

    public ClimaEntity obterPorId(String id) {
        return climaRepository.findById(id).orElse(null);
    }

    public ClimaEntity inserir(ClimaEntity clima){
        return climaRepository.save(clima);
    }

    public ClimaEntity atualizar(String id, ClimaEntity newClima){
        ClimaEntity existingClima = climaRepository.findById(id).orElse(null);

        if(existingClima != null){
            existingClima.setDate(newClima.getDate());
            existingClima.setCountry(newClima.getCountry());
            existingClima.setText(newClima.getText());
            return climaRepository.save(existingClima);
        } else {
            return null;
        }
    }

    public void excluir(String id){
        climaRepository.deleteById(id);
    }

}
