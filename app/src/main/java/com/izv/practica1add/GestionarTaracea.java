package com.izv.practica1add;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 2º DAM on 23/11/2014.
 */
public class GestionarTaracea {
    private ArrayList<Taracea> taracea;
    public GestionarTaracea (){
        taracea=new ArrayList<Taracea>();
    }
    public GestionarTaracea (ArrayList<Taracea> aj){
        taracea=aj;
    }
    public int size(){
        return taracea.size();
    }
    public void set(int i,Taracea j){
        taracea.set(i,j);
    }
    public void copiaArraylist(ArrayList<Taracea> aj){
        this.taracea=aj;
    }
    public ArrayList<Taracea> pasaArraylist(){
        ArrayList<Taracea> ju=new ArrayList<Taracea>();
        for(int i=0;i<taracea.size();i++){
            ju.add(taracea.get(i));
        }
        return ju;
    }

    public Taracea get(int i){
        Taracea j=taracea.get(i);
        return j;
    }
    public void remove(int i){
        taracea.remove(i);
    }
    public void add(Taracea j){
        taracea.add(j);
    }
    public boolean isEmpty(){
        if(taracea.isEmpty())
            return true;
        return false;
    }

    public int comparaRef(int i, Taracea j1){
        return taracea.get(i).compareTo(j1);
    }
    public void ordenaNombres(){
        Collections.sort(taracea,new Ordenar());
    }
}
