package com.example.voicecontrol.view;
import android.Manifest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.voicecontrol.R;
import com.example.voicecontrol.model.Cadastro;
import com.example.voicecontrol.model.MensagensSintentizador;
import com.example.voicecontrol.util.ControleTTS;
import com.example.voicecontrol.util.SpeechToText;
import com.example.voicecontrol.view.fragment.HomeFragment;
import com.example.voicecontrol.viewmodel.ControleCadastro;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityCadastro extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private EditText nUsuario;
    private EditText nAssistente;
    private Button entrar;
    private MensagensSintentizador mensagensSintetizador;
    private ControleTTS controleTTS;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSIONS_REQUEST_RECORD_AUDIO);
        }

        controleTTS = new ControleTTS(this);
        controleTTS.speak("Bem-vindo caro usuário");

        mensagensSintetizador = new MensagensSintentizador();
        nUsuario = findViewById(R.id.nome_usuario);
        nAssistente = findViewById(R.id.nome_assistente);
        entrar = findViewById(R.id.button);

        nUsuario.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iniciarReconhecimentoUsuario();

                }
                return false;
            }
        });

        nAssistente.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iniciarReconhecimentoUsuario();
                }
                return false;
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //criarUsuario();
                //Intent it = new Intent(ActivityCadastro.this, .class);
                //startActivity(it);
                //finish();


                navegacaoFragment();
            }
        });
    }

    private void navegacaoFragment() {
        HomeFragment home = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Verifique se o ID do contêiner corresponde ao ID no layout XML da atividade
        transaction.replace(R.id._home, home);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void iniciarReconhecimentoUsuario() {

        Intent it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
        it.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale seu nome...");

        try {
            startActivityForResult(it, REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> resultados = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (resultados != null && !resultados.isEmpty()) {
                String resultado = resultados.get(0);

                if (nUsuario.isFocused()) {
                    nUsuario.setText(resultado);
                    nUsuario.setSelection(nUsuario.getText().length());

                    String tUsuario = nUsuario.getText().toString();
                    controleTTS.speak("Seu nome de Usuario: " + tUsuario + "Certo? ");
                    if (tUsuario.equalsIgnoreCase("Sim")) {
                        iniciarReconhecimentoUsuario();
                        nAssistente.requestFocus();
                    }

                } else if (nAssistente.isFocused()) {
                    nAssistente.setText(resultado);
                    nAssistente.setSelection(nAssistente.getText().length());

                    String tAssistente = nAssistente.getText().toString();
                    controleTTS.speak("Seu nome de Usuario: " + tAssistente + "Certo?");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controleTTS.shutdown();
    }
    /*private void criarUsuario() {
        long id = -1;
        String nome = nUsuario.getText().toString();
        String nomeA = nAssistente.getText().toString();

        Cadastro cadastro = new Cadastro(nome, nomeA);
        ControleCadastro controleCadastro = ControleCadastro.getInstancia(ActivityCadastro.this);
        if (controleCadastro.cadastrar(cadastro)) {
            Log.d("Gravacao", "Ok");
        } else {
            Log.d("Gravacao", "Sem sucesso");
            }
        }*/


}
