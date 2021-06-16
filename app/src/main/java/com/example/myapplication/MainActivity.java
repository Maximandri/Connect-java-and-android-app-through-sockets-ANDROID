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

    private EditText e1, e2;
    private Socket socket = null;

    private static int cores = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executor = Executors.newFixedThreadPool(cores + 1);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.EditText01);
        e2 = (EditText)findViewById(R.id.EditText02);
    }

    public void connect(View v){

        executor.execute(() -> {
            try {
                String host = e1.getText().toString();
                String path = e2.getText().toString();
                socket = new Socket("192.168.1.14", 6000);

                SocketHandler socketHandler = new SocketHandler();
                socketHandler.setSocket(socket);

                Intent intent = new Intent(this, ListFiles.class);
                intent.putExtra("PATH", path);
                startActivity(intent);
            } catch (UnknownHostException e) {
                new Thread()
                {
                    public void run()
                    {
                        MainActivity.this.runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                Toast.makeText(MainActivity.this, "Can´t recognize the host", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }.start();
                e.printStackTrace();
            } catch (ConnectException e) {
                new Thread()
                {
                    public void run()
                    {
                        MainActivity.this.runOnUiThread(() -> Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                }.start();
                e.printStackTrace();
            } catch (IOException e) {
                new Thread()
                {
                    public void run()
                    {
                        MainActivity.this.runOnUiThread(() -> Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                }.start();
                e.printStackTrace();
            }
        });


    }


}
