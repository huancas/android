package pe.progsistem.jesus.ubeprivado.beans;

public class Direccion {
    private String id_bd;
    private String id_dir;
    private String place_id;
    private String formatted_address;
    private String icono;
    private String name;
    private String geometrylocationlat;
    private String geometrylocationlng;

    public Direccion() {
    }

    public Direccion(String id_bd, String id_dir, String place_id, String formatted_address, String icono, String name, String geometrylocationlat, String geometrylocationlng) {
        this.id_bd = id_bd;
        this.id_dir = id_dir;
        this.place_id = place_id;
        this.formatted_address = formatted_address;
        this.icono = icono;
        this.name = name;
        this.geometrylocationlat = geometrylocationlat;
        this.geometrylocationlng = geometrylocationlng;
    }

    public String getId_bd() {
        return id_bd;
    }

    public void setId_bd(String id_bd) {
        this.id_bd = id_bd;
    }

    public String getId_dir() {
        return id_dir;
    }

    public void setId_dir(String id_dir) {
        this.id_dir = id_dir;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeometrylocationlat() {
        return geometrylocationlat;
    }

    public void setGeometrylocationlat(String geometrylocationlat) {
        this.geometrylocationlat = geometrylocationlat;
    }

    public String getGeometrylocationlng() {
        return geometrylocationlng;
    }

    public void setGeometrylocationlng(String geometrylocationlng) {
        this.geometrylocationlng = geometrylocationlng;
    }
}
