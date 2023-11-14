/*package com.example.voicecontrol.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.voicecontrol.R;
import com.example.voicecontrol.model.Cadastro;
import com.example.voicecontrol.model.Cadastro;

import java.util.List;

public class Adapter extends BaseAdapter{
    Context context;
    ControleCadastro controleCadastro;
    public List<Cadastro> cadastros;

    public Adapter(Context context) {
        this.context = context;
        controleCadastro = ControleCadastro.getInstancia(context);
        cadastros = controleCadastro.buscarTodos();
        atualizarLista();
    }
    @Override
    public int getCount(){
        return cadastros.size();
    }
    @Override
    public Cadastro getItem(int position){
        return cadastros.get(position);
    }
    @Override
    public long getItemId(int position){
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.item_lista, parent, false
        );
        TextView textView = v.findViewById(R.id.textView);
        TextView textView1 = v.findViewById(R.id.textView2);

        Cadastro cadastro = cadastros.get(position);
        textView.setText(cadastro.getNome());
        textView1.setText(cadastro.getNomeAssistente());

        return v;
    }
    public void atualizarLista(){
        cadastros.clear();
        cadastros.addAll(controleCadastro.buscarTodos());
        notifyDataSetChanged();
    }
}*/
