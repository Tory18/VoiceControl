package com.example.voicecontrol.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.CalendarContract;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.voicecontrol.R;
import com.example.voicecontrol.util.ControleTTS;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {
    Intent intent= null;
    ImageButton btn;
    ListView view;
    ControleTTS controleTTS;
    private static final int REQUEST_CODE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        controleTTS = new ControleTTS(this);
        view = findViewById(R.id._dynamic);
        btn = findViewById(R.id.btnFalar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarReconhecimento();
                Log.d("tag", "inciar");
                if (intent != null) {
                    startActivity(intent);
                }

            }

        });
    }

    private void iniciarReconhecimento() {
        Intent it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
        it.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale seu comando...");

        try {
            startActivityForResult(it, REQUEST_CODE);
        } catch (Exception e) {
            // Lide com a exceção, por exemplo, mostre um Toast com uma mensagem de erro
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<String> resultados = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String resultadoReconhecimento = resultados.get(0);
            String nomeApp = resultadoReconhecimento;

            if (nomeApp.equals("telefone")) {
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "));
                controleTTS.speak("Voce esta entrando no aplicativo telefone");

            } else if (nomeApp.equals("mapa")) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=sua_localizacao"));
                controleTTS.speak("Voce esta entrando no aplicativo mapa");

            } else if (nomeApp.equals("email")) {
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                controleTTS.speak("Voce esta entrando no aplicativo email");

            } else if (nomeApp.equals("agenda")) {
                intent = getPackageManager().getLaunchIntentForPackage("com.google.android.calendar");
                //intent = new Intent(Intent.ACTION_VIEW, CalendarContract.Events.CONTENT_URI);
                controleTTS.speak("Voce esta entrando no aplicativo agenda");

            }
            else if(nomeApp.equals("Youtube")){
                intent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                controleTTS.speak("Voce esta entrando no aplicativo Youtube");

            }

            if (intent != null) {
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                iniciarReconhecimento();
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controleTTS.shutdown();
    }
}
