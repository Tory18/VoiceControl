package com.example.voicecontrol.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class ControleTTS implements TextToSpeech.OnInitListener {
    private Context context;
    private TextToSpeech tts;

    public ControleTTS(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, this);
    }

    private boolean inicializar = false; // Inicializado como falso por padrão

    public ControleTTS(Context context, TextToSpeech.OnInitListener listener) {
        this.context = context;
        tts = new TextToSpeech(context, listener);
    }

    public void speak(String message, int queueFlush, Object o, Object o1) {
        if (tts != null && inicializar && !tts.isSpeaking()) {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(new Locale("pt", "BR"));

            if (result == TextToSpeech.LANG_MISSING_DATA) {
                Toast.makeText(context, "Dados de idioma ausentes. Por favor, faça o download.", Toast.LENGTH_LONG).show();
            } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Idioma não suportado. Escolha outro idioma.", Toast.LENGTH_LONG).show();
            } else {
                inicializar = true;
            }
        } else {
            Toast.makeText(context, "Falha na inicialização do TextToSpeech.", Toast.LENGTH_SHORT).show();
        }
    }
}