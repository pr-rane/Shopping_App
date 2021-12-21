package com.example.shopping_app.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shopping_app.databinding.ActivityProductdetailsBinding;
import com.example.shopping_app.model.Product;
import com.example.shopping_app.ui.MainActivity;

public class ProductDetails extends AppCompatActivity {

    int productId=0;
    Product currentProduct;
    ActivityProductdetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductdetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            productId = extras.getInt("productId");
        }
        if(productId!=0){
            for (Product item : MainActivity.productList) {
                if (item.getId() == productId) {
                    currentProduct = item;
                    break;
                }
            }
        }
        if(currentProduct!=null){

            String imageUrl = currentProduct.getImage();
            double price = currentProduct.getPrice();
            String name = currentProduct.getTitle();
            float rate = currentProduct.getRate();
            int ratecount = currentProduct.getRatecount();
            String description = currentProduct.getDescription();

            Glide.with(this).load(imageUrl).into(binding.productImage);
            binding.productName.setText(name);
            binding.productPrice.setText("₹"+(price));
            binding.productRateCount.setText("("+ratecount+")");
            binding.productRate.setRating(rate);
            binding.productDescription.setText(description);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


}