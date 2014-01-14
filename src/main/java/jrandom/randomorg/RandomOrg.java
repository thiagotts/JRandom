package jrandom.randomorg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jrandom.Service;

public class RandomOrg extends Service {

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

    @Override
    public List<Integer> getIntegers() throws IOException {
        String receivedString = requestNumbersToRandomOrgService();
        return turnStringResponseIntoIntegerArray(receivedString);
    }

    @Override
    protected String getRequestUrl() {
        String format = "http://www.random.org/integers/?num=%d&min=%d&max=%d&col=%d&base=10&format=plain&rnd=new";
        return String.format(format, amount, minValue, maxValue, amount);
    }
    
    @Override
    protected int getBufferSize() {
        int maxAbsolute = Math.max(Math.abs(minValue), Math.abs(maxValue));        
        int amountNeededToPlaceAllNumbers = amount * Integer.toString(maxAbsolute).length();
        int amountNeededToPlaceAllTabs = amount;
        
        return amountNeededToPlaceAllNumbers + amountNeededToPlaceAllTabs;
    }

    @Override
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
