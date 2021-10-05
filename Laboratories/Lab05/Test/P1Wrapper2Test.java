import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class P1Wrapper2Test {
    P1Wrapper2.List<String> L = null;

    @Before
    public void setUp() throws Exception {
        L = new P1Wrapper2.SinglyLinkedList<String>();

    }



    @Test
    public void testCase1() {
        L.clear();
        L.add("Bob");
        L.changeOddEven();
        assertEquals("On L = {Bob}, L.changeOddEven() fails to produce L = {Bob} ",
                true, L.size() == 1 && L.isMember("Bob"));
    }

    @Test
    public void testCase2() {
        L.clear();
        L.add("Bob");
        L.add("Jil");
        L.changeOddEven();
        assertEquals("On L = {Bob, Jil}, L.changeOddEven() fails to produce L = {Bob, Jil} ",
                true, L.size() == 2 && L.get(1)=="Jil" && L.get(0)=="Bob");
    }



}
