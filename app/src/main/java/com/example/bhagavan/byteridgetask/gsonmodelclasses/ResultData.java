package com.example.bhagavan.byteridgetask.gsonmodelclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bhagavan on 09-08-2017.
 */

public class ResultData {

    @SerializedName("isError")
    @Expose
    public Boolean isError;
    @SerializedName("errorCode")
    @Expose
    public Integer errorCode;
    @SerializedName("errorMessage")
    @Expose
    public Object errorMessage;
    @SerializedName("result")
    @Expose
    public Result result;

}
