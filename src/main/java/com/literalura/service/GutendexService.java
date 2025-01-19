package com.literalura.service;

import com.literalura.model.Livro;
import com.literalura.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GutendexService {

    private final LivroRepository livroRepository;

    public GutendexService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo;

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> resposta = restTemplate.getForObject(url, Map.class);

        if (resposta != null && resposta.containsKey("results")) {
            List<Map<String, Object>> resultados = (List<Map<String, Object>>) resposta.get("results");

            if (!resultados.isEmpty()) {
                Map<String, Object> primeiroLivro = resultados.get(0);

                // Mapeando os dados do JSON para o objeto Livro
                Livro livro = new Livro();
                livro.setTitulo((String) primeiroLivro.get("title"));
                livro.setAutor(obterAutor(primeiroLivro));
                livro.setIdioma(obterIdioma(primeiroLivro));
                livro.setTotalDownloads((Integer) primeiroLivro.get("download_count"));

                // Salvando no banco de dados
                livroRepository.save(livro);
                return livro;
            }
        }
        return null;
    }

    private String obterAutor(Map<String, Object> dadosDoLivro) {
        List<Map<String, Object>> autores = (List<Map<String, Object>>) dadosDoLivro.get("authors");
        if (autores != null && !autores.isEmpty()) {
            return (String) autores.get(0).get("name");
        }
        return "Desconhecido";
    }

    private String obterIdioma(Map<String, Object> dadosDoLivro) {
        List<String> idiomas = (List<String>) dadosDoLivro.get("languages");
        if (idiomas != null && !idiomas.isEmpty()) {
            return idiomas.get(0);
        }
        return "Desconhecido";
    }
}
