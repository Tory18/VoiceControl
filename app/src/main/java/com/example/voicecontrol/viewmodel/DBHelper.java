package com.example.voicecontrol.viewmodel;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final String DB_NOME = "GestaoUser";
    private static final int VERSAO = 1;
    public static final String TABELA_USUARIO = "Usuario";
    public static final String COLUNA_NOME_USUARIO = "nome";
    public static final String COLUNA_NOMEASSISTENTE_USUARIO = "nomeAssistente";

    public DBHelper(Context context){
        super(context, DB_NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABELA_USUARIO +" ( "+
                COLUNA_NOME_USUARIO + " TEXT NOT NULL, "
                +COLUNA_NOMEASSISTENTE_USUARIO + " TEXT NOT NULL);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+TABELA_USUARIO+";";
        db.execSQL(sql);
        onCreate(db);

    }
}