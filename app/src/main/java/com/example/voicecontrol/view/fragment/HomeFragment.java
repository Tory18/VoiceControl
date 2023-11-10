package com.example.voicecontrol.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.voicecontrol.R;
import com.example.voicecontrol.util.SpeechToText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class HomeFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private Intent intent = null;

    public HomeFragment() {
        // Construtor público vazio obrigatório
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Verifique a permissão RECORD_AUDIO aqui e solicite-a, se necessário

        Button btn = view.findViewById(R.id.btnFalar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarReconhecimento();
                Log.d("tag", "inciar");
            }
        });

        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<String> resultados = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String resultadoReconhecimento = resultados.get(0);
            String nomeApp = resultadoReconhecimento;

             //Lide com os comandos de voz reconhecidos e inicie atividades correspondentes aqui
            if (nomeApp.equals("telefone")) {
                // Inicie o discador com o número de telefone
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456789"));
            } else if (nomeApp.equals("mapa")) {
                // Inicie um intent relacionado a mapas
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=sua_localizacao"));
            } else if (nomeApp.equals("email")) {
                // Inicie um intent relacionado a e-mail
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
            } else if (nomeApp.equals("agenda")) {
                // Inicie um intent de evento de calendário
                intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                // Defina detalhes do evento de calendário
            }

            if (intent != null) {
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }
}