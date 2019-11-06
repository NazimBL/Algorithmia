package com.example.dell.deeplearning;


import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.algorithmia.APIException;
import com.algorithmia.AlgorithmException;
import com.algorithmia.Algorithmia;
import com.algorithmia.AlgorithmiaClient;
import com.algorithmia.algo.AlgoResponse;
import com.algorithmia.algo.Algorithm;
import com.algorithmia.data.DataAcl;
import com.algorithmia.data.DataDirectory;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public final static String MY_KEY = "sim56Nl98nqyHxvY4ATkDGT3KY31";
    AlgorithmiaClient client;
    Button b;
    TextView textView;
    Handler handler=new Handler();
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greatings();
               // createDataDirectory("file:///assets/deepLearning.txt");
             //summerizeFile();
            }
        });


    }
public void greatings(){

    new Thread(new Runnable() {
        @Override
        public void run() {

            String algorithm="nlp/SentimentAnalysis/1.0.3";
            String in =edit.getText().toString();

            try {

                String input= "{\n"
                        + "  \"document\": \""+in+"\"\n"
                        + "}";
                final AlgoResponse result = client.algo(algorithm).pipeJson(input);
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //  textView.setText(""+response.asJsonString());

                        try {

                            textView.setText(""+result.asJsonString());
                        } catch (AlgorithmException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (APIException e) {
                e.printStackTrace();
            }
        }
    }).start();

}


    public void createDataDirectory(final String pathh) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                DataDirectory directory = client.dir("data://Nazim_BL/directory");
                try {
                    if (!directory.exists()) {

                        directory.create();
                        Log.d("Nazim","data directory created");
                        //private means clients cant interact with my data
                        DataAcl dataAcl=directory.getPermissions();
                        directory.updatePermissions(DataAcl.PRIVATE);
                        Log.d("Nazim","data directory set to private");


                        directory.putFile(new File(pathh));
                         Log.d("Nazim","file saved");
                    }

                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }).start();


    }

    public void summerizeFile(){

                  //  if(client.file(text_file).exists()){
                        Log.d("Nazim","file exists");
                        //String input = client.file(text_file).getString();
    }
    public void init(){

        client = Algorithmia.client(MY_KEY);
        b=(Button)findViewById(R.id.ok);
        textView=(TextView)findViewById(R.id.samurai);
        edit=(EditText)findViewById(R.id.edit);
    }

}