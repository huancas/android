package pe.progsistem.jesus.ubeprivado.beans;

public class Atencion {
    private String id_at;
    private String fecha_at;
    private String tipo_at;
   private String usuario_trabajador;
    private String usuario_cliente;
    private String calif;
    private String bool_calif;


    public Atencion() {
    }

    public Atencion(String id_at, String fecha_at, String tipo_at, String usuario_trabajador, String usuario_cliente, String calif, String bool_calif) {
        this.id_at = id_at;
        this.fecha_at = fecha_at;
        this.tipo_at = tipo_at;
        this.usuario_trabajador = usuario_trabajador;
        this.usuario_cliente = usuario_cliente;
        this.calif = calif;
        this.bool_calif = bool_calif;
    }

    public String getId_at() {
        return id_at;
    }

    public void setId_at(String id_at) {
        this.id_at = id_at;
    }

    public String getFecha_at() {
        return fecha_at;
    }

    public void setFecha_at(String fecha_at) {
        this.fecha_at = fecha_at;
    }

    public String getTipo_at() {
        return tipo_at;
    }

    public void setTipo_at(String tipo_at) {
        this.tipo_at = tipo_at;
    }

    public String getUsuario_cliente() {
        return usuario_cliente;
    }

    public void setUsuario_cliente(String usuario_cliente) {
        this.usuario_cliente = usuario_cliente;
    }

    public String getUsuario_trabajador() {
        return usuario_trabajador;
    }

    public void setUsuario_trabajador(String usuario_trabajador) {
        this.usuario_trabajador = usuario_trabajador;
    }

    public String getCalif() {
        return calif;
    }

    public void setCalif(String calif) {
        this.calif = calif;
    }

    public String getBool_calif() {
        return bool_calif;
    }

    public void setBool_calif(String bool_calif) {
        this.bool_calif = bool_calif;
    }
}
