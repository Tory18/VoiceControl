package com.example.voicecontrol.model;

import java.util.ArrayList;

public class InstrucoesSintentizadas {

    private String[] telaCadastro = {
            "Agora, usuário, estamos na parte em que coletaremos suas informações. " +
                    "É importante para que você possa ser chamado pelo sintetizador." +
                    "Logo abaixo existem campos de texto onde você poderá inserir o seu nome de usuário. " +
                    "Além disso, há um botão para salvar."
    };

    private String[] telaMain = {
            "Bem-vindo ao VoiceControl, o aplicativo projetado para aprimorar sua experiência de navegação no dispositivo. " +
                    "Com o VoiceControl, você pode facilmente comandar e controlar seu dispositivo usando comandos de voz intuitivos, proporcionando uma forma mais conveniente e eficiente de interação. " +
                    "Explore todas as funcionalidades deste aplicativo inovador e simplifique sua experiência de uso diário." +
                    "Se deseja acessar a tela inicial com seu cadastro já realizado, por favor, diga: \"Sim\".\n" +
                    "Se ainda não possui um cadastro e deseja criar um, por favor, diga: \"Cadastro\".\n" +
                    "Se não deseja realizar nenhuma dessas ações, diga: \"Não\"."
    };

    public String[] getTelaCadastro() {
        return telaCadastro;
    }

    public String[] getTelaMain() {
        return telaMain;
    }
}
