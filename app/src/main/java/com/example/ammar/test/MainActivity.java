package com.example.ammar.test;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import com.example.ammar.test.Model.ModelRead;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utilities.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> productlist;
    ListAdapter adapter;
   ArrayList<Product> product_list = new ArrayList<>();
    String Status=null;
    Activity context;
    ListView listView;
    ProductAdapter productAdapter;

    ApiUtils apiUtils;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productlist = new ArrayList<HashMap<String, String>>();
        listView = (ListView)  findViewById(R.id.list);
       // new GetproductList().execute("http://172.31.158.113:80/Service1.svc/GetProductName");
        //GetProductData();


        showProducts();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent= new Intent(MainActivity.this, AddProduct.class);
                startActivity(intent);

            }
        });
    }

   /* class GetproductList extends AsyncTask<String, Void, String >
    {

        public  GetproductList() {}
        protected void onPreExecute()
        {}


        protected String doInBackground(String... connurl) {
            HttpURLConnection conn = null;
            BufferedReader reader;
            try {

                final URL url = new URL(connurl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type", "application/json; charset=utf-8 ");
                conn.setRequestMethod("GET");
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
                try
                {
                ArrayList<String> stringArrayList = new ArrayList<String>();
                    JSONArray jsonArray= new JSONArray(result);
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject object= jsonArray.getJSONObject(1);
                        String Product= object.getString("Product");
                        HashMap<String , String> itemList = new HashMap<String, String>();
                        itemList.put("Product", Product);
                        productlist.add(itemList);

                    }
                    adapter=new SimpleAdapter(MainActivity.this, productlist,R.layout.productlist, new String[]{"Product"},new int[]{R.id.list});
                    ((AdapterView<ListAdapter>) listView). setAdapter(adapter);


                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
            else
            {

                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        }
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void GetProductData(){
        RequestQueue mRequestQueue;
        // Instantiate the cache

        Cache cache = new DiskBasedCache(MainActivity.this.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        // create url to hit for fetching data
        String url = String.format("http://172.31.158.113:80/Service1.svc/GetProductName");


        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            System.out.println("Response "+ response.toString());

                            // check if response contains any data
                            if(response.length()>10){

                                // creating gson object
                                Gson gson = new Gson();
                                // creating Array list of product
                                product_list = new ArrayList<Product>();
                                //parse response and save it in array list of type Product
                                product_list = gson.fromJson( response,new TypeToken<List<Product>>(){}.getType());
                               // check if product list contains data ,populate it in a list view.
                                if(product_list.size()>0){
                                    productAdapter = new ProductAdapter(MainActivity.this,product_list);
                                    listView.setAdapter(productAdapter);
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this,"No product available! " , Toast.LENGTH_SHORT).show();
                            }


                            }catch (Exception e){
                            Toast.makeText(MainActivity.this,"Please Try Again ! " , Toast.LENGTH_SHORT).show();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(MainActivity.this,"Unable to load Product ! "+error , Toast.LENGTH_SHORT).show();

                    }



                }

        );


        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);

    }





    //--------------------------------------------------------------------------------------------------



    private void showProducts() {


        product_list.clear();


        apiUtils.getSOService().readProduct().enqueue(new Callback<ModelRead>() {

            @Override
            public void onResponse(Call<ModelRead> call, retrofit2.Response<ModelRead> response) {




                ModelRead modelRead;
                if (response.isSuccessful()) {
                    modelRead = response.body();


                    for (int indx = 0; indx < modelRead.getResponse().size() ; indx++) {




                        product_list.add(new Product(Integer.valueOf(modelRead.getResponse().get(indx).getPId()),modelRead.getResponse().get(indx).getPName(), Integer.valueOf(modelRead.getResponse().get(indx).getPQuantity())));
                        productAdapter  =new ProductAdapter(MainActivity.this,product_list);
                        listView.setAdapter(productAdapter);

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Try AGAIN", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRead> call, Throwable t) {

                   Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



}
