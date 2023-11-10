package com.example.voicecontrol.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.voicecontrol.R;
import com.example.voicecontrol.view.ActivityAlterar;
import com.example.voicecontrol.view.Listagem;


public class ConfiguracaoFragment extends Fragment {


    public ConfiguracaoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracao, container, false);
        Button btnSalvar = view.findViewById(R.id.btnSalvar);
        EditText altUsuario = view.findViewById(R.id.nome_usuario);
        EditText altAssitente = view.findViewById(R.id.nome_assistente);

        altUsuario.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iniciarReconhecimento();

                }
                return false;
            }

            private void iniciarReconhecimento() {


            }
        });

        altAssitente.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                }
                return false;
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alterarUsuario();
                //Intent it = new Intent(ActivityAlterar.this, Listagem.class);
                //startActivity(it);
                //finish();
            }
        });
        return view;
    }
}