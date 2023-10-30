package com.example.voicecontrol.model;

public class Cadastro {
    private String nome;
    private String nomeAssistente;

    public Cadastro(String nome, String nomeAssistente) {
        this.nome = nome;
        this.nomeAssistente = nomeAssistente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeAssistente() {
        return nomeAssistente;
    }

    public void setNomeAssistente(String nomeAssistente) {
        this.nomeAssistente = nomeAssistente;
    }
}
