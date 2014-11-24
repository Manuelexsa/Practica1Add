package com.izv.practica1add;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class Principal extends Activity implements AdapterView.OnItemLongClickListener {

    private static final int REQUEST_TEXT = 0;
    private static final int ACTIVIDAD_SEGUNDA=1;
    private GestionarTaracea taracea= new GestionarTaracea();
    private AdaptadorArrayList ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        ArrayList<Taracea> datos=new ArrayList<Taracea>();
        datos=leer();
        taracea.copiaArraylist(datos);
        ad = new AdaptadorArrayList(this, R.layout.lista_detalle, datos);
        ListView lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);
        lv.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) this);
        TextView tv=(TextView)findViewById(R.id.tvListaObjetos);
        tv.setVisibility(View.INVISIBLE);
        tv.setHeight(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.anadir) {
            agregar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        TextView tv=(TextView)findViewById(R.id.tvListaObjetos);
        ArrayList<Taracea>js=taracea.pasaArraylist();
        outState.putParcelableArrayList("Taracea", js);
        outState.putString("text",tv.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String m=savedInstanceState.getString("text");
        ArrayList<Taracea>js= savedInstanceState.getParcelableArrayList("Taracea");
        TextView tv=(TextView)findViewById(R.id.tvListaObjetos);
        if(!m.isEmpty()){
            tv.setVisibility(View.VISIBLE);
            tv.setHeight(80);
            tv.setText(m);

        }
        taracea.copiaArraylist(js);
        ad = new AdaptadorArrayList(this, R.layout.lista_detalle, js);
        ListView lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( resultCode == Activity.RESULT_OK ){
            ArrayList <Taracea>js=new ArrayList<Taracea>();
            js=data.getParcelableArrayListExtra("taracea");
            taracea.copiaArraylist(js);
            ad.notifyDataSetChanged();
            ad = new AdaptadorArrayList(Principal.this, R.layout.lista_detalle, js);
            ListView lv = (ListView) findViewById(R.id.lvLista);
            lv.setAdapter(ad);
            TextView tv=(TextView)findViewById(R.id.tvListaObjetos);
            tv.setVisibility(View.VISIBLE);
            tv.setHeight(80);
            tv.setText(data.getStringExtra("text"));
        }

    }

    private boolean agregar() {
        TextView tv=(TextView)findViewById(R.id.tvListaObjetos);
        tv.setVisibility(View.INVISIBLE);
        tv.setHeight(0);
        tv.setText("");
        //cargamos vista
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.insertar, null);
        final EditText et,et1,et2;
        et = (EditText) vista.findViewById(R.id.etn);
        et1 = (EditText) vista.findViewById(R.id.etd);
        et2 = (EditText) vista.findViewById(R.id.etp);
        et.setHint("Introduzca nombre");
        et1.setHint("Introduzca descripcion");
        et2.setHint("Introduzca precio");/*
        et.setFilters(new InputFilter[]{new InputFilterMinMax("1", "10")});
        et1.setFilters(new InputFilter[]{new InputFilterMinMax("1", "99")});
        //dialogo*/
        final AlertDialog d = new AlertDialog.Builder(this)
                .setView(vista)
                .setTitle("Añadir Objeto")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Taracea t = new Taracea();

                        t.setNombre(et.getText().toString());
                        t.setDesc(et1.getText().toString());
                        t.setPvp(Integer.parseInt(et2.getText().toString()));
                        taracea.add(t);
                        ArrayList<Taracea> datos=taracea.pasaArraylist();
                        ad.notifyDataSetChanged();
                        ad = new AdaptadorArrayList(Principal.this, R.layout.lista_detalle, datos);
                        ListView lv = (ListView) findViewById(R.id.lvLista);
                        lv.setAdapter(ad);
                        tostada("El objeto "+t.getPvp()+" ha sido añadido");
                        escribir();
                        d.dismiss();
                    }
                    // Filtramos que nos este vacios
                       /* if(et2.getText().toString().length() == 0 ){
                            tostada("¡Introduzca nombre!");
                        }
                        if(et3.getText().toString().length() == 0 ){
                            tostada("¡Introduzca teléfono!");
                        }
                        if(et.getText().toString().length() == 0 ){
                            tostada("¡Introduzca media!");
                        }
                        if(et1.getText().toString().length() == 0 ){
                            tostada("¡Introduzca dorsal!");
                        }


                        if(!esTelefono(et3.getText().toString())){
                            tostada("¡Teléfono incorrecto!");
                        }*/

                });
            }
        });
        d.show();
        ArrayList<Taracea> datos=taracea.pasaArraylist();
        ad = new AdaptadorArrayList(Principal.this, R.layout.lista_detalle, datos);
        ListView lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);
        return true;
    }
    public ArrayList<Taracea> leer(){
        ArrayList<Taracea> lista=new ArrayList<Taracea>();
        try {
            XmlPullParser lectorxml = Xml.newPullParser();
            lectorxml.setInput(new FileInputStream(new File(getExternalFilesDir(null), "Archivo.xml")), "utf-8");
            int evento = lectorxml.getEventType();
            Taracea j=new Taracea();
            while(evento != XmlPullParser.END_DOCUMENT){
                if(evento == XmlPullParser.START_TAG) {
                    String etiqueta = lectorxml.getName();
                    if (etiqueta.compareTo("taracea") == 0) {
                    }
                    if (etiqueta.compareTo("nombre") == 0) {
                        j.setNombre(lectorxml.nextText());
                    }
                    if (etiqueta.compareTo("descripcion") == 0) {
                        j.setDesc(lectorxml.nextText());
                    }
                    if (etiqueta.compareTo("pvp") == 0) {
                        try {
                            int m = Integer.parseInt(lectorxml.nextText());
                            j.setPvp(m);
                        } catch (Exception e) {
                            tostada("error en la lectura de precio de un objeto");
                        }
                    }

                }
                if (evento == XmlPullParser.END_TAG) {
                    String etiqueta = lectorxml.getName();
                    if (etiqueta.compareTo("taracea") == 0) {
                        lista.add(j);
                        j=new Taracea();
                    }
                }
                evento = lectorxml.next();
            }
        }catch(Exception e){}
        return lista;
    }
    public void escribir()  {

        try {
            File f = new File(getExternalFilesDir(null), "Archivo.xml");
            FileOutputStream fosxml = new FileOutputStream(f);
            XmlSerializer docxml = Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            docxml.startTag(null, "Taracea");
            //recorre el array y lo escribe en l xml
            for (int i=0;i<taracea.size();i++) {

                docxml.startTag(null, "taracea");
                docxml.startTag(null, "nombre");
                docxml.text(taracea.get(i).getNombre().toString());
                docxml.endTag(null, "nombre");
                docxml.startTag(null, "descripcion");
                docxml.text(taracea.get(i).getDesc().toString());
                docxml.endTag(null, "descripcion");
                docxml.startTag(null, "precio");
                docxml.text(taracea.get(i).getPvp() + "");
                docxml.endTag(null, "precio");
                docxml.startTag(null, "dorsal");
            }
            docxml.endTag(null, "Taracea");
            docxml.endDocument();
            docxml.flush();
            fosxml.close();

        }catch (Exception e){}
    }
    private boolean editar(final int index) {
        TextView tv=(TextView)findViewById(R.id.tvListaObjetos);
        tv.setVisibility(View.INVISIBLE);
        tv.setHeight(0);
        tv.setText("");
        //cargamos ObjetoTaracea
        Taracea g=new Taracea();
        g=taracea.get(index);
        //cargamos vista
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.insertar, null);
        final EditText  et,et1, et2;
        String salidaN,salidad,salidap;
        salidaN=g.getNombre().toString();
        salidad=g.getDesc().toString();
        salidap=g.getPvp()+"";
        et = (EditText) vista.findViewById(R.id.etn);
        et1 = (EditText) vista.findViewById(R.id.etd);
        et2 = (EditText) vista.findViewById(R.id.etp);

        et.setText(salidaN);
        et1.setText(salidad);
        et2.setText(salidap);

        final AlertDialog d = new AlertDialog.Builder(this)
                .setView(vista)
                .setTitle("Modificar Objeto")
                .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        Taracea t = new Taracea();

                        t.setNombre(et.getText().toString());
                        t.setDesc(et1.getText().toString());
                        t.setPvp(Integer.parseInt(et2.getText().toString()));
                        taracea.set(index, t);
                        ArrayList<Taracea> datos=taracea.pasaArraylist();
                        ad.notifyDataSetChanged();
                        ad = new AdaptadorArrayList(Principal.this, R.layout.lista_detalle, datos);
                        ListView lv = (ListView) findViewById(R.id.lvLista);
                        lv.setAdapter(ad);
                        tostada("El objeto " + t.getNombre() + " ha sido modificado");
                        escribir();
                        d.dismiss();
                    }
                });
            }
        });
        d.show();
        ArrayList<Taracea> datos=taracea.pasaArraylist();
        ad = new AdaptadorArrayList(Principal.this, R.layout.lista_detalle, datos);
        ListView lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);
        return true;
    }
    public boolean borrar(final int pos){
        TextView tv=(TextView)findViewById(R.id.tvListaObjetos);
        tv.setVisibility(View.INVISIBLE);
        tv.setHeight(0);
        tv.setText("");
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Principal.this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Desea borrar el objeto seleccionado ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                taracea.remove(pos);
                ArrayList<Taracea> datos=taracea.pasaArraylist();
                ad.notifyDataSetChanged();
                ad = new AdaptadorArrayList(Principal.this, R.layout.lista_detalle, datos);
                ListView lv = (ListView) findViewById(R.id.lvLista);
                lv.setAdapter(ad);
                escribir();
                tostada("Objeto borrado");
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                finish();
            }
        });
        dialogo1.show();
        return true;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        String[] opc = new String[]{"Borrar", "Modificar"};
        final int posicion = position;
        AlertDialog opciones = new AlertDialog.Builder(
                Principal.this)
                .setTitle("Opciones")
                .setItems(opc,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int selected) {
                                if (selected == 0) {
                                    borrar(posicion);
                                } else if (selected == 1) {
                                    editar(position);
                                }
                            }
                        }).create();
        opciones.show();
        return true;
    }
    public Toast tostada(String t) {
        Toast toast = Toast.makeText(getApplicationContext(),t + "", Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }
}
