package com.example.tabelafipe.service;

import com.example.tabelafipe.model.FipeEntity;
import com.example.tabelafipe.repository.FipeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
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
public class FipeService {
    @Autowired
    private FipeRepository fipeRepository;

//    private RestTemplate restTemplate = new RestTemplate();

//    private String consultarURL(String apiURL) {
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiURL, String.class);
//        if (responseEntity.getStatusCode().is2xxSuccessful()) {
//            return responseEntity.getBody();
//        } else {
//            return null;
//        }
//    }

    public String consultarMarcasESalvar() {
        String apiURL ="https://parallelum.com.br/fipe/api/v1/carros/marcas";
        String dados = "";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiURL, String.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            dados = responseEntity.getBody();
            List<FipeEntity> fipeEntities = extrairDadosEDisponibilizar(dados);
            salvarMarcas(fipeEntities);
        } else {
            dados = "Falha ao obter dados";
        }

        return dados;

    }

    private List<FipeEntity> extrairDadosEDisponibilizar(String dadosMeteorologicos) {
        List<FipeEntity> fipeEntities = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(dadosMeteorologicos);
            if (root.isArray()) {
                for (JsonNode node : root) {
                    FipeEntity fipeEntity = new FipeEntity();
                    fipeEntity.setCodigo(node.get("codigo").asText());
                    fipeEntity.setNome(node.get("nome").asText());
                    fipeEntities.add(fipeEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fipeEntities;
    }

    private void salvarMarcas(List<FipeEntity> marcas) {
        fipeRepository.saveAll(marcas);
    }

    public List<FipeEntity> obterTodos(){
        return fipeRepository.findAll();
    }

    public FipeEntity inserir(FipeEntity fipeEntity){
        return fipeRepository.save(fipeEntity);
    }

    public FipeEntity atualizar(String id, FipeEntity newFipe){
        FipeEntity existingFipe = fipeRepository.findById(id).orElse(null);

        if(existingFipe != null){
            existingFipe.setNome(newFipe.getNome());
            existingFipe.setCodigo(newFipe.getCodigo());
            return fipeRepository.save(existingFipe);
        } else {
            return null;
        }
    }

    public void excluir(String id){
        fipeRepository.deleteById(id);
    }

}
