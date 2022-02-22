package com.example.citybikes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Network implements Serializable {
    public String company;
    public String href;
    public String name;
    public String id;
    public double lat;
    public double lon;
    public String city;
    public String country;

    public Network(JSONObject network) throws JSONException {
            this.company = network.optString("company", "unknown");
            this.href = network.optString("href", "unknown");
            this.name = network.optString("name", "unknown");
            this.id = network.optString("id", "unknown");
            JSONObject location = network.getJSONObject("location");
            this.country= location.optString("country", "unknown");
            this.city= location.optString("city", "unknown");
            this.lat = location.optDouble("latitude", 0);
            this.lon = location.optDouble("longitude", 0);
    }

    @Override
    public String toString(){
        return company;
    }
}
