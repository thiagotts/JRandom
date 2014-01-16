package jrandom;

import jrandom.hotbits.HotBits;
import jrandom.randomorg.RandomOrg;

public class ServiceConverter implements StringConvertible<Service> {

    CommandLineParameters parameters;

    public ServiceConverter(CommandLineParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public Service convert(String value) {
        switch (value == null ? "" : value) {
            case "hotbits":
                return new HotBits(parameters.amount);
                
            default:
                return new RandomOrg(parameters.amount, parameters.minValue, parameters.maxValue);
        }
    }
}
