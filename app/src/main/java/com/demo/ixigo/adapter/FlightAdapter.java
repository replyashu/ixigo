package com.demo.ixigo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.ixigo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by apple on 28/03/17.
 */

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    Context context;

    JSONArray flights;
    JSONObject airlines, airports, providers;

    public FlightAdapter(JSONArray flights, JSONObject airlines, JSONObject airports, JSONObject providers){
        this.flights = flights;
        this.airlines = airlines;
        this.airports = airports;
        this.providers = providers;
    }

    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        FlightViewHolder viewHolder;
        context = parent.getContext();
        view = LayoutInflater.from(context).inflate(
                R.layout.flight_item, parent, false);
        viewHolder = new FlightViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FlightViewHolder holder, int position) {

        try {


            holder.txtAirlineCode.setText(flights.getJSONObject(position).getString("airlineCode") + "\n" +
                                airlines.get(flights.getJSONObject(position).getString("airlineCode")));

            holder.txtFlightClass.setText(flights.getJSONObject(position).getString("class") + " ");

            long dateDep = flights.getJSONObject(position).getLong("departureTime");
            long dateArr = flights.getJSONObject(position).getLong("arrivalTime");

            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss");
            final Calendar cal = Calendar.getInstance();

            cal.setTimeInMillis(dateDep);
            String dateString = format.format(cal.getTimeInMillis());
//            String dateString = DateFormat.format("MM/dd/yyyy", new Date(dateDep)).toString();
//            String arrival = DateFormat.format("MM/dd/yyyy", new Date(dateArr)).toString();

            holder.txtDeparture.setText("Departure: " + dateString  + "");

            cal.setTimeInMillis(dateArr);
            String arrival = format.format(cal.getTimeInMillis());

            holder.txtArrival.setText("Arrival: " + arrival + "");

            int len = flights.getJSONObject(position).getJSONArray("fares").length();

            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < len ; i++){
                String name =  providers.get(flights.getJSONObject(position).getJSONArray("fares").getJSONObject(i).getString("providerId")).toString();

                sb.append(name + ": " );
                sb.append(flights.getJSONObject(position).getJSONArray("fares").getJSONObject(i).getString("fare") + "\n");
            }

            holder.txtProvider.setText(sb.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return flights.length();
    }

    public static class FlightViewHolder extends RecyclerView.ViewHolder {

        TextView txtAirlineCode;

        TextView txtFlightClass;

        TextView txtDeparture;

        TextView txtArrival;

        TextView txtProvider;

        TextView txtProviderInfo;

        public FlightViewHolder(View v) {
            super(v);
            txtAirlineCode = (TextView) v.findViewById(R.id.txtAirlineCode);
            txtFlightClass = (TextView) v.findViewById(R.id.txtFlightClass);
            txtDeparture = (TextView) v.findViewById(R.id.txtDeparture);
            txtArrival = (TextView) v.findViewById(R.id.txtArrival);
            txtProvider = (TextView) v.findViewById(R.id.txtProvider);
            txtProviderInfo = (TextView) v.findViewById(R.id.txtProviderInfo);
        }
    }
}