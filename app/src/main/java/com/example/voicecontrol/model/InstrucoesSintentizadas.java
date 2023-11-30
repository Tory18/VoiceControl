package com.example.voicecontrol.model;
import java.util.ArrayList;

public class InstrucoesSintentizadas {
    private String Atencao[] = {"Tente de novo", "Tem algo errado"};
    private String Erro[] = {"Erro!"};
    private String Acesso[] = {"Acesso permitido"};

    private String TelaCadastro[] = {"Agora, usuário, estamos na parte em que coletaremos suas informações. " +
            "Logo abaixo existem dois campos de texto, um para o nome de usuário e outro para o nome do assistente. " +
            "Além disso, há um botão para salvar."};

    private String TelaMain[] = {"Caso queira ir para a tela home, diga: Sim. " +
            "Caso já tenha um cadastro, diga: Cadastro. " +
            "Caso não queira fazer nada disso, diga: Não."};
    private String Main[] = { "Bem vindo ao VoiceControl, " +
            "esse é um app de auxiliador para sua nevegegacao do seu dispositivo."};

    public String[] getTelaCadastro() {
        return TelaCadastro;
    }

    public String[] getTelaMain() {
        return TelaMain;
    }
    public String[] getMain() {
        return Main;
    }


    public ArrayList<String> getMensagemList() {
        ArrayList<String> listaCadastro = new ArrayList<>();
        for (String mensagem : TelaCadastro) {
            listaCadastro.add(mensagem);
        }
        return listaCadastro;
    }

    public ArrayList<String> getErroList() {
        ArrayList<String> listaErro = new ArrayList<>();
        for (String mensagem : Erro) {
            listaErro.add(mensagem);
        }
        return listaErro;
    }

    public ArrayList<String> getAtencaoList() {
        ArrayList<String> listaAtencao = new ArrayList<>();
        for (String mensagem : Atencao) {
            listaAtencao.add(mensagem);
        }
        return listaAtencao;
    }

    public ArrayList<String> getAcessoList() {
        ArrayList<String> listaAcesso = new ArrayList<>();
        for (String mensagem : Acesso) {
            listaAcesso.add(mensagem);
        }
        return listaAcesso;
    }
}
