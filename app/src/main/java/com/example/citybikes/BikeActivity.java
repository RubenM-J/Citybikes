package com.example.citybikes;

import static com.example.citybikes.ListActivity.EXTRA_URL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class BikeActivity extends AppCompatActivity {
    public static final String EXTRA_STATIONS = "stations";
    private TextView txtName;
    private TextView txtStations;
    private TextView txtFreespots;
    private ArrayList<Station> stations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stations = new ArrayList<>();
        txtName = (TextView) findViewById(R.id.networkName);
        txtStations = (TextView) findViewById(R.id.bikesavailable);
        txtFreespots = (TextView) findViewById(R.id.freespots);
        ImageButton btnLocation = (ImageButton) findViewById(R.id.showlocation);
        ImageButton btnBack = (ImageButton) findViewById(R.id.backButton);
        Intent intent = getIntent();

        String networkUrl = intent.getStringExtra(EXTRA_URL);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BikeActivity.this,
                        ListActivity.class);
                startActivity(intent);
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BikeActivity.this,
                        MapsActivity.class);
                intent.putExtra(EXTRA_STATIONS, stations);
                startActivity(intent);
            }
        });

        Runnable runnable = new Runnable(){
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(BikeActivity.this);
                String url = "http://api.citybik.es" + networkUrl;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(JSONObject response) {
                                try{
                                    JSONObject network = response.getJSONObject("network");
                                    txtName.setText(network.getString("name"));
                                    txtStations.setText("Stations: " + network.getJSONArray("stations").length());
                                    JSONArray allStations = network.getJSONArray("stations");
                                    for (int i = 0; i < allStations.length(); i++){
                                        stations.add(new Station(allStations.getJSONObject(i)));
                                    }
                                    txtFreespots.setText(network.getString("company"));
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                    Toast.makeText(BikeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(BikeActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                queue.add(jsonObjectRequest);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}