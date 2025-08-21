package com.example.myapplication.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.myapplication.R;

public class PaymentActivity extends AppCompatActivity {


    TextView subTotal,discount,shipping,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.payment_toolbar);  // Ensure the ID is correct
        androidx.appcompat.widget.Toolbar payment_toolbar;
        // Set the toolbar as the ActionBar


        double amount  = 0.0;
         amount = getIntent().getDoubleExtra("amount",0.0);


         subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);

        subTotal.setText(amount+"₹");

        Button checkoutButton = findViewById(R.id.pay_btn); // Assuming the ID of the button is pay_btn

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the FinalActivity
                Intent intent = new Intent(PaymentActivity.this, FinalActivity.class);
                startActivity(intent);
            }
        });


    }
}