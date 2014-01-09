package jrandom.randomorg;

import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

public class RandomOrgTests {

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
    public void RequestNumbersMustReturnAValidString() throws IOException {
        RandomOrg randomOrg = new RandomOrg(3, 0, 10);

        String response = randomOrg.requestNumbersToRandomOrgService();

        assertNotNull(response);
        String[] integers = response.replace('\n', ' ').trim().split("\t");
        assertNotNull(integers);
        
        for (String integer : integers) {
            int parsedInt = Integer.parseInt(integer);
            assertNotNull(parsedInt);
        }
    }

    @Test
    public void MustTurnAValidStringResponseIntoAnIntegerArray() {
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
        RandomOrg randomOrg = new RandomOrg(1, 0, 10);

         List<Integer> integers = randomOrg.getIntegers();

        assertNotNull(integers);
        assertEquals(1, integers.size());
        assertTrue(integers.get(0) > 0 && integers.get(0) < 10);        
    }

}
