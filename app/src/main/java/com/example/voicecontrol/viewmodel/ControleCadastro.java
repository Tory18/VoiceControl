package com.example.voicecontrol.viewmodel;
import android.content.Context;

import com.example.voicecontrol.model.Cadastro;

import java.util.ArrayList;
import java.util.List;

public class ControleCadastro {
    private final List<Cadastro> cadastros;
    private Context context;
    private static ControleCadastro instancia = null;

    private ControleCadastro(Context context){
        this.context = context;
        cadastros = new ArrayList<>();
    }
    public static ControleCadastro getInstancia(Context context){
        if(instancia == null)
            instancia = new ControleCadastro(context);
        return instancia;
    }
    public boolean cadastrar(Cadastro cadastro){
        CadastroRepositorio pessoaRepositorio = new CadastroRepositorio(context);
        long resultado = pessoaRepositorio.inserir(cadastro);
        if (resultado != -1)
            return true;
        else
            return false;

    }
    public List<Cadastro> buscarTodos(){
        CadastroRepositorio cadastroRepositorio = new CadastroRepositorio(context);
        List<Cadastro> cadastros = cadastroRepositorio.buscarTodosUsuarios();
        return cadastros;
    }
    public Cadastro buscarPorPosicao(int posicao){
        return cadastros.get(posicao);
    }
    public boolean atualizarLista(){
        CadastroRepositorio cadastroRepositorio = new CadastroRepositorio(context);
        return cadastroRepositorio.alterar((Cadastro) cadastros);
    }

}