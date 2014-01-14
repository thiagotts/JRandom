package jrandom.hotbits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jrandom.Service;

public class HotBits extends Service {
    
    private final int ColumnsPerLine = 80;
    private final int MaximumNumberLength = 3;


    public HotBits(int amount) {
        this.amount = Math.abs(amount);
        this.maxValue = 0;
        this.minValue = 0;
    }

    @Override
    public List<Integer> getIntegers() throws IOException {
        String receivedString = requestNumbersToRandomOrgService();
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
    protected List<Integer> turnStringResponseIntoIntegerArray(String receivedString) {
        String[] lines = receivedString.split("\n");
        
        List<Integer> integers = new ArrayList<>();
        
        
        for(int index = 1; index < lines.length - 2; index++ ) {
            String line = lines[index];
            String[] numbers = line.split(",");
            
            for(String number : numbers) {
                int parsedInt = Integer.parseInt(number.trim());
                integers.add(parsedInt);
            }
        }
        
        return integers;
    }

}
