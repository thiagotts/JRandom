package jrandom;

import com.beust.jcommander.Parameter;

public class CommandLineParameters {

    @Parameter(names = {"-amount", "-a"}, description = "Amount of numbers to request", required = true)
    protected Integer amount = 1;

    @Parameter(names = "-min", description = "Minimum value", required = true)
    protected Integer minValue = 1;

    @Parameter(names = "-max", description = "Maximum value", required = true)
    protected Integer maxValue = 1;
    
    @Parameter(names = {"-service", "-s"}, description = "Service from which to collect random numbers.")
    protected String service;
    
}
