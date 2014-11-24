package com.izv.practica1add;

import java.util.Comparator;

/**
 * Created by 2º DAM on 23/11/2014.
 */
public class Ordenar implements Comparator<Taracea> {

    @Override
    public int compare(Taracea j1, Taracea j2) {
        if(j1.getNombre().compareTo(j2.getNombre())>0)
            return 1;
        if(j1.getNombre().compareTo(j2.getNombre())<0)
            return -1;
        return 0;
    }
}
