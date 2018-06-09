package com.example.ammar.test;

import android.app.Activity;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.ammar.test.Model.ModelInsert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Utilities.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;

public class AddProduct extends AppCompatActivity {
    String productname = "A", productQuantity = "1";

    EditText edt_name,edt_quantity;

    Button save;

    ApiUtils apiUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt_name = (EditText)findViewById(R.id.txtAddproductname);
        edt_quantity = (EditText)findViewById(R.id.txtAddproductquantity);

        save = (Button) findViewById(R.id.btnadd);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productname = edt_name.getText().toString();
                productQuantity = edt_quantity.getText().toString();

                addProducts(productname,productQuantity);

            }
        });


        //final EditText txtAddproductname = (EditText) findViewById(R.id.txtAddproductname);


//        Button btnadd = (Button) findViewById(R.id.btnadd);
//        btnadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Productname = txtAddproductname.getText().toString();
//                new SaveProduct().execute("http://172.31.158.113:80/Service1.svc/AddProductName");
//
//            }
//        });












    }
        class SaveProduct extends AsyncTask<String, Void, String >
        {
            String Status=null;

            protected void onPreExecute()
            {}


            protected String doInBackground(String... connurl) {
                HttpURLConnection conn = null;
                BufferedReader reader;
                try {

                    final URL url = new URL(connurl[0]);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setChunkedStreamingMode(0);

                    conn.addRequestProperty("Content-Type", "application/json; charset=utf-8 ");
                    conn.setRequestMethod("POST");

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ProductName","A");

                    OutputStream out= new BufferedOutputStream(conn.getOutputStream());
                    out.write(jsonObject.toString().getBytes());
                    out.flush();
                    out.close();

                    int result = conn.getResponseCode();
                    if (result == 200) {
                        InputStream in = new BufferedInputStream(conn.getInputStream());
                        reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            Status = line;

                        }
                    }

                } catch (Exception ex) {

                }
                return Status;
            }
            protected void onPostExecute(String result)
            {

                super.onPostExecute(result);
                if(result!=null)
                {
                    Toast.makeText(AddProduct.this,"Saved",Toast.LENGTH_LONG).show();

                    }



                else
                {

                    Toast.makeText(AddProduct.this,"Error",Toast.LENGTH_LONG).show();
                }
            }
        }

    //send command to the server
    public void addProduct(){

        RequestQueue mRequestQueue;
        // Instantiate the cache

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        String url = String.format("http://172.31.158.113:80/Service1.svc/AddProductName");

        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.d("Login",response.toString());
                    System.out.println("Response "+ response.toString());

                    Handler handler =  new Handler(getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(AddProduct.this, "Product Save!",Toast.LENGTH_LONG).show();
                        }
                    });

                }catch (Exception e){
                    Toast.makeText(AddProduct.this,""+e , Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(AddProduct.this,"Unable to save! " , Toast.LENGTH_SHORT).show();
            }
        }){

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // put all other values of product item if any..
                params.put("ProductName", "aise");
             //   params.put("ProductId", String.valueOf(id));
                return params;
            };
        };

        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);

    }


    //------------------------------------------------------------------------------------------------

    public void addProducts(String name, String quantity)
    {
        apiUtils .getSOService().insertProduct(name,quantity) .enqueue(new Callback<ModelInsert>() {
            @Override
            public void onResponse(Call<ModelInsert> call, retrofit2.Response<ModelInsert> response) {

                ModelInsert modelMessage;
                if (response.isSuccessful()) {
                    modelMessage = response.body();

                    if (modelMessage.getStatusMessage().equals("Successfull")) {


                        edt_name.setText("");
                        edt_quantity.setText("");

                        Toast.makeText(AddProduct.this, "Product Added", Toast.LENGTH_SHORT).show();



                    }else {

                        Toast.makeText(AddProduct.this,modelMessage.getStatusMessage(),Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(AddProduct.this,"Try AGAIN",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ModelInsert> call, Throwable t) {

                Toast.makeText(AddProduct.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }












}

