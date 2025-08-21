package com.example.myapplication.activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {


    Fragment homefragment;
    ConstraintLayout cart;
    ConstraintLayout  exit;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cart = findViewById(R.id.cart);
        exit = findViewById(R.id.exit);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click event
                Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CartActivity.class); // replace 'CartActivity' with the actual name of your activity
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to navigate to the login activity
                Toast.makeText(MainActivity.this,"Exit",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Login.class); // LoginActivity is the name of your login page's activity.
                startActivity(intent);
                finish(); // This ensures the user can't go back to the MainActivity by pressing the back button.
            }
        });



        homefragment = new HomeFragment();
        loadFragment(homefragment);

    }






    private void loadFragment(Fragment homefragment) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homefragment);
        transaction.commit();
    }
}





