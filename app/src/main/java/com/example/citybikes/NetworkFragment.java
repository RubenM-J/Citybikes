package com.example.citybikes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkFragment extends Fragment {
    private static final String ARG_NAME = "name";
    private static final String ARG_STATION = "stations";
    private static final String ARG_COMPANY = "company";

    private String argName;
    private String argStation;
    private String argCompany;

    public static final String EXTRA_STATIONS = "stations";
    public String networkUrl = "";
    public Context context;
    public TextView name;
    public TextView station;
    public TextView id;

    private ArrayList<Station> stations;

    public NetworkFragment() {
    }

    public static NetworkFragment newInstance(String name, String station, String id) {
        NetworkFragment fragment = new NetworkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_STATION, station);
        args.putString(ARG_COMPANY, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            argName = getArguments().getString(ARG_NAME);
            argStation = getArguments().getString(ARG_STATION);
            argCompany = getArguments().getString(ARG_COMPANY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        stations = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        name = view.findViewById(R.id.networkName);
        station = view.findViewById(R.id.freespots);
        id = view.findViewById(R.id.bikesavailable);

        ImageButton imgButton = view.findViewById(R.id.showlocation);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        MapsActivity.class);
                intent.putExtra(EXTRA_STATIONS, stations);
                networkUrl = "";
                startActivity(intent);
            }
        });

        name.setText(ARG_NAME);
        station.setText(ARG_STATION);
        id.setText(ARG_COMPANY);
        return view;
    }

    public void requestNetwork() {
        stations = new ArrayList<>();
        if (!networkUrl.isEmpty() && context != null){
            Runnable runnable = new Runnable(){
                public void run() {

                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url = "http://api.citybik.es" + networkUrl;
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onResponse(JSONObject response) {
                                    try{
                                        JSONObject network = response.getJSONObject("network");
                                        name.setText(network.getString("name"));
                                        station.setText("Stations: " + network.getJSONArray("stations").length());
                                        JSONArray allStations = network.getJSONArray("stations");
                                        for (int i = 0; i < allStations.length(); i++){
                                            stations.add(new Station(allStations.getJSONObject(i)));
                                        }
                                        id.setText(network.getString("company"));
                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                    queue.add(jsonObjectRequest);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        else {
            Toast.makeText(context,networkUrl + ":" + context.toString(), Toast.LENGTH_LONG).show();
        }
    }
}