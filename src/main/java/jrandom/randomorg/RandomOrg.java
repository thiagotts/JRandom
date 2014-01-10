package jrandom.randomorg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RandomOrg {

    private final int amount;
    private final int minValue;
    private final int maxValue;
    

    public RandomOrg(int amount, int minValue, int maxValue) {
        checkIfValuesAreValid(amount, minValue, maxValue);

        this.amount = amount;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private void checkIfValuesAreValid(int amount, int minValue, int maxValue) throws IllegalArgumentException {
        if (amount < 1 || amount > 10000) {
            throw new IllegalArgumentException("The amount must be an integer in the [1,10000] interval.");
        }

        if (minValue < -1000000000 || minValue > 1000000000) {
            throw new IllegalArgumentException("The minimum value must be an integer in the [-1000000000,1000000000] interval.");
        }

        if (maxValue < -1000000000 || maxValue > 1000000000) {
            throw new IllegalArgumentException("The maximum value must be an integer in the [-1000000000,1000000000] interval.");
        }

        if (minValue >= maxValue) {
            throw new IllegalArgumentException("The maximum value must be greater than the minimum value.");
        }
    }

    public List<Integer> getIntegers() throws IOException {
        String receivedString = requestNumbersToRandomOrgService();
        return turnStringResponseIntoIntegerArray(receivedString);
    }

    protected String getRequestUrl() {
        String format = "http://www.random.org/integers/?num=%d&min=%d&max=%d&col=%d&base=10&format=plain&rnd=new";
        return String.format(format, amount, minValue, maxValue, amount);
    }

    protected String requestNumbersToRandomOrgService() throws IOException {
        int maxAbsolute = Math.max(Math.abs(minValue), Math.abs(maxValue));
        char[] buffer = new char[amount * Integer.toString(maxAbsolute).length() + amount];

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

    protected List<Integer> turnStringResponseIntoIntegerArray(String response) {
        String[] strings = response.replace('\n', ' ').trim().split("\t");

        List<Integer> integers = new ArrayList<>();
        for (String string : strings) {
            int parsedInt = Integer.parseInt(string);
            integers.add(parsedInt);
        }

        return integers;
    }

}
