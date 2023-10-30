package com.example.voicecontrol.model;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final List<Cadastro> cadastros;
    private  static Controller instancia = null;

    private Controller(){
        cadastros= new ArrayList<>();

    }
    public static Controller getInstance(){
        if (instancia == null)
            instancia = new Controller();
        return instancia;
    }
}
