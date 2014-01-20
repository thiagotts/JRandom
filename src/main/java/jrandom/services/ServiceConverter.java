package jrandom.services;

import jrandom.CommandLineParameters;
import jrandom.StringConvertible;

public class ServiceConverter implements StringConvertible<Service> {

    CommandLineParameters parameters;

    public ServiceConverter(CommandLineParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public Service convert(String value) {
        switch (value == null ? "" : value) {
            case "hotbits":
                return new HotBits(parameters.amount, parameters.minValue, parameters.maxValue);
                
            default:
                return new RandomOrg(parameters.amount, parameters.minValue, parameters.maxValue);
        }
    }
}
