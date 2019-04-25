package pe.progsistem.jesus.ubeprivado.beans;

public class Fotos {
    private String foto_id;
    private String url_foto;
    private String pub_id;

    public Fotos() {
    }

    public Fotos(String foto_id, String url_foto, String pub_id) {
        this.foto_id = foto_id;
        this.url_foto = url_foto;
        this.pub_id = pub_id;
    }

    public String getFoto_id() {
        return foto_id;
    }

    public void setFoto_id(String foto_id) {
        this.foto_id = foto_id;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getPub_id() {
        return pub_id;
    }

    public void setPub_id(String pub_id) {
        this.pub_id = pub_id;
    }
}
