package jrandom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class NumberCollector implements Collectable {
    
    String requestUrl;
    int bufferSize;

    public NumberCollector(String requestUrl, int bufferSize) {
        this.requestUrl = requestUrl;
        this.bufferSize = bufferSize;
    }    

    @Override
    public String collect() throws IOException {
        char[] buffer = new char[bufferSize];

        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(requestUrl);
            inputStreamReader = new InputStreamReader(url.openStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.read(buffer);

            return new String(buffer);
        } finally {
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }    

}
