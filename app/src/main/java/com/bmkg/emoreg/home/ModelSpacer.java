package com.bmkg.emoreg.home;

/**
 * Created by Minami on 7/25/2018.
 */

public class ModelSpacer extends Combined {

    String text;

    public ModelSpacer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
