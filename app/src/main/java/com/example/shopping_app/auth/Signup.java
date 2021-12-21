package com.example.shopping_app.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping_app.ui.MainActivity;
import com.example.shopping_app.R;
import com.example.shopping_app.db.DbHelper;
import com.example.shopping_app.ui.loginregister;

import org.jetbrains.annotations.NotNull;

public class Signup extends Fragment {

    Context context;
    DbHelper dbHelper;

    public Signup() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText userEditText, passEditText, repassEditText;
        Button signupBtn;
        TextView loginText;
        userEditText = view.findViewById(R.id.usernameinput);
        passEditText = view.findViewById(R.id.passwordinput);
        repassEditText = view.findViewById(R.id.retypepasswordinput);
        signupBtn = view.findViewById(R.id.signupbutton);
        loginText = view.findViewById(R.id.loginlink);

        dbHelper = new DbHelper(context);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userEditText.getText().toString();
                String password = passEditText.getText().toString();
                String repassword = repassEditText.getText().toString();
                if(username.equals("")||password.equals("")||repassword.equals("")){
                    Toast.makeText(getContext(),"Please Enter all field",Toast.LENGTH_LONG).show();
                    Log.e("signup","field empty");
                }
                else {
                    if(password.equals(repassword)){
                        Boolean checkuser = dbHelper.checkExistUser(username);
                        if(!checkuser){
                            Boolean insert = dbHelper.insertData(username,password);
                            if(insert){
                                Toast.makeText(getContext(),"Registered Successfully!",Toast.LENGTH_LONG).show();
                                Log.e("signup","user registered");
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(getContext(),"user already exists!",Toast.LENGTH_LONG).show();
                            Log.e("signup","user exists");
                        }
                    }
                    else {
                        Toast.makeText(getContext(),"passwords not matched",Toast.LENGTH_LONG).show();
                        Log.e("signup","pass not match");
                    }
                }
            }
        });
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, loginregister.loginFragment);
                fragmentTransaction.commit();
                }
            }
        });

    }


}