package com.example.citybikes;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Station implements Parcelable {
    public String Id;
    public String name;
    public String lastUpdate;
    public double latitude;
    public double longitude;
    public int freeBikes;
    public int emptySlots;

    public Station(JSONObject json){
        this.name = isEmptyOrNull(json.optString("name", "unknown"));
        this.lastUpdate = isEmptyOrNull(json.optString("timestamp", "unknown"));
        this.Id = json.optString("id", "unknown");
        this.freeBikes = json.optInt("free_bikes", 0);
        this.emptySlots = json.optInt("empty_slots", 0);
        this.latitude = json.optDouble("latitude", 0);
        this.longitude = json.optDouble("longitude", 0);
    }

    protected Station(Parcel in) {
        name = in.readString();
        lastUpdate = in.readString();
        Id = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        freeBikes = in.readInt();
        emptySlots = in.readInt();
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };

    private String isEmptyOrNull(String str){
        if(str != null && !str.trim().isEmpty()) {
            return str;
        }
        else {
            return "unknown";
        }
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(lastUpdate);
        dest.writeString(Id);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeInt(freeBikes);
        dest.writeInt(emptySlots);
    }
}
