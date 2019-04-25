package pe.progsistem.jesus.ubeprivado.beans;

public class User {
    private String idu;
    private String nombre;
    private String sexo;
    private String fecha_nac;
    private String cel;
    private String cel2;
    private String nom_user;
    private String email;
    private String foto_perfil;
    private String estado;
    private String fecha_inicio;
    private String ultima_vez;
    private String cargo_idc;

    public User(String idu, String nombre, String sexo, String fecha_nac, String cel, String cel2, String nom_user, String email, String foto_perfil, String estado, String fecha_inicio, String ultima_vez, String cargo_idc) {
        this.idu = idu;
        this.nombre = nombre;
        this.sexo = sexo;
        this.fecha_nac = fecha_nac;
        this.cel = cel;
        this.cel2 = cel2;
        this.nom_user = nom_user;
        this.email = email;
        this.foto_perfil = foto_perfil;
        this.estado = estado;
        this.fecha_inicio = fecha_inicio;
        this.ultima_vez = ultima_vez;
        this.cargo_idc = cargo_idc;
    }

    public User() {
    }

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getCel2() {
        return cel2;
    }

    public void setCel2(String cel2) {
        this.cel2 = cel2;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getUltima_vez() {
        return ultima_vez;
    }

    public void setUltima_vez(String ultima_vez) {
        this.ultima_vez = ultima_vez;
    }

    public String getCargo_idc() {
        return cargo_idc;
    }

    public void setCargo_idc(String cargo_idc) {
        this.cargo_idc = cargo_idc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
