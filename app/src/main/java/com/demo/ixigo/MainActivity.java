package com.demo.ixigo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.demo.ixigo.adapter.FlightAdapter;
import com.demo.ixigo.utils.Appcontroller;

import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnRequest)
    Button btnRequest;

    @BindView(R.id.recycleIxigo)
    RecyclerView recyclerView;

    Appcontroller appcontroller;
    FlightAdapter adapter;

    HttpURLConnection c = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appcontroller = Appcontroller.getInstance();

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnRequest, R.id.recycleIxigo})
    public void submit(View v){
        switch (v.getId()){
            case R.id.btnRequest:

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                appcontroller.fetchFlights();

                adapter = appcontroller.returnAdapter();

                recyclerView.setAdapter(adapter);
                 break;
        }
    }

}