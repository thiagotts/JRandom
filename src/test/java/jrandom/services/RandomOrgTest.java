package jrandom.services;

import jrandom.services.RandomOrg;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import java.util.List;
import jrandom.NumberCollector;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class RandomOrgTest {
    
    @Test(expected = ParameterException.class)
    public void TheMinimumValueIsRequired() {
        RandomOrg randomOrg = new RandomOrg(-1, null, 10);
    }
    
    @Test(expected = ParameterException.class)
    public void TheMaximumValueIsRequired() {
        RandomOrg randomOrg = new RandomOrg(-1, 1, null);
    }    

    @Test(expected = IllegalArgumentException.class)
    public void TheAmountShouldNotBeLessThanZero() {
        RandomOrg randomOrg = new RandomOrg(-1, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheAmountShouldNotBeZero() {
        RandomOrg randomOrg = new RandomOrg(0, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheAmountShouldNotBeGreaterThan10000() {
        RandomOrg randomOrg = new RandomOrg(10001, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheMinValueShouldNotBeLessThanMinus1000000000() {
        RandomOrg randomOrg = new RandomOrg(1, -1000000001, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheMinValueShouldNotBeGreaterThan1000000000() {
        RandomOrg randomOrg = new RandomOrg(1, 1000000001, 1000000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheMaxValueShouldNotBeLessThanMinus1000000000() {
        RandomOrg randomOrg = new RandomOrg(1, -1000000000, -1000000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheMaxValueShouldNotBeGreaterThan1000000000() {
        RandomOrg randomOrg = new RandomOrg(1, 10, 1000000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheMaxValueMustBeGreaterThanTheMinValue_CaseWhenTheMinValueIsGreaterThanTheMaxValue() {
        RandomOrg randomOrg = new RandomOrg(1, 1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TheMaxValueMustBeGreaterThanTheMinValue_CaseWhenTheMinValueIsEqualToTheMaxValue() {
        RandomOrg randomOrg = new RandomOrg(1, 1, 1);
    }

    @Test
    public void MustCreateAValidRandomOrgInstance_BoundariesCase() {
        RandomOrg randomOrg = new RandomOrg(1, -1000000000, 1000000000);

        assertNotNull(randomOrg);
    }

    @Test
    public void MustCreateAValidRandomOrgInstance_OrdinaryCase() {
        RandomOrg randomOrg = new RandomOrg(1, -1, 1);

        assertNotNull(randomOrg);
    }

    @Test
    public void MustReturnACorrectlyFormedRequestUrl() {
        RandomOrg randomOrg = new RandomOrg(1, 0, 10);

        String requestUrl = randomOrg.getRequestUrl();

        assertNotNull(requestUrl);
        assertEquals("http://www.random.org/integers/?num=1&min=0&max=10&col=1&base=10&format=plain&rnd=new", requestUrl);
    }

    @Test
    public void MustTurnAValidStringResponseIntoAnIntegerArray() throws IOException {
        RandomOrg randomOrg = new RandomOrg(3, 0, 10);
        String response = "1\t2\t3\n";

        List<Integer> result = randomOrg.turnStringResponseIntoIntegerArray(response);

        assertEquals(3, result.size());
        assertEquals(1, (int) result.get(0));
        assertEquals(2, (int) result.get(1));
        assertEquals(3, (int) result.get(2));

    }
    
    @Test
    public void MustReturnASingleIntegerThatsGreaterThanZeroAndLessThan10() throws IOException {
        RandomOrg randomOrg = new RandomOrg(1, 1, 9);

         List<Integer> integers = randomOrg.getIntegers();

        assertNotNull(integers);
        assertEquals(1, integers.size());
        assertTrue(integers.get(0) > 0 && integers.get(0) < 10);        
    }
    
    @Test
    public void MustReturn10IntegersGreaterThan100AndLessThan200() throws IOException {
        RandomOrg randomOrg = new RandomOrg(10, 101, 199);

         List<Integer> integers = randomOrg.getIntegers();

        assertNotNull(integers);
        assertEquals(10, integers.size());
        for(Integer integer : integers) {
            assertTrue(integer > 100 && integer < 200);    
        }      
    }    
    
    @Test(expected = IOException.class)
    public void WhenResponseIsInvalidMustThrowException() throws IOException {
        RandomOrg randomOrg = new RandomOrg(1, 1, 9);
        NumberCollector mockCollector = mock(NumberCollector.class);        
        randomOrg.setNumberCollector(mockCollector);
        
        when(mockCollector.collect()).thenReturn("Test\n for\n invalid\n response\n.\n");
        
        List<Integer> integers = randomOrg.getIntegers();        
    }      
    
    @Test(expected = IOException.class)
    public void IfStringResponseIsEmptyMustThrowException() throws IOException {
        RandomOrg randomOrg = new RandomOrg(1, 1, 9);
        NumberCollector mockCollector = mock(NumberCollector.class);        
        randomOrg.setNumberCollector(mockCollector);
        
        when(mockCollector.collect()).thenReturn("");
        
        List<Integer> integers = randomOrg.getIntegers();
    }    
    
    @Test(expected = IOException.class)
    public void IfStringResponseIsNullMustThrowException() throws IOException {
        RandomOrg randomOrg = new RandomOrg(1, 1, 9);
        NumberCollector mockCollector = mock(NumberCollector.class);        
        randomOrg.setNumberCollector(mockCollector);
        
        when(mockCollector.collect()).thenReturn(null);
        
        List<Integer> integers = randomOrg.getIntegers();
    }     

}
