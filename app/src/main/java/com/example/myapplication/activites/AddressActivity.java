package com.example.myapplication.activites;

import android.annotation.SuppressLint;

import com.example.myapplication.models.NewProductsModel;
import com.example.myapplication.models.PopularProductsModel;
import com.google.api.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AddressAdapter;
import com.example.myapplication.models.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress{

  Button addAddress;
  RecyclerView recyclerView;
  private List<AddressModel> addressModelList;
  private AddressAdapter addressAdapter;
  FirebaseFirestore firestore;
  FirebaseAuth auth;
  Button addAddressBtn ,paymentBtn;
  Toolbar toolbar;
  String mAddress = "";

  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_address);


    toolbar = findViewById(R.id.address_toolbar);
    setSupportActionBar(toolbar);

    //get Data from detailed activity
    Object obj = getIntent().getSerializableExtra("item");

    firestore = FirebaseFirestore.getInstance();
    auth = FirebaseAuth.getInstance();

    recyclerView = findViewById(R.id.address_recycler);
    paymentBtn = findViewById(R.id.payment_btn);
    addAddressBtn = findViewById(R.id.add_address_btn);


    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    addressModelList = new ArrayList<>();
    addressAdapter = new AddressAdapter(getApplicationContext(),addressModelList,this);
    recyclerView.setAdapter(addressAdapter);

    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
            .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                  for (DocumentSnapshot doc : task.getResult().getDocuments()) {

                    AddressModel addressModel = doc.toObject(AddressModel.class);
                    addressModelList.add(addressModel);
                    addressAdapter.notifyDataSetChanged();

                  }
                }
              }
            });

    paymentBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        double amount = 0.0;
        if (obj instanceof NewProductsModel) {
          NewProductsModel newProductsModel = (NewProductsModel) obj;
          amount = newProductsModel.getPrice();
        }
        if (obj instanceof PopularProductsModel) {
          PopularProductsModel popularProductsModel = (PopularProductsModel) obj;
          amount = popularProductsModel.getPrice();
        }
        Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
        intent.putExtra("amount",amount);
        startActivity(intent);
      }
    });

    addAddressBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(AddressActivity.this,AddressActivity.class));
      }

    });


    addAddress = findViewById(R.id.add_address_btn);

    addAddress.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));

      }
    });
  }

  @Override
  public void setAddress(String address) {

    mAddress = address;
  }
}