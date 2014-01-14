package jrandom.hotbits;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

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

    @Test
    public void WhenAmountIsGreaterThan2048TheServerReturns2048Numbers() throws IOException {
        HotBits hotBits = new HotBits(3000);

        List<Integer> integers = hotBits.getIntegers();

        assertNotNull(integers);
        assertEquals(2048, integers.size());
    }

    @Test
    public void WhenAmountIsLessThanMinus2048TheServerReturns2048Numbers() throws IOException {
        HotBits hotBits = new HotBits(-3000);

        List<Integer> integers = hotBits.getIntegers();

        assertNotNull(integers);
        assertEquals(2048, integers.size());
    }

}
