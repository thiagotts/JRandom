package jrandom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;


public abstract class Service {

    protected int amount;
    protected int minValue;
    protected int maxValue;
    
    public abstract List<Integer> getIntegers() throws IOException;
    
    protected abstract String getRequestUrl();
    
    protected abstract int getBufferSize();
    
    protected abstract List<Integer> turnStringResponseIntoIntegerArray(String receivedString);
    
    protected String requestNumbersToRandomOrgService() throws IOException {
        char[] buffer = new char[getBufferSize()];

        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(getRequestUrl());
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
