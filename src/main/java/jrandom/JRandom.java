package jrandom;

import jrandom.services.Service;
import jrandom.services.ServiceConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import java.util.List;

public class JRandom {

    public static void main(String[] args) {
        String breakLine = System.lineSeparator() + System.lineSeparator();
        try {
            System.out.print(run(args) + breakLine);
        } catch (IOException | IllegalArgumentException | ParameterException ex) {
            System.out.print(ex.getMessage() + breakLine);
        }
    }

    protected static String run(String[] args) throws IOException {
        CommandLineParameters parameters = getParameters(args);
        Service service = getService(parameters);
        printWarningMessages(service);
        
        List<Integer> integers = service.getIntegers();
        StringBuilder result = new StringBuilder();
        for (Integer integer : integers) {
            result.append(integer).append("\t");
        }

        return result.toString();
    }

    private static CommandLineParameters getParameters(String[] args) {
        CommandLineParameters parameters = new CommandLineParameters();
        JCommander jCommander = new JCommander(parameters, args);
        return parameters;
    }

    protected static Service getService(CommandLineParameters parameters) {
        return new ServiceConverter(parameters).convert(parameters.service);
    }

    private static void printWarningMessages(Service service) {
        for(String message : service.getWarningMessages()) {
            System.out.println(message + System.lineSeparator());
        }
    }
    
}
