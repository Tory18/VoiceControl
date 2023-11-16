package com.example.voicecontrol.model;
import java.util.ArrayList;

public class InstrucoesSintentizadas {
    private String Atenção [] = {"Tente de novo" + "Tem algo errado"};
    private String Erro[] = {"Erro!"};
    private String Acesso[] = {"Acesso permitido"};


    private String TelaCadastro[] = {"Agora, usuário, estamos na parte em que coletaremos suas informações. " +
            "Logo abaixo existem dois campos de texto, um para o nome de usuário e outro para o nome do assistente. " +
            "Além disso, há um botão para salvar."};


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
    public String[] getTelaCadastro() {
        ArrayList<String> instrucaoCadastro = new ArrayList<>();
        for (String mensagem : Acesso){
            instrucaoCadastro.add(mensagem);
        }
        return TelaCadastro;
    }


}