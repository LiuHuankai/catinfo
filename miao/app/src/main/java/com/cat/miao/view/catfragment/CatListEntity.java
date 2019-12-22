package com.cat.miao.view.catfragment;

import java.io.Serializable;

public class CatListEntity implements Serializable {

    public String catname;
    public String catimagepath;
    public int catid;

    public CatListEntity(){}

    public CatListEntity(String name, String imagepath){
        this.catname = name;
        this.catimagepath = imagepath;

    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCatimagepath() {
        return catimagepath;
    }

    public void setCatimagepath(String catimagepath) {
        this.catimagepath = catimagepath;
    }

    public void setCatid(int id){
        this.catid = id;
    }

    @Override
    public String toString() {
        return "GoodsEntity{" +
                "catname='" + catname + '\'' +
                ", catimagepath='" + catimagepath + '\'' +

                '}';
    }
}
