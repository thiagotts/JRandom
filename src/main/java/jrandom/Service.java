package jrandom;

import java.io.IOException;
import java.util.List;

public abstract class Service {

    protected int amount;
    protected int minValue;
    protected int maxValue;
    protected Collectable numberCollector;
    
    public abstract List<Integer> getIntegers() throws IOException;
    
    protected abstract String getRequestUrl();
    
    protected abstract int getBufferSize();
    
    protected abstract List<Integer> turnStringResponseIntoIntegerArray(String receivedString) throws IOException;

    public void setNumberCollector(Collectable numberCollector) {
        this.numberCollector = numberCollector;
    }    
}
