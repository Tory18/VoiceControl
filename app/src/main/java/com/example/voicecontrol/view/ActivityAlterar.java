package com.example.voicecontrol.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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

public class ActivityAlterar extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private EditText nUsuario;
    private EditText nAssistente;
    private Button salvar;
    private InstrucoesSintentizadas mensagensSintetizador;
    private ControleTTS controleTTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSIONS_REQUEST_RECORD_AUDIO);
        }

        controleTTS = new ControleTTS(this);
        controleTTS.speak("Olá caro usuário, nesta tela voce poderá alterar suas informações");

        mensagensSintetizador = new InstrucoesSintentizadas();
        nUsuario = findViewById(R.id.nome_usuario);
        nAssistente = findViewById(R.id.nome_assistente);
        salvar = findViewById(R.id.btnSalvar);

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

        salvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alterarUsuario();
                Intent it = new Intent(ActivityAlterar.this, ActivityCadastro.class);
                startActivity(it);
                finish();



            }
        });
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
                    controleTTS.speak("Seu novo nome de Usuario: " + tUsuario + "Certo? ");
                    if (tUsuario.equalsIgnoreCase("Sim")) {
                        iniciarReconhecimentoUsuario();
                        nAssistente.requestFocus();
                    }

                } else if (nAssistente.isFocused()) {
                    nAssistente.setText(resultado);
                    nAssistente.setSelection(nAssistente.getText().length());

                    String tAssistente = nAssistente.getText().toString();
                    controleTTS.speak("Seu novo nome de Assistente: " + tAssistente + "Certo?");
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
    private void alterarUsuario() {
        String nome = nUsuario.getText().toString();
        String nomeA = nAssistente.getText().toString();

        Cadastro cadastro = new Cadastro(nome, nomeA);
        ControleCadastro controleCadastro = ControleCadastro.getInstancia(ActivityAlterar.this);
        if (controleCadastro.atualizarLista()) {
            Toast.makeText(this, "Dados alterados com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sem sucesso", Toast.LENGTH_SHORT).show();
        }
    }
}