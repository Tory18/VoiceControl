package com.example.voicecontrol.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.voicecontrol.R;
import com.example.voicecontrol.model.PermissoesUsuario;
import com.example.voicecontrol.util.ControleTTS;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ControleTTS controleTTS;
    private Button btnCadastro;

    private static final int PERMISSION_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solicitarPermissao();

        btnCadastro = findViewById(R.id.bnt_cadastro);

        /*controleTTS = new ControleTTS(this, status ->{
            if (status == TextToSpeech.SUCCESS) {
                // Lógica para configurar o TextToSpeech, se necessário
                confirmacao();
            } else {
                Toast.makeText(MainActivity.this, "Erro ao inicializar TextToSpeech.", Toast.LENGTH_SHORT).show();
            }
        });*/



        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String text = btnCadastro.getContentDescription().toString();
                // controleTTS.speak(text);
                Intent cadIntent = new Intent(MainActivity.this, ActivityCadastro.class);
                startActivity(cadIntent);
            }
        });
    }
    // ...
    private void solicitarPermissao() {
        String[] permissoesUsuario = PermissoesUsuario.Lista_de_Permissoes;
        if (PermissoesUsuario.TodasPermissoesConcedidas(this, permissoesUsuario)) {
            controleTTS.speak(getString(R.string.saudacao_cadastro), TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            PermissoesUsuario.SolicitarPermissoesFaltantes(this, permissoesUsuario, PERMISSION_REQUEST_CODE);
            controleTTS.speak(getString(R.string.pedir_permissoes), TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
    /*private void confirmacao() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userName = preferences.getString("user_name", "");

        if (!userName.isEmpty()) {
            controleTTS.speak(getString(R.string.bem_vindo, userName), TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        controleTTS.speak(getString(R.string.saudacao), TextToSpeech.QUEUE_FLUSH, null, null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controleTTS.shutdown();
    }
}
