package com.example.voicecontrol.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.voicecontrol.R;
import com.example.voicecontrol.model.Cadastro;
import com.example.voicecontrol.model.InstrucoesSintentizadas;
import com.example.voicecontrol.util.ControleTTS;
import com.example.voicecontrol.viewmodel.ControleCadastro;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityAlterar extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private static final int REQUEST_CODE = 1;
    private EditText altUsuario;
    private Button salvar;
    private ControleTTS controleTTS;
    private SQLiteDatabase bancoDados;
    private static final String PREFS_NAME = "user_prefs";
    private static final String USER_NAME_KEY = "user_name";
    private String respostaReconhecida;

    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSIONS_REQUEST_RECORD_AUDIO);
        }*/



        altUsuario = findViewById(R.id.nome_usuario);

        controleTTS = new ControleTTS(this);
        controleTTS.speak("Você é o " + altUsuario.getText().toString() + ". Deseja fazer alguma alteração?");

        Intent intent = getIntent();
        id = intent.getStringExtra("nome");
        carregarDados();

        salvar = findViewById(R.id.btnSalvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarReconhecimento();

            }

            private void iniciarReconhecimento() {
                Intent it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
                controleTTS.speak("Fale ...");
                it.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale...");
                try {
                    startActivityForResult(it, REQUEST_CODE);
                } catch (Exception e) {

                }
            }
        });

        altUsuario.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iniciarReconhecimentoAlterar();
                    altUsuario.requestFocus();
                }
                return false;
            }
        });
    }

    public void alterar() {
        String nome = altUsuario.getText().toString();
        Cadastro cadastro = new Cadastro(nome);
        ControleCadastro controleCadastro = ControleCadastro.getInstancia(ActivityAlterar.this);
        if (controleCadastro.atualizarLista()) {
            controleTTS.speak("Dados alterados com sucesso");
            Toast.makeText(this, "Dados alterados com sucesso", Toast.LENGTH_SHORT).show();

            // Update SharedPreferences with the new user name
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USER_NAME_KEY, nome);
            editor.apply();

            // Start Home activity with the updated information
            Intent intent = new Intent(ActivityAlterar.this, Home.class);
            intent.putExtra(USER_NAME_KEY, nome);
            startActivity(intent);

            // Remove the following line if you want users to be able to go back to the previous screen
            finish();
        } else {
            Toast.makeText(this, "Sem sucesso", Toast.LENGTH_SHORT).show();
        }
    }

    public void carregarDados() {
        try {
            bancoDados = openOrCreateDatabase("GestaoUser", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT nome, nomeAssistente FROM Usuario WHERE nome = ?", new String[]{id});
            cursor.moveToFirst();
            altUsuario.setText(cursor.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void iniciarReconhecimentoAlterar() {
        Intent it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
        controleTTS.speak("Fale ...");
        it.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale...");

        try {
            startActivityForResult(it, REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            respostaReconhecida = matches.get(0).toLowerCase();

            if (altUsuario.isFocused() && !respostaReconhecida.equalsIgnoreCase("sim")) {
                altUsuario.setText(respostaReconhecida);
                altUsuario.setSelection(altUsuario.getText().length());


                String tUsuario = altUsuario.getText().toString();
                controleTTS.speak("Seu novo nome é : " + tUsuario );
            }
             else if (respostaReconhecida.equalsIgnoreCase("sim")){
                alterar();
                Intent intent = new Intent(ActivityAlterar.this, Home.class);
                startActivity(intent);
                finish();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                if (preferences.contains(USER_NAME_KEY)) {
                    String nome = preferences.getString(USER_NAME_KEY, "");
                    controleTTS.speak("Você é o " + nome);
                }
            }
        }, 100);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (controleTTS != null) {
            controleTTS.shutdown();
        }
    }
}