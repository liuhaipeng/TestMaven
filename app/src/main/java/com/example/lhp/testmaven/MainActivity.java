package com.example.lhp.testmaven;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.core.util.UserManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String test = UserManager.test();

    }
}
