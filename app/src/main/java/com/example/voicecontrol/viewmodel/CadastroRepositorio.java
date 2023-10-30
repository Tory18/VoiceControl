package com.example.voicecontrol.viewmodel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.voicecontrol.model.Cadastro;
import com.example.voicecontrol.viewmodel.DBHelper;
import com.example.voicecontrol.model.Cadastro;

import java.util.ArrayList;
import java.util.List;

public class CadastroRepositorio {
    private DBHelper helper;
    public CadastroRepositorio(Context context){
        helper = new DBHelper(context);
    }
    public long inserir(Cadastro cadastro){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUNA_NOME_USUARIO, cadastro.getNome());
        valores.put(DBHelper.COLUNA_NOMEASSISTENTE_USUARIO, cadastro.getNomeAssistente());
        long id = db.insert(DBHelper.TABELA_USUARIO, null, valores);
        db.close();
        return id;

    }
    public List<Cadastro> buscarTodosUsuarios(){
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM "+DBHelper.TABELA_USUARIO+" ORDER BY "+DBHelper.COLUNA_NOME_USUARIO;
        Cursor cursor = db.rawQuery(sql,null);

        List<Cadastro> cadastros = new ArrayList<>();

        while(cursor.moveToNext()){
            String nome = cursor.getString(0);
            String nomeAssistente = cursor.getString(1);
            Cadastro cadastro = new Cadastro(nome, nomeAssistente);
            cadastros.add(cadastro);
        }
        cursor.close();
        db.close();
        return cadastros;
    }
}