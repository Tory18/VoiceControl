package com.example.voicecontrol.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechToText implements RecognitionListener {
    private static final int REQUEST_CODE = 2;

    private Intent it;
    private String textReconhecido = "";
    private SpeechRecognizer speechRecognizer;
    public final backgroundVoiceListener backgroundVoiceListener;
    private boolean estaOuvindo = false;

    public SpeechToText(Context context) {
        backgroundVoiceListener = new backgroundVoiceListener();
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(this);

        this.it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
        it.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
        it.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 3000);

    }

    public boolean isEstaOuvindo() {
        return estaOuvindo;
    }

    private void setEstaOuvindo(boolean estaOuvindo) {
        this.estaOuvindo = estaOuvindo;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        setEstaOuvindo(false);
    }

    @Override
    public void onBeginningOfSpeech() {
        setEstaOuvindo(true);
    }

    @Override
    public void onRmsChanged(float v) {
        Log.e("TAG", "onRmsChanged: " + v);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        // Dados de áudio brutos recebidos.

    }

    @Override
    public void onEndOfSpeech() {
        setEstaOuvindo(false);
    }

    @Override
    public void onError(int error) {
        Log.e("Erro ao ouvir", "textReconhecido: "
                + textReconhecido);
    }

    @Override
    public void onResults(Bundle results) {
        Log.e("Result da esculta", "Termiou de gravar");

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        textReconhecido = "";
        ArrayList<String> matches = partialResults.getStringArrayList(
                SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null)
            for (String resultado : matches)
                textReconhecido += resultado;
        //MainActivity.setEditTextMsgUsu(textoEscutado);
        setEstaOuvindo(false);

    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        // Eventos relacionados ao reconhecimento de fala.

    }

    public class backgroundVoiceListener {
        public void run() {
            try {
                if (!isEstaOuvindo()) {
                    setEstaOuvindo((true));
                    speechRecognizer.startListening(it);
                    Log.e("Tag", "Escultando");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void interrupt() {
            try {
                speechRecognizer.stopListening();
                Log.e("Tag", "Encerrou a escuta");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
