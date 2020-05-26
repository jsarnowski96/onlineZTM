package com.example.onlineztm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class TimetableActivity extends AppCompatActivity {

    private Spinner busStopSpinner;
    private JSONObject busStopsJson;
    private JSONArray stops;
    private BusStops busStops;
    private JsonTask jsonTask;
    private ArrayList<String> busStopsStringArray;
    private Button confirmationButton;
    private Button cancelButton;
    private PopupWindow popUp;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        busStopSpinner = findViewById(R.id.busStopSpinner);
        busStopsStringArray = new ArrayList<String>();

        /*sharedPreferences = getPreferences(MODE_PRIVATE);
        if(sharedPreferences.getString("any_field", "default") == null) {
            getBusStops();
        }
        else {
            Gson gson = new Gson();
            Map<String,?> allEntries = sharedPreferences.getAll();
            busStops = new TimetableActivity.BusStops();
            stops = new JSONArray();
            for(Map.Entry<String,?> entry : allEntries.entrySet()) {
                String json = sharedPreferences.getString("busStop" + entry.getKey(), "");
                busStops = gson.fromJson(json, BusStops.class);
            }
        }*/

        getBusStops();

        Collections.sort(busStopsStringArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busStopsStringArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        busStopSpinner.setAdapter(adapter);

        busStopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TimetableActivity.this, "Selected: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirmationButton = findViewById(R.id.confirmationButton);
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TimetableActivity.this, "Confirmed selection for item: " + busStopSpinner.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        for(int i = 0; i < busStops.stops.length; i++) {
            String json = gson.toJson(busStops.stops[i]);
            editor.putString("busStop" + i, json);
        }
        editor.commit();*/
    }

    public class BusStops {
        private String lastUpdate;
        private TimetableActivity.Stop[] stops;

        public String getLastUpdate() { return lastUpdate; }
        public void setLastUpdate(String value) { this.lastUpdate = value; }

        public TimetableActivity.Stop[] getStops() { return stops; }
        public void setStops(TimetableActivity.Stop[] value) { this.stops = value; }
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
        private TimetableActivity.ZoneName zoneName;
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

        public TimetableActivity.ZoneName getZoneName() { return zoneName; }
        public void setZoneName(TimetableActivity.ZoneName value) { this.zoneName = value; }

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

        public static TimetableActivity.ZoneName forValue(String value) throws IOException {
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
        protected JSONObject doInBackground(String... params) {
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
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
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

    private void getBusStops() {
        jsonTask = new TimetableActivity.JsonTask();
        busStops = new TimetableActivity.BusStops();
        stops = new JSONArray();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        try {
            busStopsJson = jsonTask.execute("https://ckan.multimediagdansk.pl/dataset/c24aa637-3619-4dc2-a171-a23eec8f2172/resource/4c4025f0-01bf-41f7-a39f-d156d201b82b/download/stops.json").get();
            stops = busStopsJson.getJSONObject(df.format(date)).getJSONArray("stops");
            busStops.stops = new TimetableActivity.Stop[stops.length()];
            for(int i = 0; i < stops.length(); i++) {
                busStops.stops[i] = new TimetableActivity.Stop();
                if(stops.getJSONObject(i).getString("stopName") == "null" && stops.getJSONObject(i).getString("stopCode") == "null") {
                    busStops.stops[i].stopName = stops.getJSONObject(i).getString("stopDesc");
                }
                else {
                    busStops.stops[i].stopName = stops.getJSONObject(i).getString("stopName");
                }
                busStopsStringArray.add(busStops.stops[i].stopName.toString());
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
}