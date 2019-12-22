package com.cat.miao.view.AdoptFragment;

import java.io.Serializable;

public class AdoptListEntity implements Serializable {

    public String adoptname;
    public String adoptimagepath;
    public String adoptinfo;
    public int adoptid;

    public AdoptListEntity(){}

    public AdoptListEntity(String name, String imagepath,String info){
        this.adoptname = name;
        this.adoptimagepath = imagepath;
        this.adoptinfo = info;

    }

    public String getAdoptname() {
        return adoptname;
    }

    public void setAdoptname(String adoptname) {
        this.adoptname = adoptname;
    }

    public String getAdoptimagepath() {
        return adoptimagepath;
    }

    public void setAdoptimagepath(String adoptimagepath) {
        this.adoptimagepath = adoptimagepath;
    }

    public String getAdoptinfo(){ return adoptinfo; }

    public void setAdoptinfo(String adoptinfo){ this.adoptinfo = adoptinfo; }

    public void setAdoptid(int id){
        this.adoptid = id;
    }

    @Override
    public String toString() {
        return "AdoptListEntity{" +
                "adoptname='" + adoptname + '\'' +
                ", adoptimagepath='" + adoptimagepath + '\'' +
                ", adoptinfo='" + adoptinfo + '\'' +
                '}';
    }
}
