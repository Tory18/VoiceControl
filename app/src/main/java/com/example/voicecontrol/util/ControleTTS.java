package com.example.voicecontrol.util;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class ControleTTS implements TextToSpeech.OnInitListener {
    private Context context;
    private TextToSpeech tts;

    private boolean inicializar = true;

    private boolean mensagemFalada = false;

    public ControleTTS(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, this);
    }

    public void speak(String message) {
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

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Idioma não suportado", Toast.LENGTH_SHORT).show();
            } else {
                inicializar = true;
            }
        } else {
            Toast.makeText(context, "Falha na inicialização do TextToSpeech.", Toast.LENGTH_SHORT).show();
        }
    }
}
