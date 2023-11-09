package com.example.voicecontrol.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.voicecontrol.R;
import com.example.voicecontrol.util.SpeechToText;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SpeechToText speech = new SpeechToText(getActivity());

        Button btn = view.findViewById(R.id.btnFalar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!speech.isEstaOuvindo()) {
                    iniciarRec();
                } else {
                    Toast.makeText(getActivity(), "O reconhecimento de voz já está em andamento",
                            Toast.LENGTH_SHORT).show();
                }
            }

            private void iniciarRec() {
                try {
                    // Inicializar o reconhecimento de fala
                    speech.backgroundVoiceListener.run();
                    Toast.makeText(getActivity(), "Iniciando reconhecimento de voz",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Lida com possíveis erros ao iniciar o reconhecimento de voz
                    Toast.makeText(getActivity(), "Erro ao iniciar o reconhecimento de voz",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}