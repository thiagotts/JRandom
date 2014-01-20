package jrandom;

import jrandom.services.Service;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import jrandom.services.HotBits;
import jrandom.services.RandomOrg;
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
        String[] args = new String[]{"-min", "1", "-max", "100", "-a", "10", "-s", "randomorg"};

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
    public void CallingWithRandomOrgAsServiceMustReturnARandomOrgInstance() throws IOException {
        String[] args = new String[]{"-amount", "10", "-min", "1", "-max", "100", "-service", "randomorg"};
        CommandLineParameters parameters = new CommandLineParameters();
        JCommander jCommander = new JCommander(parameters, args);

        Service service = JRandom.getService(parameters);
        
        assertTrue(service instanceof RandomOrg);
    }
    
    @Test
    public void CallingWithHotBitsAsServiceMustReturnAHotBitsInstance() throws IOException {
        String[] args = new String[]{"-amount", "10", "-s", "hotbits"};
        CommandLineParameters parameters = new CommandLineParameters();
        JCommander jCommander = new JCommander(parameters, args);

        Service service = JRandom.getService(parameters);
        
        assertTrue(service instanceof HotBits);
    }    
    
    @Test
    public void CallingWithoutAServiceMustReturnARandomOrgInstance() throws IOException {
        String[] args = new String[]{"-amount", "10", "-min", "1", "-max", "100"};
        CommandLineParameters parameters = new CommandLineParameters();
        JCommander jCommander = new JCommander(parameters, args);

        Service service = JRandom.getService(parameters);
        
        assertTrue(service instanceof RandomOrg);
    }
    
    @Test
    public void CallingWithAnEmptyStringAsServiceMustReturnARandomOrgInstance() throws IOException {
        String[] args = new String[]{"-amount", "10", "-min", "1", "-max", "100", "-s", " "};
        CommandLineParameters parameters = new CommandLineParameters();
        JCommander jCommander = new JCommander(parameters, args);

        Service service = JRandom.getService(parameters);
        
        assertTrue(service instanceof RandomOrg);
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
    public void CallingWithoutMinimumValueForRandomOrgMustThrowParameterException() throws IOException {
        String[] args = new String[]{"-a", "1", "-max", "100"};

        String result = JRandom.run(args);
    }     
    
    @Test(expected = ParameterException.class)
    public void CallingWithoutMaximumValueForRandomOrgMustThrowParameterException() throws IOException {
        String[] args = new String[]{"-a", "1", "-min", "10"};

        String result = JRandom.run(args);
    }   
    
    @Test
    public void CallingWithMinimumValueForHotBitsMustGenerateWarningMessage() throws IOException {
        String[] args = new String[]{"-a", "1", "-min", "10", "-s", "hotbits"};
        CommandLineParameters parameters = new CommandLineParameters();
        JCommander jCommander = new JCommander(parameters, args);

        Service service = JRandom.getService(parameters);
        
        assertEquals("HotBits does not use minimum values. Parameter will be ignored.", service.getWarningMessages().get(0));
    }

    @Test
    public void CallingWithMaximumValueForHotBitsMustGenerateWarningMessage() throws IOException {
        String[] args = new String[]{"-a", "1", "-max", "10", "-s", "hotbits"};
        CommandLineParameters parameters = new CommandLineParameters();
        JCommander jCommander = new JCommander(parameters, args);

        Service service = JRandom.getService(parameters);
        
        assertEquals("HotBits does not use maximum values. Parameter will be ignored.", service.getWarningMessages().get(0));        
    }    

}
