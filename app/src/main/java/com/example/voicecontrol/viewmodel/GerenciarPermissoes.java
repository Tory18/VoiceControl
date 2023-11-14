package com.example.voicecontrol.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.voicecontrol.model.PermissoesUsuario;

import java.util.ArrayList;
import java.util.List;

public class GerenciarPermissoes {

    public static boolean VerificarPermissao(Context context, String permissao){
        return ContextCompat.checkSelfPermission(context, permissao) == PackageManager.PERMISSION_GRANTED;
    }

    public static List<String> VerificarPermissoesFaltantes(Context context, String[] permissoes){
        List<String> permissoesFaltantes = new ArrayList<>();

        for (String permissao : permissoes) {
            if (!VerificarPermissao(context, permissao)) {
                permissoesFaltantes.add(permissao);
            }
        }

        return permissoesFaltantes;
    }

    public static boolean SolicitarPermissoesFaltantes(Context context, String[] permissoes, int requestCode){
        List<String> permissoesFaltantes = VerificarPermissoesFaltantes(context, permissoes);

        if (!permissoesFaltantes.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, permissoesFaltantes.toArray(new String[0]), requestCode);
            return false; // Permissões ainda não concedidas
        }

        return true; // Todas as permissões já foram concedidas
    }
}
