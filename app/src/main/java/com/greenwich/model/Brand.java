package com.greenwich.model;

/**
 * Created by nguye on 2/25/2018.
 *
 */

public class Brand {
    private int id;
    private String brandName;

    public Brand(int id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
