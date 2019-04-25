package pe.progsistem.jesus.ubeprivado.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Sede implements Parcelable {
    public String sede_id;
    public String sede_name;
    public String sede_descripcion;
    public String sede_cel;
    public String sede_cel2;

    public Sede() {
    }

    public Sede(String sede_id, String sede_name) {
        this.sede_id = sede_id;
        this.sede_name = sede_name;
    }

    public Sede(String sede_id, String sede_name, String sede_descripcion, String sede_cel, String sede_ce2) {
        this.sede_id = sede_id;
        this.sede_name = sede_name;
        this.sede_descripcion = sede_descripcion;
        this.sede_cel = sede_cel;
        this.sede_cel2 = sede_ce2;
    }

    protected Sede(Parcel in) {
        sede_id = in.readString();
        sede_name = in.readString();
        sede_descripcion = in.readString();
        sede_cel = in.readString();
        sede_cel2 = in.readString();
    }

   /* public Sede(Parcel in) {
        super();
        readFromParcel(in);
    } */

    public static final Creator<Sede> CREATOR = new Creator<Sede>() {
        @Override
        public Sede createFromParcel(Parcel in) {
            return new Sede(in);
        }

        @Override
        public Sede[] newArray(int size) {
            return new Sede[size];
        }
    };

    public String getSede_id() {
        return sede_id;
    }

    public void setSede_id(String sede_id) {
        this.sede_id = sede_id;
    }

    public String getSede_name() {
        return sede_name;
    }

    public void setSede_name(String sede_name) {
        this.sede_name = sede_name;
    }

    public String getSede_descripcion() {
        return sede_descripcion;
    }

    public void setSede_descripcion(String sede_descripcion) {
        this.sede_descripcion = sede_descripcion;
    }

    public String getSede_cel() {
        return sede_cel;
    }

    public void setSede_cel(String sede_cel) {
        this.sede_cel = sede_cel;
    }

    public String getSede_cel2() {
        return sede_cel2;
    }

    public void setSede_cel2(String sede_ce2) {
        this.sede_cel2 = sede_ce2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sede_id);
        dest.writeString(sede_name);
        dest.writeString(sede_descripcion);
        dest.writeString(sede_cel);
        dest.writeString(sede_cel2);
    }
}
