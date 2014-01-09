package jrandom;

import com.beust.jcommander.ParameterException;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

public class JRandomTest {

    @Test
    public void CallingWithAllParametersFullNamesWithValidValuesMustReturnAValidResponse() throws IOException {
        String[] args = new String[]{"-amount", "10", "-min", "1", "-max", "100"};

        String result = JRandom.run(args);

        assertNotNull(result);
        String[] integers = result.replace('\n', ' ').trim().split("\t");
        assertNotNull(integers);
        assertEquals(10, integers.length);

        for (String integer : integers) {
            int parsedInt = Integer.parseInt(integer);
            assertNotNull(parsedInt);
        }
    }
    
    @Test
    public void CallingWithAllParametersShortNamesWithValidValuesMustReturnAValidResponse() throws IOException {
        String[] args = new String[]{"-min", "1", "-max", "100", "-a", "10"};

        String result = JRandom.run(args);

        assertNotNull(result);
        
        String[] integers = result.replace('\n', ' ').trim().split("\t");
        assertNotNull(integers);
        assertEquals(10, integers.length);

        for (String integer : integers) {
            int parsedInt = Integer.parseInt(integer);
            assertNotNull(parsedInt);
        }
    }
    
    @Test(expected = ParameterException.class)
    public void CallingWithNonExistentParameterMustThrowException() throws IOException {
        String[] args = new String[]{"-yyy", "10", "-min", "1", "-max", "100"};

        String result = JRandom.run(args);
    }
    
    @Test(expected = ParameterException.class)
    public void CallingWithInvalidAmountValueMustThrowException() throws IOException {
        String[] args = new String[]{"-a", "abc", "-min", "1", "-max", "100"};

        String result = JRandom.run(args);
    }   
    
    @Test(expected = ParameterException.class)
    public void CallingWithInvalidMinimumValueMustThrowException() throws IOException {
        String[] args = new String[]{"-a", "1", "-min", "!!", "-max", "100"};

        String result = JRandom.run(args);
    }     
    
    @Test(expected = ParameterException.class)
    public void CallingWithInvalidMaximumValueMustThrowException() throws IOException {
        String[] args = new String[]{"-a", "1", "-min", "10", "-max", "%23"};

        String result = JRandom.run(args);
    }     
    
    @Test(expected = ParameterException.class)
    public void CallingWithNoParametersMustThrowException() throws IOException {
        String[] args = new String[]{};

        String result = JRandom.run(args);
    }
    
    @Test(expected = ParameterException.class)
    public void CallingWithoutAmountMustThrowException() throws IOException {
        String[] args = new String[]{"-min", "10", "-max", "100"};

        String result = JRandom.run(args);
    }    
    
    @Test(expected = ParameterException.class)
    public void CallingWithoutMinimumValueMustThrowException() throws IOException {
        String[] args = new String[]{"-a", "1", "-max", "100"};

        String result = JRandom.run(args);
    }     
    
    @Test(expected = ParameterException.class)
    public void CallingWithoutMaximumValueMustThrowException() throws IOException {
        String[] args = new String[]{"-a", "1", "-min", "10"};

        String result = JRandom.run(args);
    }     

}
