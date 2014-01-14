package jrandom.hotbits;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import jrandom.NumberCollector;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class HotBitsTest {
    
    @Test
    public void MustReturnACorrectlyFormedRequestUrl() {
        HotBits hotBits = new HotBits(100);

        String requestUrl = hotBits.getRequestUrl();

        assertNotNull(requestUrl);
        assertEquals("https://www.fourmilab.ch/cgi-bin/Hotbits?fmt=c&nbytes=100", requestUrl);
    }    

    @Test
    public void ServerMustReturn100NumbersBetweenZeroAnd255() throws IOException {
        HotBits hotBits = new HotBits(100);

        List<Integer> integers = hotBits.getIntegers();

        assertNotNull(integers);
        assertEquals(100, integers.size());
        assertTrue(Collections.max(integers) <= 255);
        assertTrue(Collections.min(integers) >= 0);
    }

    @Test
    public void NegativeAmountAreTreatedAsPositives() throws IOException {
        HotBits hotBits = new HotBits(-5);

        List<Integer> integers = hotBits.getIntegers();

        assertNotNull(integers);
        assertEquals(5, integers.size());
    }

    @Test
    public void WhenAmountEqualsZeroTheServerReturns256Numbers() throws IOException {
        HotBits hotBits = new HotBits(0);

        List<Integer> integers = hotBits.getIntegers();

        assertNotNull(integers);
        assertEquals(256, integers.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheAmountCannotBeGreaterThan2048() throws IOException {
        HotBits hotBits = new HotBits(3000);

        List<Integer> integers = hotBits.getIntegers();
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheAmountCannotBeessThanMinus2048() throws IOException {
        HotBits hotBits = new HotBits(-3000);

        List<Integer> integers = hotBits.getIntegers();
    }
    
    @Test(expected = IOException.class)
    public void WhenResponseIsInvalidMustThrowException() throws IOException {
        HotBits hotBits = new HotBits(5);
        NumberCollector mockCollector = mock(NumberCollector.class);        
        hotBits.setNumberCollector(mockCollector);
        
        when(mockCollector.collect()).thenReturn("Test\n for\n invalid\n response\n.\n");
        
        List<Integer> integers = hotBits.getIntegers();        
    }    
    
    @Test(expected = IOException.class)
    public void IfStringResponseIsEmptyMustThrowException() throws IOException {
        HotBits hotBits = new HotBits(5);
        NumberCollector mockCollector = mock(NumberCollector.class);        
        hotBits.setNumberCollector(mockCollector);
        
        when(mockCollector.collect()).thenReturn("");
        
        List<Integer> integers = hotBits.getIntegers();
    }    
    
    @Test(expected = IOException.class)
    public void IfStringResponseIsNullMustThrowException() throws IOException {
        HotBits hotBits = new HotBits(5);
        NumberCollector mockCollector = mock(NumberCollector.class);        
        hotBits.setNumberCollector(mockCollector);
        
        when(mockCollector.collect()).thenReturn(null);
        
        List<Integer> integers = hotBits.getIntegers();
    }      

}
