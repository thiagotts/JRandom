package jrandom;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import java.util.List;
import jrandom.randomorg.RandomOrg;

public class JRandom {

    public static void main(String[] args) {
        try {
            System.out.print(run(args)+"\n\n");
        } catch (IOException | IllegalArgumentException | ParameterException ex) {
            System.out.print(ex.getMessage()+"\n\n");
        }
    }

    protected static String run(String[] args) throws IOException {
        CommandLineParameters parameters = new CommandLineParameters();
        JCommander jCommander = new JCommander(parameters, args);
        
        RandomOrg randomOrg = new RandomOrg(parameters.amount, parameters.minValue, parameters.maxValue);
        List<Integer> integers = randomOrg.getIntegers();
        
        StringBuilder result = new StringBuilder();
        for(Integer integer : integers) {
            result.append(integer).append("\t");
        }
        
        return result.toString();
    }
    
    
    
}
