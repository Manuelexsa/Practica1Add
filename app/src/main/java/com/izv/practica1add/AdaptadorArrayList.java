package com.izv.practica1add;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class AdaptadorArrayList extends ArrayAdapter<Taracea> {
    private Context contexto;
    private ArrayList<Taracea> lista;
    private int recurso;
    private static LayoutInflater i;
    private ViewHolder vh;

    static class ViewHolder{
        public TextView tvp, tvd,tvn;
        public ImageView iv;
        public MenuItem item;
        public int posicion;
    }
    public AdaptadorArrayList(Context context, int resource, ArrayList<Taracea> objects) {
        super(context, resource, objects);
        this.contexto=context;
        this.lista=objects;
        this.recurso=resource;
        this.i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.v("LOG", "" + lista.size());
        if(convertView == null){
            convertView = i.inflate(recurso, null);
            vh = new ViewHolder();
            vh.tvd=(TextView)convertView.findViewById(R.id.txDesc);
            vh.tvn=(TextView)convertView.findViewById(R.id.txnombre);
            vh.tvp=(TextView)convertView.findViewById(R.id.txPvp);
            vh.iv=(ImageView)convertView.findViewById(R.id.imgInsert);
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder)convertView.getTag();
        }
        vh.posicion=position;
        vh.tvn.setText(lista.get(position).getNombre().toString());
        vh.tvd.setText(lista.get(position).getDesc().toString());
        vh.tvp.setText(lista.get(position).getPvp()+"");
        vh.iv.setTag(position);
        return convertView;
    }
}