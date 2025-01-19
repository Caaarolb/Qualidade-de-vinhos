package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String autor;
    private String titulo;
    private String idioma;  // Renomeado de 'linguagem' para 'idioma'
    private Integer totalDownloads;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Autor> autores;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.autor = dadosLivro.autores().getFirst().nomeAutor();
        this.titulo = dadosLivro.titulo();
        this.idioma = dadosLivro.idioma().toString();  // Usando 'idioma' em vez de 'linguagem'
        this.totalDownloads = dadosLivro.totalDownloads();
        this.autores = new ArrayList<>();

        for (DadosAutor dadosAutor : dadosLivro.autores()) {
            Autor autor = new Autor(dadosAutor);
            autor.setLivro(this);
            this.autores.add(autor);
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;  // Nome correto do método
    }

    public void setIdioma(String idioma) {  // Método para 'idioma'
        this.idioma = idioma;
    }

    public Integer getTotalDownloads() {
        return totalDownloads;  // Nome correto do método
    }

    public void setTotalDownloads(Integer totalDownloads) {  // Método para 'totalDownloads'
        this.totalDownloads = totalDownloads;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", totalDownloads=" + totalDownloads +
                ", autores=" + autores +
                '}';
    }
}
