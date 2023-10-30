package com.example.voicecontrol.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.voicecontrol.R;
import com.example.voicecontrol.viewmodel.Adapter;
import com.example.voicecontrol.viewmodel.ControleCadastro;

public class Listagem extends AppCompatActivity {
    private ListView listView;
    private ImageView imgFundo;
    private ImageView imgOpc;
    private ImageView imgCel;
    private ControleCadastro controleCadastro;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
        controleCadastro = ControleCadastro.getInstancia(this);

        listView = findViewById(R.id._lista);
        adapter = new Adapter(this);
        listView.setAdapter(adapter);

        imgFundo = findViewById(R.id.image_fundo);
        imgCel = findViewById(R.id.image_celular);
        imgOpc = findViewById(R.id.image_opções);
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.atualizarLista();
    }
}