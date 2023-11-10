package com.example.voicecontrol.model;

public class App {
    private int id;
    private String nome;
    private String intent;

    public App(int id, String nome, String intent) {
        this.id = id;
        this.nome = nome;
        this.intent = intent;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }


}
