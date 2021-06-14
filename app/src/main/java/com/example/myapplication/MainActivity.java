package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {

    private EditText e1;
    private Socket socket = null;

    private static int cores = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executor = Executors.newFixedThreadPool(cores + 1);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.EditText01);
    }

    public void connect(View v){

        executor.execute(() -> {
            try {
                String host = e1.getText().toString();
                socket = new Socket(host, 6000);

                SocketHandler socketHandler = new SocketHandler();
                socketHandler.setSocket(socket);
            } catch (UnknownHostException e) {
                Toast.makeText(this, "Can´t recognize the host", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (ConnectException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            Intent intent = new Intent(this, ListFiles.class);
            startActivity(intent);
        });


    }


}