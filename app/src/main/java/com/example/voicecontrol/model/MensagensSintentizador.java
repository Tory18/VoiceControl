package com.example.voicecontrol.model;
import java.util.ArrayList;

public class MensagensSintentizador{
    private String Atenção [] = {"Tente de novo" + "Tem algo errado"};
    private String Erro[] = {"Erro!"};
    private String Acesso[] = {"Acesso permitido"};
    private String TelaCadastro[ ] = {"Caro usuario, essa tela é a de cadastro onde podera colocar o nome que deseja ser chamado pelo seu assistente, e tambem podera colocar a foqeu que desej achamar a sua assistente"};

    public ArrayList<String> getMensagemList(){
        ArrayList<String> listaCadastro = new ArrayList<>();
        for (String mensagem : TelaCadastro){
            listaCadastro.add(mensagem);
        }
        return listaCadastro;
    }
    public ArrayList<String> getErroList(){
        ArrayList<String> listaErro = new ArrayList<>();
        for (String mensagem : Erro){
            listaErro.add(mensagem);
        }
        return listaErro;
    }

    public ArrayList<String>  getAtencaoList() {
        ArrayList<String> listaAtencao = new ArrayList<>();
        for (String mensagem : Atenção) {
            listaAtencao.add(mensagem);
        }
        return listaAtencao;
    }

    public ArrayList<String>  getAcessoList(){
        ArrayList<String> listaAcesso = new ArrayList<>();
        for (String mensagem : Acesso){
            listaAcesso.add(mensagem);
        }
        return listaAcesso;

    }

}