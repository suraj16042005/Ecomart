package com.example.myapplication.activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.NewProductsModel;
import com.example.myapplication.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button addToCart, buyNow;
    ImageView addItems, removeItems;

    int totalQuantity = 1;
    int totalPrice = 0;

    // New Products
    NewProductsModel newProductsModel = null;

    // Popular Products
    PopularProductsModel popularProductsModel = null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;
        } else if (obj instanceof PopularProductsModel) {
            popularProductsModel = (PopularProductsModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        // New Products
        if (newProductsModel != null) {
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));

            totalPrice = newProductsModel.getPrice() * totalQuantity;
        }
        // Popular Products
        if (popularProductsModel != null) {
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));

            totalPrice = popularProductsModel.getPrice() * totalQuantity;

            //Buy Now
            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start AddAddressActivity when Buy Now is clicked
                    Intent intent = new Intent(DetailedActivity.this, AddressActivity.class);

                    if(newProductsModel != null) {
                        intent.putExtra("item",newProductsModel);
                    }
                    if (popularProductsModel != null) {
                        intent.putExtra("item",popularProductsModel);
                    }

                    startActivity(intent);
                }
            });


            //Add To Cart
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToCart();
                }
            });
        }

        //Add To cart
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProductsModel != null) {
                        totalPrice = newProductsModel.getPrice() * totalQuantity;
                    }
                    if (popularProductsModel != null) {
                        totalPrice = popularProductsModel.getPrice() * totalQuantity;
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity > 1) {  // Changed from totalQuantity > 10 to totalQuantity > 1
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProductsModel != null) {
                        totalPrice = newProductsModel.getPrice() * totalQuantity;
                    }
                    if (popularProductsModel != null) {
                        totalPrice = popularProductsModel.getPrice() * totalQuantity;
                    }
                }
            }
        });
    }

    private void addToCart() {

        {  // Fixed method declaration
            String saveCurrentTime, saveCurrentDate;
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd , yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());

            final HashMap<String, Object> cartMap = new HashMap<>();

            cartMap.put("productName", name.getText().toString());
            cartMap.put("productPrice", price.getText().toString());
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("totalQuantity", quantity.getText().toString());
            cartMap.put("totalPrice", totalPrice);

            firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                    .collection("User")
                    .add(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }
    }
}







