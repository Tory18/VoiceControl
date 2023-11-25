package com.example.voicecontrol.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.voicecontrol.R;
import com.example.voicecontrol.model.PermissoesUsuario;
import com.example.voicecontrol.util.ControleTTS;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ControleTTS controleTTS;
    private Button btnCadastro;

    private static final int REQUEST_CODE = 5;

    private static final int PERMISSION_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solicitarPermissao();

        btnCadastro = findViewById(R.id.bnt_cadastro);

        controleTTS = new ControleTTS(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                // Lógica para configurar o TextToSpeech, se necessário
                confirmacao();
            } else {
                Toast.makeText(MainActivity.this, "Erro ao inicializar TextToSpeech.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = btnCadastro.getContentDescription().toString();
                controleTTS.speak(text);
                Intent cadIntent = new Intent(MainActivity.this, Home.class);
                startActivity(cadIntent);
            }
        });
    }

    private void solicitarPermissao() {
        String[] permissoesUsuario = PermissoesUsuario.Lista_de_Permissoes;
        if (controleTTS != null && PermissoesUsuario.TodasPermissoesConcedidas(this, permissoesUsuario)) {
            controleTTS.speak(getString(R.string.saudacao_cadastro));
        } else {
            PermissoesUsuario.SolicitarPermissoesFaltantes(this, permissoesUsuario, PERMISSION_REQUEST_CODE);
            controleTTS.speak(getString(R.string.pedir_permissoes));
        }
    }

    private void confirmacao() {
        if (controleTTS != null) {
            SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String nome = preferences.getString("user_name", "");

            if (!nome.isEmpty()) {
                controleTTS.speak(getString(R.string.bem_vindo, nome));
                recVoz();
            }
        } else {
            Toast.makeText(this, "ControleTTS não inicializado corretamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void recVoz() {
        Intent it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
        controleTTS.speak("Fale o seu nome...");

        it.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga seu nome...");

        try {
            startActivityForResult(it, REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> resultados = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (resultados != null && !resultados.isEmpty()) {
                String resultado = resultados.get(0);
                if (resultado.equalsIgnoreCase("sim")) {
                    controleTTS.speak("Você está sendo redirecionado para a tela inicial");
                    Intent it = new Intent(MainActivity.this, Home.class);
                } else if (resultado.equalsIgnoreCase("não")) {
                    controleTTS.speak("ERRO");
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        controleTTS.speak(getString(R.string.saudacao));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controleTTS.shutdown();
    }
}
