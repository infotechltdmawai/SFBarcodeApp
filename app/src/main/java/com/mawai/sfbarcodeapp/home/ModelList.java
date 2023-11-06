package com.mawai.sfbarcodeapp.home;

public class ModelList {

    public int id;
    public int userid;

    public String name;

    public int image;
     public int url;

    public ModelList(int id, int userid, String name, int image, int url) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.image = image;
        this.url = url;
    }

    public ModelList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
