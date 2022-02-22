package com.example.citybikes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ListActivity extends AppCompatActivity implements NetworkAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "NetworkUrl";
    private ArrayList<Network> ITEMS;
    private RecyclerView rvNetworks;
    private String filter;
    private NetworkFragment networkFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        try {
            networkFragment = (NetworkFragment) getSupportFragmentManager().findFragmentById(R.id.networkFragment);
            networkFragment.context = this;
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT);
        }

        Spinner spinnerCountries =findViewById(R.id.spinnerCountries);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCountries.setAdapter(adapter);

        rvNetworks = (RecyclerView) findViewById(R.id.rvDeals);
        rvNetworks.setHasFixedSize(true);
        rvNetworks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        ITEMS = new ArrayList<>();
        filter = "";

        ImageButton btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,
                        MapsActivity.class);
                startActivity(intent);
            }
        });

        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter = String.valueOf(adapter.getItem(position));
                requestNetworks();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filter = "";
                requestNetworks();
            }
        });
        requestNetworks();
    }

    private void requestNetworks(){
        Runnable runnable = new Runnable(){
            public void run() {
                ITEMS.clear();
                RequestQueue queue = Volley.newRequestQueue(ListActivity.this);
                String url = "http://api.citybik.es/v2/networks";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                JSONArray jsonArray = null;
                                Network parsedNetwork;

                                try{
                                    jsonArray = response.getJSONArray("networks");

                                    for (int i =0; i < jsonArray.length(); i++){
                                        parsedNetwork = new Network(jsonArray.getJSONObject(i));
                                        ITEMS.add(parsedNetwork);
                                    }

                                    if (!filter.isEmpty()){
                                        ArrayList<Network> filtered = new ArrayList<>();
                                        for (Network network : ITEMS){
                                            if (network.country.equals(filter)){
                                                filtered.add(network);
                                            }
                                        }
                                        ITEMS = filtered;
                                    }
                                    final NetworkAdapter adapter = new NetworkAdapter(ListActivity.this, ITEMS);
                                    rvNetworks.setAdapter(adapter);
                                    adapter.setOnItemClickListener(ListActivity.this);
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                queue.add(jsonObjectRequest);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this, BikeActivity.class);
        Network clickedNetwork = ITEMS.get(position);
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            networkFragment.context = this;
            networkFragment.networkUrl = clickedNetwork.href;
            networkFragment.requestNetwork();
        }
        else {
            intent.putExtra(EXTRA_URL, clickedNetwork.href);
            startActivity(intent);
        }
    }
}