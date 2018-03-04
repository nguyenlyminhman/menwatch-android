package com.greenwich.model;

import java.io.Serializable;

/**
 * Created by nguye on 2/27/2018.
 */

public class Product implements Serializable {
    private int id;
    private int idStyle;
    private int idBrand;
    private String name;
    private Double price;
    private String description;
    private String img1;
    private String img2;
    private String img3;
    private String mt;
    private String cs;
    private String sm;
    private String wr;

    public Product(int id, int idStyle, int idBrand, String name, Double price, String description, String img1, String img2, String img3, String mt, String cs, String sm, String wr) {
        this.id = id;
        this.idStyle = idStyle;
        this.idBrand = idBrand;
        this.name = name;
        this.price = price;
        this.description = description;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.mt = mt;
        this.cs = cs;
        this.sm = sm;
        this.wr = wr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdStyle() {
        return idStyle;
    }

    public void setIdStyle(int idStyle) {
        this.idStyle = idStyle;
    }

    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getWr() {
        return wr;
    }

    public void setWr(String wr) {
        this.wr = wr;
    }
}
