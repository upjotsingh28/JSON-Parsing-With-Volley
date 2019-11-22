package com.antino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ModelClass> list;
    ApiAdapter apiAdapter;
    private String url = "http://demo8716682.mockable.io/cardData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
        list=new ArrayList<>();
        apiAdapter=new ApiAdapter(list,this);
        recyclerView.setAdapter(apiAdapter);
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                list.clear();
                for (int i =0;i<response.length();i++) {
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        ModelClass modelClass=new ModelClass();
                        modelClass.setName(jsonObject.getString("name"));
                        modelClass.setAge(jsonObject.getString("age"));
                        modelClass.setLocation(jsonObject.getString("location"));
                        modelClass.setUrl(jsonObject.getString("url"));

                        list.add(modelClass);

                    }catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                    apiAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
