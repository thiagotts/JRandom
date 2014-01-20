package jrandom;

import com.beust.jcommander.Parameter;

public class CommandLineParameters {

    @Parameter(names = {"-amount", "-a"}, description = "Amount of numbers to request", required = true)
    public Integer amount = 1;

    @Parameter(names = "-min", description = "Minimum value")
    public Integer minValue;

    @Parameter(names = "-max", description = "Maximum value")
    public Integer maxValue;
    
    @Parameter(names = {"-service", "-s"}, description = "Service from which to collect random numbers.")
    public String service;
    
}
