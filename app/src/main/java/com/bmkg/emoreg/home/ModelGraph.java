package com.bmkg.emoreg.home;

import org.json.JSONArray;

/**
 * Created by Minami on 7/25/2018.
 */

public class ModelGraph extends Combined {
    JSONArray ja;


    public ModelGraph(JSONArray ja) {
        this.ja = ja;
    }

    public JSONArray getJa() {
        return ja;
    }

    public void setJa(JSONArray ja) {
        this.ja = ja;
    }
}
