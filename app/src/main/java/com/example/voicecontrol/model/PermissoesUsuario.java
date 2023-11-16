package com.example.voicecontrol.model;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissoesUsuario {
    public static final String[] Lista_de_Permissoes = {
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WAKE_LOCK
    };

    public static boolean VerificarPermissao(Context context, String permissao) {
        return ContextCompat.checkSelfPermission(context, permissao) == PackageManager.PERMISSION_GRANTED;
    }

    public static List<String> VerificarPermissoesFaltantes(Context context, String[] permissoes) {
        List<String> permissoesFaltantes = new ArrayList<>();

        for (String permissao : permissoes) {
            if (!VerificarPermissao(context, permissao)) {
                permissoesFaltantes.add(permissao);
            }
        }

        return permissoesFaltantes;
    }

    public static boolean TodasPermissoesConcedidas(Context context, String[] permissoes) {
        for (String permissao : permissoes) {
            if (!VerificarPermissao(context, permissao)) {
                return false;
            }
        }
        return true;
    }

    public static boolean SolicitarPermissoesFaltantes(Activity activity, String[] permissoes, int requestCode) {
        List<String> permissoesFaltantes = VerificarPermissoesFaltantes(activity, permissoes);

        if (!permissoesFaltantes.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissoesFaltantes.toArray(new String[0]), requestCode);
            return false; // Permissões ainda não concedidas
        }

        return true; // Todas as permissões já foram concedidas
    }
}
