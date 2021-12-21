package com.example.shopping_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.shopping_app.auth.Login;
import com.example.shopping_app.auth.Signup;
import com.example.shopping_app.db.DbHelper;
import com.example.shopping_app.R;

import java.io.IOException;

public class loginregister extends AppCompatActivity {
    public static Signup signupFragment;
    public static Login loginFragment;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginregister);

        signupFragment = new Signup();
        loginFragment = new Login();

        Toast.makeText(this,"Please Enter all field",Toast.LENGTH_LONG).show();

        dbHelper = new DbHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragmentContainerView, signupFragment)
//                .commit();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, signupFragment, null)
                .commit();



    }
}