package com.literalura.controller;

import com.literalura.model.Livro;
import com.literalura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private GutendexService gutendexService;

    @GetMapping("/buscar")
    public Livro buscarLivro(@RequestParam String titulo) {
        return gutendexService.buscarLivroPorTitulo(titulo);
    }
}
