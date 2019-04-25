package pe.progsistem.jesus.ubeprivado.beans;



import java.io.Serializable;


public class Publicacion implements Serializable {

    private String id_pu;
    private String nombre_pu;
    private String edad_pu;
    private String descrip_pu;
    private String estado_disponible;
    private String tarifa;
    private String tarifa2;
    private String tarifa3;
    private String url_video;
    private String prioridad;
    private String sede_id;
    private String cat_id;
    private String usu_id;
    private String us_id;
   private String url_foto;
    private String ul_fecha;
    private String nombre_sede;
    private String cel_sede;
    private String calificacion;

    Sede sede;

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Publicacion() {
    }

    public Publicacion(String id_pu, String nombre_pu, String edad_pu, String descrip_pu, String estado_disponible, String tarifa, String tarifa2, String tarifa3, String url_video, String prioridad, String sede_id, String cat_id, String usu_id, String us_id, String url_foto, String ul_fecha, String nombre_sede, String cel_sede, String calificacion) {
        this.id_pu = id_pu;
        this.nombre_pu = nombre_pu;
        this.edad_pu = edad_pu;
        this.descrip_pu = descrip_pu;
        this.estado_disponible = estado_disponible;
        this.tarifa = tarifa;
        this.tarifa2 = tarifa2;
        this.tarifa3 = tarifa3;
        this.url_video = url_video;
        this.prioridad = prioridad;
        this.sede_id = sede_id;
        this.cat_id = cat_id;
        this.usu_id = usu_id;
        this.us_id = us_id;
        this.url_foto = url_foto;
        this.ul_fecha = ul_fecha;
        this.nombre_sede = nombre_sede;
        this.cel_sede = cel_sede;
        this.calificacion = calificacion;
    }

    public String getId_pu() {
        return id_pu;
    }

    public void setId_pu(String id_pu) {
        this.id_pu = id_pu;
    }

    public String getNombre_pu() {
        return nombre_pu;
    }

    public void setNombre_pu(String nombre_pu) {
        this.nombre_pu = nombre_pu;
    }

    public String getEdad_pu() {
        return edad_pu;
    }

    public void setEdad_pu(String edad_pu) {
        this.edad_pu = edad_pu;
    }

    public String getDescrip_pu() {
        return descrip_pu;
    }

    public void setDescrip_pu(String descrip_pu) {
        this.descrip_pu = descrip_pu;
    }

    public String getEstado_disponible() {
        return estado_disponible;
    }

    public void setEstado_disponible(String estado_disponible) {
        this.estado_disponible = estado_disponible;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public String getTarifa2() {
        return tarifa2;
    }

    public void setTarifa2(String tarifa2) {
        this.tarifa2 = tarifa2;
    }

    public String getTarifa3() {
        return tarifa3;
    }

    public void setTarifa3(String tarifa3) {
        this.tarifa3 = tarifa3;
    }

    public String getUrl_video() {
        return url_video;
    }

    public void setUrl_video(String url_video) {
        this.url_video = url_video;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getSede_id() {
        return sede_id;
    }

    public void setSede_id(String sede_id) {
        this.sede_id = sede_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getUs_id() {
        return us_id;
    }

    public void setUs_id(String us_id) {
        this.us_id = us_id;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getUl_fecha() {
        return ul_fecha;
    }

    public void setUl_fecha(String ul_fecha) {
        this.ul_fecha = ul_fecha;
    }

    public String getNombre_sede() {
        return nombre_sede;
    }

    public void setNombre_sede(String nombre_sede) {
        this.nombre_sede = nombre_sede;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(String usu_id) {
        this.usu_id = usu_id;
    }

    public String getCel_sede() {
        return cel_sede;
    }

    public void setCel_sede(String cel_sede) {
        this.cel_sede = cel_sede;
    }
}
