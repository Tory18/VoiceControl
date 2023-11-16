package com.example.voicecontrol.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.voicecontrol.R;
import com.example.voicecontrol.util.ControleTTS;

public class MainActivity extends AppCompatActivity {
    private ControleTTS controleTTS;
    private Button btnCadastro;
    private View view;

    private static final int PERMISSION_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCadastro = findViewById(R.id.bnt_cadastro);
        view = findViewById(R.id.tela);
        controleTTS = new ControleTTS(this, status ->{
            if (status == ControleTTS.SUCESS) {
                // Lógica para configurar o TextToSpeech, se necessário
                confirmacao();
            } else {
                Toast.makeText(MainActivity.this, "Erro ao inicializar TextToSpeech.", Toast.LENGTH_SHORT).show();
            }
        };

    private void confirmacao() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userName = preferences.getString("user_name", "");

        if (!userName.isEmpty()) {
            controleTTS.speak("Bem-vindo, " + userName + "!", ControleTTS.QUEUE_FLUSH, null, null);
        }
    }
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


        @Override
        protected void onResume () {
            super.onResume();

        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            controleTTS.shutdown();
        }
    }

