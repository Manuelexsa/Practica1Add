package com.izv.practica1add;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 2º DAM on 23/11/2014.
 */
public class Taracea implements Parcelable,Comparable<Taracea>{
    private String desc,nombre;
    private int pvp,ref;
    public static final Creator<Taracea> CREATOR = new Creator<Taracea>() {
        @Override
        public Taracea createFromParcel(Parcel source) {
            return new Taracea(source);
        }

        @Override
        public Taracea[] newArray(int size) {
            return new Taracea[size];
        }
    };

    public Taracea(int ref,String desc, String nombre, int pvp) {
        this.ref=ref;
        this.desc = desc;
        this.nombre = nombre;
        this.pvp = pvp;
    }

    public Taracea(Parcel prc) {
        this.ref = prc.readInt();
        this.desc = prc.readString();
        this.nombre = prc.readString();
        this.pvp = prc.readInt();
    }

    public Taracea() {

    }



    @Override
    public int compareTo(Taracea another) {
        if (this.ref==another.ref) {
            return 1;
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ref);
        dest.writeString(nombre);
        dest.writeString(desc);
        dest.writeInt(pvp);
    }

    @Override
    public String toString() {
        return "Taracea {" +
                ", ref = '" + ref + '\'' +
                "nombre = " + nombre +
                ", descripcion = " + desc +
                ", pvp = '" + pvp + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof Taracea)) return false;

        Taracea other = (Taracea) obj;

        if(nombre.isEmpty()){
            return false;
        }
        return true;
    }


    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPvp() {
        return pvp;
    }

    public void setPvp(int pvp) {
        this.pvp = pvp;
    }
}
