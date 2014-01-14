package jrandom.hotbits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jrandom.NumberCollector;
import jrandom.Service;

public final class HotBits extends Service {
    
    private final int ColumnsPerLine = 80;
    private final int MaximumNumberLength = 3;

    public HotBits(int amount) {
        this.amount = Math.abs(amount);
        if(this.amount > 2048) throw new IllegalArgumentException("The absolute amount value should be less than 2048.");
        
        this.maxValue = 0;
        this.minValue = 0;
        setNumberCollector(new NumberCollector(getRequestUrl(), getBufferSize()));
    }

    @Override
    public List<Integer> getIntegers() throws IOException {
        String receivedString = numberCollector.collect();
        return turnStringResponseIntoIntegerArray(receivedString);
    }

    @Override
    protected String getRequestUrl() {
        String format = "https://www.fourmilab.ch/cgi-bin/Hotbits?fmt=c&nbytes=%d";
        return String.format(format, amount);
    }

    @Override
    protected int getBufferSize() {
        int totalNumbers = amount == 0 ? 256 : amount;
        int amountNeededToPlaceVariableDeclaration = 32;
        int amountNeededToPlaceAllNumbers = totalNumbers * MaximumNumberLength;
        int amountNeededToPlaceSpacesAndCommasBetweenNumbers = totalNumbers * 2;
        int amountNeededToPlaceSpacesBeforeEachLine = (Math.round(amountNeededToPlaceAllNumbers / ColumnsPerLine) + 1) * 5;
        
        return amountNeededToPlaceVariableDeclaration +
               amountNeededToPlaceAllNumbers +
               amountNeededToPlaceSpacesAndCommasBetweenNumbers +
               amountNeededToPlaceSpacesBeforeEachLine;
    }

    @Override
    protected List<Integer> turnStringResponseIntoIntegerArray(String receivedString) throws IOException {
        try {
            if(receivedString.isEmpty()) throw new NumberFormatException("Empty response.");
                     
            List<Integer> integers = new ArrayList<>();
            
            int startIndex = amount > 2048 ? 2 : 1;
            String[] lines = receivedString.split("\n");   
            for (int index = startIndex; index < lines.length - 2; index++) {
                String line = lines[index];
                String[] numbers = line.split(",");
                
                for (String number : numbers) {
                    int parsedInt = Integer.parseInt(number.trim());
                    integers.add(parsedInt);
                }
            }
            
            return integers;
        } catch (NumberFormatException | NullPointerException exception) {
            throw new IOException("Service response was invalid: " + exception.getMessage());
        }
    }

}
