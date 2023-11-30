package com.example.voicecontrol.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class ControleTTS implements TextToSpeech.OnInitListener {
    private Context context;
    private TextToSpeech tts;
    private boolean initialized = false; // Inicializado como falso por padrão

    public ControleTTS(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, this);
    }

    public ControleTTS(Context context, TextToSpeech.OnInitListener listener) {
        this.context = context;
        tts = new TextToSpeech(context, listener);
    }

    public void speak(String message) {
        if (tts != null && initialized && !tts.isSpeaking()) {
            // Adicionando logs para depuração
            Log.d("ControleTTS", "Falando: " + message);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            Log.w("ControleTTS", "Falha ao falar. tts não iniciado ou já está falando.");
        }
    }

    public void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            // Adicionando log para depuração
            Log.d("ControleTTS", "TextToSpeech encerrado com sucesso.");
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(context, "Dados de idioma ausentes. Por favor, faça o download.", Toast.LENGTH_LONG).show();
            } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Idioma não suportado. Escolha outro idioma.", Toast.LENGTH_LONG).show();
            } else {
                initialized = true;
            }
        } else {
            Toast.makeText(context, "Falha na inicialização do TextToSpeech.", Toast.LENGTH_SHORT).show();
        }
        // Adicionando log para depuração
        Log.d("ControleTTS", "onInit: ControleTTS inicializado. Inicializado: " + initialized);
    }
}
