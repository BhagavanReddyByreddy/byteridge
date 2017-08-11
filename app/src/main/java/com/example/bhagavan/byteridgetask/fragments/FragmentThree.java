package com.example.bhagavan.byteridgetask.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bhagavan.byteridgetask.singleton.AppController;
import com.example.bhagavan.byteridgetask.networkconnectiodetector.ConnectivityReceiver;
import com.example.bhagavan.byteridgetask.R;
import com.example.bhagavan.byteridgetask.gsonmodelclasses.ResultData;
import com.example.bhagavan.byteridgetask.gsonmodelclasses.Subject;
import com.example.bhagavan.byteridgetask.gsonmodelclasses.SwaggeredLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by bhagavan on 10-08-2017.
 */

public class FragmentThree  extends Fragment {


    TextView messagetxt;
    ResultData resultData;
    ArrayList<Subject> subjects;
    ArrayList<SwaggeredLayout> swaggeredLayouts;
    List<String> list = new ArrayList<String>();
    private boolean isViewShown = false;

    public FragmentThree() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(checkConnection()) {
            makeApiRequest();
        }else{
            Toast.makeText(getActivity(), "Sorry! Not connected to internet",Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return  isConnected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        messagetxt = (TextView) view.findViewById(R.id.messageTxt);

        if (!isViewShown) {
            if(checkConnection()) {
                makeApiRequest();
            }else{
                Toast.makeText(getActivity(), "Sorry! Not connected to internet",Toast.LENGTH_LONG).show();
            }
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            if(checkConnection()) {
                makeApiRequest();
            }else{
                Toast.makeText(getActivity(), "Sorry! Not connected to internet",Toast.LENGTH_LONG).show();
            }
        } else {
            isViewShown = false;
        }
    }


    private void makeApiRequest() {

        String tag_json_obj = "JsonObjectRequest3";

        String url = "http://www.mocky.io/v2/595dd7561000003a017c17ca";

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        try {
                            showMessage(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    private void showMessage(String response) throws JSONException {


        JSONObject resultDataObject = new JSONObject(response);
        String result = resultDataObject.getString("result");
        JSONObject resultObject = new JSONObject(result);
        JSONArray subjectsArray = resultObject.getJSONArray("subjects");
        JSONArray swaggeredLayoutArray = resultObject.getJSONArray("swaggered_layout");

        boolean errorStatus = resultDataObject.getBoolean("isError");
        int errorCode = resultDataObject.getInt("errorCode");
        String errorMessage = resultDataObject.getString("errorMessage");

        if(errorStatus) {
            messagetxt.setText( errorMessage + "please try again");
        }else{
            messagetxt.setText("Good to Go!");

        }
       /* Type listType1 = new TypeToken<ArrayList<Subject>>(){}.getType();
        subjects = new GsonBuilder().create().fromJson(subjectsArray.toString(), listType1);

        Type listType2 = new TypeToken<ArrayList<SwaggeredLayout>>(){}.getType();
        swaggeredLayouts = new GsonBuilder().create().fromJson(swaggeredLayoutArray.toString(), listType2);*/






    }
}