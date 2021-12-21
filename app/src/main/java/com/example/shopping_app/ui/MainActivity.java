package com.example.shopping_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping_app.R;
import com.example.shopping_app.Sliding;
import com.example.shopping_app.model.Product;
import com.example.shopping_app.productAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    String url = "https://fakestoreapi.com/products/";
    public static ArrayList<Product> productList = new ArrayList<>();


    RecyclerView productRecycler;
    productAdapter adapter;
    RequestQueue requestQueue;
    Context context;
    Sliding slider;
    Button sortButton;
    int click=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        slider = (Sliding) findViewById(R.id.filterSlider);
        sortButton =findViewById(R.id.sortButton);
        productRecycler = findViewById(R.id.productRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        productRecycler.setLayoutManager(gridLayoutManager);

        slider.setVisibility(View.GONE);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rdogrpFilter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.nameAscRadio:
                        sortNameAsc();
                        break;
                    case R.id.nameDscRadio:
                        sortNameDsc();
                        break;
                    case R.id.priceAscRadio:
                        sortPriceAsc();
                        break;
                    case R.id.priceDscRadio:
                        sortPriceDsc();
                        break;
                    default:
                }
                SlideDisplayHide();

            }
        });
        slider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SlideDisplayHide();

            }
        });
        sortButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                SlideDisplayHide();
            }
        });


        if(productList.size()==0){
            getdata();
        }
        else{
            adapter = new productAdapter(productList, context);
        }
        if (adapter != null)
            productRecycler.setAdapter(adapter);



    }

    private void SlideDisplayHide() {
        if (click == 0) {
            click = 1;
            slider.setVisibility(View.VISIBLE);

        } else if (click == 1) {
            click = 0;
            slider.setVisibility(View.GONE);

        }
    }

    public void getdata() {
        Response.Listener<String> response_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                productList.clear();
                try {
                    JSONArray jsonArray = new JSONArray(response);
//                    JSONObject jsonObject = jsonArray.getJSONObject(0).getJSONObject("address").getJSONObject("geo");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject prodObject = jsonArray.getJSONObject(i);
                        Integer prodId = prodObject.getInt("id");
                        String prodName = prodObject.getString("title");
                        String prodCatname = prodObject.getString("category");
                        Double price = prodObject.getDouble("price");
                        String prodDesc = prodObject.getString("description");
                        String imgUrl = prodObject.getString("image");

                        JSONObject prodImgJArray = prodObject.getJSONObject("rating");

//                        JSONObject imgObject = prodImgJArray.getJSONObject(0);
                        float rate = (float) prodImgJArray.getDouble("rate");
                        int ratecount = prodImgJArray.getInt("count");


                        Product currentProduct = new Product(prodId, prodName,
                                price, prodDesc, prodCatname, imgUrl, rate, ratecount);
                        productList.add(currentProduct);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new productAdapter(productList, context);

                if (adapter != null)
                    productRecycler.setAdapter(adapter);
            }
        };


        Response.ErrorListener response_error_listener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("logerror", error.toString());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //TODO
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    //TODO
                } else if (error instanceof NetworkError) {
                    //TODO
                } else if (error instanceof ParseError) {
                    //TODO
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response_listener, response_error_listener);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(stringRequest);
    }

    public RequestQueue getRequestQueue() {
        //requestQueue is used to stack your request and handles your cache.
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null)
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (click == 1) {
            click = 0;
            slider.setVisibility(View.GONE);
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sortNameAsc(){
        if(productList.size()>0){

        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product lhs, Product rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
        adapter.notifyDataSetChanged();
        }
    }

    public void sortNameDsc(){
        if(productList.size()>0){

            Collections.sort(productList, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return rhs.getTitle().compareTo(lhs.getTitle());
                }
            });
            adapter.notifyDataSetChanged();
        }
    }

    public void sortPriceAsc(){
        if(productList.size()>0){

            Collections.sort(productList, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return Double.compare(lhs.getPrice(), rhs.getPrice());
                }
            });
            adapter.notifyDataSetChanged();
        }
    }

    public void sortPriceDsc(){
        if(productList.size()>0){

            Collections.sort(productList, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return Double.compare(rhs.getPrice(), lhs.getPrice());
                }
            });
            adapter.notifyDataSetChanged();
        }
    }

}