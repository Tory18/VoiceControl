package com.example.voicecontrol.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.voicecontrol.R;
import com.example.voicecontrol.util.ControleTTS;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {
    private Intent intent = null;
    private ImageButton btn;
    private ListView view;
    private ControleTTS controleTTS;
    private static final int REQUEST_CODE = 4;
    private static final String PREFS_NAME = "user_prefs";
    private static final String USER_NAME_KEY = "user_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        controleTTS = new ControleTTS(this);
        view = findViewById(R.id._dynamic);
        btn = findViewById(R.id.btnFalar);



        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                if (preferences.contains(USER_NAME_KEY)) {
                    String nome = preferences.getString(USER_NAME_KEY, "");

                    String chamar = "Ola" + nome + "!";
                    controleTTS.speak(chamar);
                    iniciarReconhecimento();
                }
            }
        }, 100);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarReconhecimento();
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
            processarComando(resultadoReconhecimento);

            if (resultadoReconhecimento.equalsIgnoreCase("alterar")) {
                Intent it;
                it = new Intent(Home.this, ActivityAlterar.class);
                startActivity(it);
                controleTTS.speak("Você está sendo redirecionado para tela de alterar seu nome e o nome de seu usuario");
            }
        }
    }

    private void processarComando(String nomeApp) {
        switch (nomeApp.toLowerCase()) {
            case "telefone":
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "));
                controleTTS.speak("Você está entrando no aplicativo telefone");
                break;

            case "mapa":
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=sua_localizacao"));
                controleTTS.speak("Você está entrando no aplicativo mapa");
                break;

            case "e-mail":
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                controleTTS.speak("Você está entrando no aplicativo email");
                break;

            case "agenda":
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_CALENDAR);
                controleTTS.speak("Você está entrando no aplicativo agenda");
                break;
            case "youtube":
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com"));
                controleTTS.speak("Você está entrando no aplicativo Youtube");
                break;
        }
        Log.d("HomeActivity", "Nome do App: " + nomeApp);
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE);
        }
        intent = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        controleTTS.speak("Deseja ir a alguma coisa?");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                iniciarReconhecimento();
            }
        }, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (controleTTS != null) {
            controleTTS.shutdown();
        }
    }
}
