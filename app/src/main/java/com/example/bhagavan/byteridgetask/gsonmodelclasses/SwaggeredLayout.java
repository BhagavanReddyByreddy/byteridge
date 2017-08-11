package com.example.bhagavan.byteridgetask.gsonmodelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bhagavan on 09-08-2017.
 */

public class SwaggeredLayout {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKidsUrl() {
        return kidsUrl;
    }

    public void setKidsUrl(String kidsUrl) {
        this.kidsUrl = kidsUrl;
    }

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("kids_url")
    @Expose
    public String kidsUrl;

}
