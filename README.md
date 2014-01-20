JRandom
=======
[![Build Status](https://secure.travis-ci.org/thiagotts/JRandom.png)](http://travis-ci.org/thiagotts/JRandom)

JRandom is a collector of [true random numbers][randomness] from free publicly available services. You can use it as
a command line tool or you can integrate it into your system using a very simple API. It currently supports requests
to [Random.org][randomorg] and [HotBits][hotbits].

### Command line

In order to use JRandom as a command line tool, clone this repository or [download it] and extract the files. Then build
it with maven:

    mvn clean compile assembly:single

This will create an executable jar file named something like jrandom-{version}-jar-with-dependencies.jar inside the target
folder. I renamed the file to jrandom.jar to make examples more readable.

Requesting 10 random numbers in the interval [0, 100] to Random.org:

    java -jar jrandom.jar -amount 10 -min 0 -max 100 -service randomorg
    85	73	11	43	96	3	69	47	35	62	

It is possible to use short names for paramaters. Not providing a service uses Random.org as default:

    java -jar jrandom.jar -a 10 -min 0 -max 100
    3	    4	    5	    6	    4

Since HotBits always returns numbers in the [0, 255], max and min parameters are ignored:

    java -jar jrandom.jar -a 5 -min 0 -max 100 -s hotbits
    HotBits does not use minimum values. Parameter will be ignored.
    HotBits does not use maximum values. Parameter will be ignored.
    243	    118	    161	    118	    189	

Not informing a required parameter simply shows an informative message:

    java -jar jrandom.jar -min 0 -max 100
    The following option is required: -amount, -a


### API

To use JRandom in your system, clone this repository or [download it] and extract the files. If you use maven to
build your project, build JRandom like so:

    mvn clean compile package

Then add it as a dependency to your project. If you don't use Maven, add the full jar file with all depencies included as showed
in the previous section.

The following program requests 10 random number in the interval [-10, 10] to Random.org:

```java
import java.io.IOException;
import jrandom.services.HotBits;
import jrandom.services.RandomOrg;

public class JRandomUsage {

    public static void main(String[] args) throws IOException {
        RandomOrg random = new RandomOrg(10, -10, 10);
        System.out.println(random.getIntegers());       
    }
}
```

Which will generate outputs like this one (results must vary, right?):

    [-5, -2, -5, 10, -8, -9, 0, 1, -7, 8]

Similarly, you can use JRandom to request up to 2048 random numbers in the interval [0, 255] to HotBits:

```java
import java.io.IOException;
import jrandom.services.HotBits;
import jrandom.services.RandomOrg;

public class JrandomUsage {

    public static void main(String[] args) throws IOException {
        HotBits hotBits = new HotBits(5);
        System.out.println(hotBits.getIntegers());    
    }
}
```

Which will result in something like:

    [248, 57, 184, 35, 110]

### Quotas

Please notice that all services limit the amount of numbers you can collect daily. These limitations are generally based on your IP address.
Refer to each service documentation to get detailed information about these quotas.


[randomness]: http://www.random.org/randomness/
[randomorg]: http://www.random.org/
[hotbits]: http://www.fourmilab.ch/hotbits/
[download]: https://github.com/thiagotts/JRandom/archive/master.zip
