package com.example.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketHandler {

    private static Socket socket;
    private static PrintWriter pw;
    private static BufferedReader br;

    private static int cores = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executor = Executors.newFixedThreadPool(cores + 1);

    public static synchronized Socket getSocket(){
        return socket;
    }

    public static synchronized void setSocket(Socket socket){
        SocketHandler.socket = socket;
    }

    public static synchronized void sendString(String... voids){

        String message = voids[0];

        executor.execute(() -> {
            try {
                pw = new PrintWriter(socket.getOutputStream());

                pw.write(message);
                pw.flush();

                pw.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static synchronized List<String> receiveFolders(){
        List<String> folders = new ArrayList<String>();

        executor.execute(() -> {

            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                System.out.println(br.ready());

                String line = br.readLine();
                System.out.println(line);

                while (line != null && line.length() > 0) {
                    folders.add(line);
                    System.out.println(line);
                    line = br.readLine();
                }

                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        return folders;
    }

}
