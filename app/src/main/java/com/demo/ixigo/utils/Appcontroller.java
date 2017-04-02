package com.demo.ixigo.utils;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.demo.ixigo.adapter.FlightAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by apple on 27/03/17.
 */

public class Appcontroller extends Application{

    private static Appcontroller sInstance;
    FlightAdapter adapter;

    HttpURLConnection c = null;




    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public void fetchFlights(){
        new GetIxigoFlight().execute();

    }

    public static synchronized Appcontroller getInstance() {
        return sInstance;
    }


    private class GetIxigoFlight extends AsyncTask<Void, Void, Void> {

        String result;
        @Override
        protected Void doInBackground(Void... params) {
            String uri = Constant.url + "ixigoandroidchallenge%2Fsample_flight_api_response.json?alt=media&token=d8005801-7878-4f57-a769-ac24133326d6";

            try {
                c = (HttpURLConnection) new URL
                        (uri).openConnection();
                c.setUseCaches(false);
                c.connect();

                int code = c.getResponseCode();
                BufferedReader br;
                String output;
                StringBuilder sb = new StringBuilder();
                if (200 <= c.getResponseCode() && c.getResponseCode() <= 299) {
                    br = new BufferedReader(new InputStreamReader((c.getInputStream())));

                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                } else {
                    br = new BufferedReader(new InputStreamReader((c.getInputStream())));
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                }

                Log.d("responseeee", sb.toString());

                result = sb.toString();

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try {
                JSONObject array = new JSONObject(result);
                JSONArray object = array.getJSONArray("flights");

                Log.d("departureeee", object.length() + "");
                JSONObject airlines = array.getJSONObject("appendix").getJSONObject("airlines");
                JSONObject airports = array.getJSONObject("appendix").getJSONObject("airports");
                JSONObject providers = array.getJSONObject("appendix").getJSONObject("providers");

                adapter = new FlightAdapter(object, airlines, airports, providers);

                Log.d("flights", airlines.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }
//            recyclerView.setAdapter(adapter);

            super.onPostExecute(aVoid);
        }
    }


    public FlightAdapter returnAdapter(){
        return  adapter;
    }
}
