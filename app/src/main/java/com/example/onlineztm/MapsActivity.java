package com.example.onlineztm;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    JSONObject busStopsJson;
    JSONArray stops;
    JsonTask jsonTask;
    BusStops busStops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        jsonTask = new JsonTask();
        busStops = new BusStops();
        stops = new JSONArray();
        try {
            busStopsJson = jsonTask.execute("https://ckan.multimediagdansk.pl/dataset/c24aa637-3619-4dc2-a171-a23eec8f2172/resource/4c4025f0-01bf-41f7-a39f-d156d201b82b/download/stops.json").get();
            stops = busStopsJson.getJSONObject("2020-01-26").getJSONArray("stops");
            busStops.stops = new Stop[stops.length()];
            for(int i = 0; i < stops.length(); i++) {
                busStops.stops[i] = new Stop();
                busStops.stops[i].stopName = stops.getJSONObject(i).getString("stopName");
                busStops.stops[i].stopCode = stops.getJSONObject(i).getString("stopCode");
                busStops.stops[i].stopLat = stops.getJSONObject(i).getDouble("stopLat");
                busStops.stops[i].stopLon = stops.getJSONObject(i).getDouble("stopLon");
            }
            try {

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng tricity = new LatLng(54.410240, 18.580600);
        mMap.addMarker(new MarkerOptions().position(tricity).title("Tricity Center mass"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tricity));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tricity, 15.0f));

        for(int i = 0; i < stops.length(); i++) {
            LatLng location = new LatLng(busStops.stops[i].stopLat, busStops.stops[i].stopLon);
            mMap.addMarker(new MarkerOptions().position(location).title(busStops.stops[i].stopName + busStops.stops[i].stopCode));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public class BusStops {
        private String lastUpdate;
        private Stop[] stops;

        public String getLastUpdate() { return lastUpdate; }
        public void setLastUpdate(String value) { this.lastUpdate = value; }

        public Stop[] getStops() { return stops; }
        public void setStops(Stop[] value) { this.stops = value; }
    }

    public class Stop {
        private Long stopID;
        private String stopCode;
        private String stopName;
        private String stopShortName;
        private String stopDesc;
        private String subName;
        private String date;
        private Long zoneID;
        private ZoneName zoneName;
        private Long virtual;
        private Long nonpassenger;
        private Long depot;
        private Long ticketZoneBorder;
        private Long onDemand;
        private String activationDate;
        private Double stopLat;
        private Double stopLon;
        private String stopURL;
        private Object locationType;
        private Object parentStation;
        private String stopTimezone;
        private Object wheelchairBoarding;

        public Long getStopID() { return stopID; }
        public void setStopID(Long value) { this.stopID = value; }

        public String getStopCode() { return stopCode; }
        public void setStopCode(String value) { this.stopCode = value; }

        public String getStopName() { return stopName; }
        public void setStopName(String value) { this.stopName = value; }

        public String getStopShortName() { return stopShortName; }
        public void setStopShortName(String value) { this.stopShortName = value; }

        public String getStopDesc() { return stopDesc; }
        public void setStopDesc(String value) { this.stopDesc = value; }

        public String getSubName() { return subName; }
        public void setSubName(String value) { this.subName = value; }

        public String getDate() { return date; }
        public void setDate(String value) { this.date = value; }

        public Long getZoneID() { return zoneID; }
        public void setZoneID(Long value) { this.zoneID = value; }

        public ZoneName getZoneName() { return zoneName; }
        public void setZoneName(ZoneName value) { this.zoneName = value; }

        public Long getVirtual() { return virtual; }
        public void setVirtual(Long value) { this.virtual = value; }

        public Long getNonpassenger() { return nonpassenger; }
        public void setNonpassenger(Long value) { this.nonpassenger = value; }

        public Long getDepot() { return depot; }
        public void setDepot(Long value) { this.depot = value; }

        public Long getTicketZoneBorder() { return ticketZoneBorder; }
        public void setTicketZoneBorder(Long value) { this.ticketZoneBorder = value; }

        public Long getOnDemand() { return onDemand; }
        public void setOnDemand(Long value) { this.onDemand = value; }

        public String getActivationDate() { return activationDate; }
        public void setActivationDate(String value) { this.activationDate = value; }

        public Double getStopLat() { return stopLat; }
        public void setStopLat(Double value) { this.stopLat = value; }

        public Double getStopLon() { return stopLon; }
        public void setStopLon(Double value) { this.stopLon = value; }

        public String getStopURL() { return stopURL; }
        public void setStopURL(String value) { this.stopURL = value; }

        public Object getLocationType() { return locationType; }
        public void setLocationType(Object value) { this.locationType = value; }

        public Object getParentStation() { return parentStation; }
        public void setParentStation(Object value) { this.parentStation = value; }

        public String getStopTimezone() { return stopTimezone; }
        public void setStopTimezone(String value) { this.stopTimezone = value; }

        public Object getWheelchairBoarding() { return wheelchairBoarding; }
        public void setWheelchairBoarding(Object value) { this.wheelchairBoarding = value; }
    }

    public enum ZoneName {
        GDASK, GDYNIA, KOLBUDY, PRUSZCZ_GD_GMINA, PRUSZCZ_GD_MIASTO, SOPOT, UKOWO;

        public String toValue() {
            switch (this) {
                case GDASK: return "Gda\u0144sk";
                case GDYNIA: return "Gdynia";
                case KOLBUDY: return "Kolbudy";
                case PRUSZCZ_GD_GMINA: return "Pruszcz Gd. - gmina";
                case PRUSZCZ_GD_MIASTO: return "Pruszcz Gd. - miasto";
                case SOPOT: return "Sopot";
                case UKOWO: return "\u017bukowo";
            }
            return null;
        }

        public static ZoneName forValue(String value) throws IOException {
            if (value.equals("Gda\u0144sk")) return GDASK;
            if (value.equals("Gdynia")) return GDYNIA;
            if (value.equals("Kolbudy")) return KOLBUDY;
            if (value.equals("Pruszcz Gd. - gmina")) return PRUSZCZ_GD_GMINA;
            if (value.equals("Pruszcz Gd. - miasto")) return PRUSZCZ_GD_MIASTO;
            if (value.equals("Sopot")) return SOPOT;
            if (value.equals("\u017bukowo")) return UKOWO;
            throw new IOException("Cannot deserialize ZoneName");
        }
    }

    private class JsonTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            // Do stuff before the operation
        }

        @Override
        protected JSONObject doInBackground(String... params){
            HttpURLConnection urlConnection = null;
            String urlString = params[0];
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                urlConnection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            urlConnection.setReadTimeout(10000 /* milliseconds */ );
            urlConnection.setConnectTimeout(15000 /* milliseconds */ );
            try {
                urlConnection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();

            String line = null;
            while (true) {
                try {
                    if (!((line = br.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append(line);
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String jsonString = sb.toString();
            System.out.println("JSON: " + jsonString);

            try {
                return new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                return new JSONObject(br.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

        }
    }

}

