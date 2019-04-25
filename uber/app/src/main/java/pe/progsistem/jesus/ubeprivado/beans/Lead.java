package pe.progsistem.jesus.ubeprivado.beans;

import java.util.UUID;

public class Lead {
    private String mId;
    private String mName;
    private String mTitle;
    private String mCompany;
    private int mImage;

    public Lead(String mName, String mTitle, String mCompany, int mImage) {
        this.mId = UUID.randomUUID().toString();
        this.mName = mName;
        this.mTitle = mTitle;
        this.mCompany = mCompany;
        this.mImage = mImage;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int mImage) {
        this.mImage = mImage;
    }

    @Override
    public String toString() {
        return "Lead{" +
                "ID='" + mId + '\'' +
                ", Compañia='" + mCompany + '\'' +
                ", Nombre='" + mName + '\'' +
                ", Cargo='" + mTitle + '\'' +
                '}';
    }
}
