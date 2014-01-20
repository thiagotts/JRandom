package jrandom.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jrandom.Collectable;

public abstract class Service {

    protected int amount;
    protected Integer minValue;
    protected Integer maxValue;
    protected List<String> warningMessages = new ArrayList<>();
    protected Collectable numberCollector;

    public abstract List<Integer> getIntegers() throws IOException;

    protected abstract String getRequestUrl();

    protected abstract int getBufferSize();

    protected abstract void validateParameters();
    
    protected abstract List<Integer> turnStringResponseIntoIntegerArray(String receivedString) throws IOException;

    public void setNumberCollector(Collectable numberCollector) {
        this.numberCollector = numberCollector;
    }
    
    public List<String> getWarningMessages() {
        return warningMessages;
    }
}
