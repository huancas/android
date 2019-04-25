package pe.progsistem.jesus.ubeprivado.beans;

public class Usuario {
    private int mId;
    private String mName;
    private String mApellido_p;
    private String mApellido_m;
    private String mUsuario;
    private String mEmail;
    private String mEdad;
    private String mDni;
    private String mPassword;
    private String mCelular;
    private String mCelular2;
    private String mFecha_nac;
    private String minicio;
    private String foto_perfil;
    private String foto_dni;
    private String foto_pago;
    private String estado_usuario;
    private String cargo_usuario;
    private String ultima_accion;

    public Usuario() {
    }

    public Usuario(int mId, String mName, String mApellido_p, String mApellido_m, String mUsuario, String mEmail, String mEdad, String mDni, String mPassword, String mCelular, String mCelular2, String mFecha_nac, String minicio, String foto_perfil, String foto_dni, String foto_pago, String estado_usuario, String cargo_usuario, String ultima_accion) {
        this.mId = mId;
        this.mName = mName;
        this.mApellido_p = mApellido_p;
        this.mApellido_m = mApellido_m;
        this.mUsuario = mUsuario;
        this.mEmail = mEmail;
        this.mEdad = mEdad;
        this.mDni = mDni;
        this.mPassword = mPassword;
        this.mCelular = mCelular;
        this.mCelular2 = mCelular2;
        this.mFecha_nac = mFecha_nac;
        this.minicio = minicio;
        this.foto_perfil = foto_perfil;
        this.foto_dni = foto_dni;
        this.foto_pago = foto_pago;
        this.estado_usuario = estado_usuario;
        this.cargo_usuario = cargo_usuario;
        this.ultima_accion = ultima_accion;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmApellido_p() {
        return mApellido_p;
    }

    public void setmApellido_p(String mApellido_p) {
        this.mApellido_p = mApellido_p;
    }

    public String getmApellido_m() {
        return mApellido_m;
    }

    public void setmApellido_m(String mApellido_m) {
        this.mApellido_m = mApellido_m;
    }

    public String getmUsuario() {
        return mUsuario;
    }

    public void setmUsuario(String mUsuario) {
        this.mUsuario = mUsuario;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmEdad() {
        return mEdad;
    }

    public void setmEdad(String mEdad) {
        this.mEdad = mEdad;
    }

    public String getmDni() {
        return mDni;
    }

    public void setmDni(String mDni) {
        this.mDni = mDni;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmCelular() {
        return mCelular;
    }

    public void setmCelular(String mCelular) {
        this.mCelular = mCelular;
    }

    public String getmCelular2() {
        return mCelular2;
    }

    public void setmCelular2(String mCelular2) {
        this.mCelular2 = mCelular2;
    }

    public String getmFecha_nac() {
        return mFecha_nac;
    }

    public void setmFecha_nac(String mFecha_nac) {
        this.mFecha_nac = mFecha_nac;
    }

    public String getMinicio() {
        return minicio;
    }

    public void setMinicio(String minicio) {
        this.minicio = minicio;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getFoto_dni() {
        return foto_dni;
    }

    public void setFoto_dni(String foto_dni) {
        this.foto_dni = foto_dni;
    }

    public String getFoto_pago() {
        return foto_pago;
    }

    public void setFoto_pago(String foto_pago) {
        this.foto_pago = foto_pago;
    }

    public String getEstado_usuario() {
        return estado_usuario;
    }

    public void setEstado_usuario(String estado_usuario) {
        this.estado_usuario = estado_usuario;
    }

    public String getCargo_usuario() {
        return cargo_usuario;
    }

    public void setCargo_usuario(String cargo_usuario) {
        this.cargo_usuario = cargo_usuario;
    }

    public String getUltima_accion() {
        return ultima_accion;
    }

    public void setUltima_accion(String ultima_accion) {
        this.ultima_accion = ultima_accion;
    }
}
