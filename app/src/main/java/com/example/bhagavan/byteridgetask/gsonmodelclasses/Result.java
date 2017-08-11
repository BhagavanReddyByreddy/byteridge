package com.example.bhagavan.byteridgetask.gsonmodelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bhagavan on 09-08-2017.
 */

public class Result {

    @SerializedName("subjects")
    @Expose
    public List<Subject> subjects = null;
    @SerializedName("swaggered_layout")
    @Expose
    public List<SwaggeredLayout> swaggeredLayout = null;

}
