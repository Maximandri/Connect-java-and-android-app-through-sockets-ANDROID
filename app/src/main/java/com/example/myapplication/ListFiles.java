package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListFiles extends AppCompatActivity{

    private static int cores = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executor = Executors.newFixedThreadPool(cores + 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);
        listFiles();
    }

    public void listFiles(){
        executor.execute(() -> {
            for (int i = 1; i <= 5; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                Button btn = new Button(this);
                btn.setId(i);
                final int id_ = btn.getId();
                btn.setText("button " + id_);

                LinearLayout linear = ((LinearLayout) findViewById(R.id.linear));
                linear.addView(btn);

                btn = ((Button) findViewById(id_));
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(),
                                "Button clicked index = " + id_, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });
    }
}