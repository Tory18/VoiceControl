package com.example.voicecontrol.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.voicecontrol.R;
import com.example.voicecontrol.model.App;
import com.example.voicecontrol.model.Cadastro;

import java.util.List;

public class AdapterApps extends BaseAdapter {
    Context context;

    public List<App> listApps;

    @Override
    public int getCount() {
        return listApps.size();
    }

    @Override
    public Object getItem(int position) {
        return listApps.get(position);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.item_lista_apps, parent, false
        );
        TextView NomeApp = v.findViewById(R.id.nome_app);
        ImageView imgApp = v.findViewById(R.id.img_app);

        App apps = listApps.get(position);


        return null;
    }
}
