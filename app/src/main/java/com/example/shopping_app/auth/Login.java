package com.example.shopping_app.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class Login extends Fragment {

    Context context;
    DbHelper dbHelper;
    public Login() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText userEditText, passEditText;
        Button loginBtn;
        TextView signupText;
        userEditText = view.findViewById(R.id.usernameinput);
        passEditText = view.findViewById(R.id.passwordinput);
        loginBtn = view.findViewById(R.id.loginbutton);
        signupText = view.findViewById(R.id.signuplink);
        dbHelper = new DbHelper(context);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userEditText.getText().toString();
                String password = passEditText.getText().toString();
                if(username.equals("")||password.equals("")){
                    Toast.makeText(context,"Please Enter all field",Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkuser = dbHelper.checkUserDetails(username,password);
                    if (checkuser) {
                        Toast.makeText(context, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);

                    } else {
                        Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                    FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, loginregister.signupFragment);
                    fragmentTransaction.commit();
                }
            }
        });

    }

}