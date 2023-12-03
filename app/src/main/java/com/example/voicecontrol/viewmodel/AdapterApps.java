package com.example.voicecontrol.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.voicecontrol.R;
import com.example.voicecontrol.model.App;
import com.example.voicecontrol.model.Cadastro;

import java.util.ArrayList;
import java.util.List;

public class AdapterApps extends ArrayAdapter<App> {
    private Context context;
    private  int resource;

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
    public AdapterApps(Context context, int resource, ArrayList<App> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.img_app);
            viewHolder.textView = convertView.findViewById(R.id.nome_app);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(getItem(position).getId());
        viewHolder.textView.setText(getItem(position).getNome());
        return convertView;
    }
}