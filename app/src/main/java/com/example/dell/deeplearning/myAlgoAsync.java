package com.example.dell.deeplearning;

import android.os.AsyncTask;
import android.util.Log;

import com.algorithmia.APIException;
import com.algorithmia.Algorithmia;
import com.algorithmia.AlgorithmiaClient;
import com.algorithmia.algo.AlgoResponse;
import com.algorithmia.algo.Algorithm;

/**
 * Created by Nazim on 24/02/2017.
 */

public abstract class myAlgoAsync <T>  extends AsyncTask<T,Void,AlgoResponse>{

    private String algoUrl;
    private AlgorithmiaClient client;
    private Algorithm algo;

    public myAlgoAsync(String algoUrl) {
        super();

        this.algoUrl = algoUrl;
        this.client = Algorithmia.client(MainActivity.MY_KEY);
        this.algo = client.algo(algoUrl);
    }
    @Override
    protected AlgoResponse doInBackground(T... inputs) {
        if(inputs.length == 1) {
            T input = inputs[0];
            // Call algorithmia
            try {
                AlgoResponse response = algo.pipe(input);
                return response;
            } catch(APIException e) {
                // Connection error
                Log.e("Nazim", "Algorithmia API Exception", e);
                return null;
            }
        } else {
            // Too many inputs
            return null;
        }
    }

}
