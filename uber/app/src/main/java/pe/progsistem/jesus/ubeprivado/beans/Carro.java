package pe.progsistem.jesus.ubeprivado.beans;

import java.io.Serializable;

public class Carro implements Serializable {
    private String idca;
    private String categoria;
    private  String descripcion;
    private String img_carro;
    private String porcentage;

    public Carro() {
    }

    public Carro(String idca, String categoria, String img_carro, String porcentage) {
        this.idca = idca;
        this.categoria = categoria;
        this.img_carro = img_carro;
        this.porcentage = porcentage;
    }

    public String getIdca() {
        return idca;
    }

    public void setIdca(String idca) {
        this.idca = idca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImg_carro() {
        return img_carro;
    }

    public void setImg_carro(String img_carro) {
        this.img_carro = img_carro;
    }

    public String getPorcentage() {
        return porcentage;
    }

    public void setPorcentage(String porcentage) {
        this.porcentage = porcentage;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
