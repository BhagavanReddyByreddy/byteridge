package com.example.bhagavan.byteridgetask.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bhagavan.byteridgetask.singleton.AppController;
import com.example.bhagavan.byteridgetask.networkconnectiodetector.ConnectivityReceiver;
import com.example.bhagavan.byteridgetask.R;
import com.example.bhagavan.byteridgetask.adapters.ResultAdapter;
import com.example.bhagavan.byteridgetask.gsonmodelclasses.ResultData;
import com.example.bhagavan.byteridgetask.gsonmodelclasses.Subject;
import com.example.bhagavan.byteridgetask.gsonmodelclasses.SwaggeredLayout;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by bhagavan on 09-08-2017.
 */

public class FragmentTwo  extends Fragment {

    RecyclerView recylerView;
    ResultData resultData;
    ArrayList<Subject> subjects;
    ArrayList<SwaggeredLayout> swaggeredLayouts;
    List<String> layoutsList = new ArrayList<String>();
    private boolean isViewShown = false;

    public FragmentTwo() {

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

        View view = inflater.inflate(R.layout.fragment_two, container, false);
        recylerView = (RecyclerView)view.findViewById(R.id.recyclerView);

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

        String tag_json_obj = "JsonObjectRequest2";

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
                            showList(response.toString());
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

    private void showList(String response) throws JSONException {

        /*Type listType = new TypeToken<ResultData>(){}.getType();
        resultData = new GsonBuilder().create().fromJson(result, listType);*/

        JSONObject resultDataObject = new JSONObject(response);
        String result = resultDataObject.getString("result");
        JSONObject resultObject = new JSONObject(result);
        JSONArray subjectsArray = resultObject.getJSONArray("subjects");
        JSONArray swaggeredLayoutArray = resultObject.getJSONArray("swaggered_layout");

        Type listType1 = new TypeToken<ArrayList<Subject>>(){}.getType();
        subjects = new GsonBuilder().create().fromJson(subjectsArray.toString(), listType1);

        Type listType2 = new TypeToken<ArrayList<SwaggeredLayout>>(){}.getType();
        swaggeredLayouts = new GsonBuilder().create().fromJson(swaggeredLayoutArray.toString(), listType2);

        passToAdapter(swaggeredLayouts);

    }

    private void passToAdapter(ArrayList<SwaggeredLayout> swaggeredLayouts) {
        if (subjects != null) {
            for (SwaggeredLayout layouts : swaggeredLayouts) {
                layoutsList.add(layouts.getKidsUrl());
            }
            ResultAdapter adapter = new ResultAdapter(getActivity(),layoutsList);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
                //recylerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recylerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                recylerView.setAdapter(adapter);
            }

        }
    }
}
