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
        long id = db.insert(DBHelper.TABELA_USUARIO, null, valores);
        db.close();
        return id;

    }
    public boolean alterar(Cadastro cadastro){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUNA_NOME_USUARIO, cadastro.getNome());

        int linhas = db.update(DBHelper.TABELA_USUARIO, valores, DBHelper.COLUNA_NOME_USUARIO,
                new String[] {String.valueOf(cadastro.getNome())});
        db.close();
        return linhas > 0;
    }
    public List<Cadastro> buscarTodosUsuarios(){
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM "+DBHelper.TABELA_USUARIO+" ORDER BY "+DBHelper.COLUNA_NOME_USUARIO;
        Cursor cursor = db.rawQuery(sql,null);

        List<Cadastro> cadastros = new ArrayList<>();

        while(cursor.moveToNext()){
            String nome = cursor.getString(0);
            Cadastro cadastro = new Cadastro(nome);
            cadastros.add(cadastro);
        }
        cursor.close();
        db.close();
        return cadastros;
    }
}