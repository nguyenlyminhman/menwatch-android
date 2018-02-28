package com.greenwich.model;

/**
 * Created by nguye on 2/27/2018.
 *
 */

public class Style {
        private int id;
        private String styleName;

    public Style(int id, String styleName) {
        this.id = id;
        this.styleName = styleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
}
