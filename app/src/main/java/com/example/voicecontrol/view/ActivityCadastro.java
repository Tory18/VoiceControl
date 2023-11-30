package com.example.voicecontrol.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.voicecontrol.model.InstrucoesSintentizadas;
import com.example.voicecontrol.util.ControleTTS;
import com.example.voicecontrol.viewmodel.ControleCadastro;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityCadastro extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private EditText nUsuario;
    private EditText nAssistente;
    private Button entrar;
    private InstrucoesSintentizadas instrucoes;
    private ControleTTS controleTTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        controleTTS = new ControleTTS(this);

        instrucoes = new InstrucoesSintentizadas();
        nUsuario = findViewById(R.id.nome_usuario);
        nAssistente = findViewById(R.id.nome_assistente);
        entrar = findViewById(R.id.button);

        for (String instruction : instrucoes.getTelaCadastro()) {
            controleTTS.speak(instruction);
            Log.w("Cont", "funcionou" +instruction);
        }

        nUsuario.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iniciarReconhecimentoNome();
                    nUsuario.requestFocus();

                }
                return false;
            }
        });

        nAssistente.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iniciarReconhecimentoAssistente();
                    nAssistente.requestFocus();
                }
                return false;
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarUsuario();
                SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user_name", nUsuario.getText().toString());
                editor.apply();
                Log.d("ActivityCadastro", "Nome do usuário salvo: " + nUsuario.getText().toString());


                Intent it = new Intent(ActivityCadastro.this, MainActivity.class);
                startActivity(it);
                finish();

            }
        });
    }
    private void criarUsuario() {
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
    }

    private void iniciarReconhecimentoNome() {

        Intent it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
        controleTTS.speak("Fale o seu nome...");

        it.putExtra(RecognizerIntent.EXTRA_PROMPT, "False seu nome...");

        try {
            startActivityForResult(it, REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void iniciarReconhecimentoAssistente() {

        Intent it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
        controleTTS.speak("Fale o nome de sua assistente...");
        it.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale o nome do assistente...");

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
                    resposta();

                    String tUsuario = nUsuario.getText().toString();
                    controleTTS.speak("Seu nome de Usuario: " + tUsuario + "Certo? ");


                } else if (nAssistente.isFocused()) {
                    nAssistente.setText(resultado);
                    nAssistente.setSelection(nAssistente.getText().length());
                    resposta();
                    //String tAssistente = nAssistente.getText().toString();
                    //controleTTS.speak("Seu nome de assistente: " + tAssistente + "Certo?");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void resposta(){

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (controleTTS != null) {
            controleTTS.shutdown();
        }    }
}
