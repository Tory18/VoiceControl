package com.example.voicecontrol.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.voicecontrol.R;
import com.example.voicecontrol.model.PermissoesUsuario;
import com.example.voicecontrol.util.ControleTTS;
import com.example.voicecontrol.viewmodel.GerenciarPermissoes;

public class MainActivity extends AppCompatActivity {
    private Button btnPermissao;
    private ControleTTS controleTTS;
    private ImageView imgVoiceControl;
    private Button btnCadastro;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.voicecontrol.R.layout.activity_main);


        btnPermissao = findViewById(R.id.btn_permissao);
        controleTTS = new ControleTTS(this);
        imgVoiceControl = findViewById(R.id.imageView6);
        btnCadastro = findViewById(R.id.bnt_cadastro);

        String [] permissoesUsario = PermissoesUsuario.Permissoe;
        if (GerenciarPermissoes.SolicitarPermissoesFaltantes(this, permissoesUsario, 1)){
            // Todas as permissões já foram concedidas, prossiga com suas ações
        }else{
            // Algumas permissões ainda precisam ser concedidas
            // O pedido de permissões foi enviado; você pode tratar o resultado em onRequestPermissionsResult
        }

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = btnCadastro.getContentDescription().toString();
                controleTTS.speak(text);
                Intent cadIntent = new Intent(MainActivity.this, ActivityCadastro.class);
                startActivity(cadIntent);
            }
        });
        btnPermissao.setOnClickListener(new View.OnClickListener() {
            private long lastClickTime = 0;

            @Override
            public void onClick(View v) {
                String textoBtn = btnPermissao.getContentDescription().toString();
                controleTTS.speak(textoBtn);

                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < 1000) {


                } else {
                    // Se o botão for pressionado duas vezes seguidas, abre as configurações do aplicativo
                    Intent confIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    confIntent.setData(android.net.Uri.parse("package:" + getPackageName()));
                    startActivity(confIntent);
                }

                lastClickTime = currentTime;
            }
        });
    }

    protected void onResume() {
        super.onResume();
        controleTTS.speak("Ola usuario, o proximmo passo sera ir para a tela de cadastro");

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        controleTTS.shutdown();
    }

}