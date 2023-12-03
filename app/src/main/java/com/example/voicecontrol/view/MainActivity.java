package com.example.voicecontrol.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.voicecontrol.R;
import com.example.voicecontrol.model.InstrucoesSintentizadas;
import com.example.voicecontrol.model.PermissoesUsuario;
import com.example.voicecontrol.util.ControleTTS;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ControleTTS controleTTS;
    private Button btnCadastro;
    private InstrucoesSintentizadas intrucoes;
    private static final String PREFS_NAME = "user_prefs";
    private static final String USER_NAME_KEY = "user_name";

    private static final int REQUEST_CODE = 5;
    private static final int PERMISSION_REQUEST_CODE = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solicitarPermissao();
        intrucoes = new InstrucoesSintentizadas();
        btnCadastro = findViewById(R.id.bnt_cadastro);
        controleTTS = new ControleTTS(this);

        for (String instruction : intrucoes.getTelaMain()) {
            controleTTS.speak(instruction);
            Log.w("Cont", "funcionou" +instruction);
        }

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = btnCadastro.getContentDescription().toString();
                if (controleTTS != null) {
                    controleTTS.speak(text);
                } else {
                    Log.e("MainActivity", "ControleTTS não inicializado corretamente.");
                }
                Intent irHome = new Intent(MainActivity.this, ActivityCadastro.class);
                startActivity(irHome);
            }
        });
    }
    private void solicitarPermissao() {
        String[] permissoesUsuario = PermissoesUsuario.Lista_de_Permissoes;
        if (controleTTS != null && PermissoesUsuario.TodasPermissoesConcedidas(this, permissoesUsuario)) {

        } else {
            PermissoesUsuario.SolicitarPermissoesFaltantes(this, permissoesUsuario, PERMISSION_REQUEST_CODE);

        }
    }
    private void recVoz() {
        Intent it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());

        try {
            startActivityForResult(it, REQUEST_CODE);
        } catch (Exception e) {
            Log.e("MainActivity", "Erro ao iniciar reconhecimento de voz: " + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> resultados = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (resultados != null && !resultados.isEmpty()) {
                String resultado = resultados.get(0);

                if (resultado.equalsIgnoreCase("sim")) {
                    confirmacao();
                    Intent it = new Intent(MainActivity.this, Home.class);
                    startActivity(it);
                } else if (resultado.equalsIgnoreCase("não")) {
                    //
                } else if (resultado.equalsIgnoreCase("cadastro")) {
                    //String aviso = "Voce esta indo para tel de cadastro";
                    //controleTTS.speak(aviso);
                    Intent it = new Intent(MainActivity.this, ActivityCadastro.class);
                    startActivity(it);
                }else {
                    controleTTS.speak("Comando não reconhecido. Por favor, tente novamente.");
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        recVoz();

        if (controleTTS == null) {
            controleTTS = new ControleTTS(this, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    controleTTS.speak("Bem-vindo à minha aplicação!");
                    confirmacao();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao inicializar TextToSpeech.", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Erro ao inicializar TextToSpeech. Status: " + status);
                }
            });
        }
    }
    private void confirmacao() {
        if (controleTTS != null) {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

            if (preferences.contains(USER_NAME_KEY)) {
                String nome = preferences.getString(USER_NAME_KEY, "");
                Log.d("ActivityCadastro", "Nome do usuário recuperado: " + nome);

                if (!nome.isEmpty()) {
                    controleTTS.speak("Bem-vindo de volta, " + nome + "!");
                    recVoz();
                    Log.d("ActivityCadastro", "Texto para Fala: Bem-vindo de volta, " + nome + "!");
                } else {
                    Log.e("ActivityCadastro", "O nome recuperado está vazio ou nulo.");
                }

            } else {
                Log.e("MainActivity", "As preferências não contêm a chave " + USER_NAME_KEY);
                controleTTS.speak("Erro ao recuperar o nome do usuário.");
            }
        } else {
            Toast.makeText(this, "ControleTTS não inicializado corretamente.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (controleTTS != null) {
            controleTTS.shutdown();
        }
    }
}