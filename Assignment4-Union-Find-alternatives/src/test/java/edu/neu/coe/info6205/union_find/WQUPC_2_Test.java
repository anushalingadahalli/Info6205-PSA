package edu.neu.coe.info6205.union_find;

import edu.neu.coe.info6205.util.PrivateMethodTester;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class WQUPC_2_Test {
    @Test
    public void testToString() {
        WQUPC_2 h = new WQUPC_2(2,true);
        assertEquals("WQUPC_2 : weighted quick union with path compression" +
                "\n count: 2" +
                "\n parents: [0, 1]" +
                "\n sizes: [1, 1]", h.toString());
    }

    /**
     *
     */
    @Test
    public void testIsConnected01() {
        WQUPC_2 h = new WQUPC_2(2,true);
        assertFalse(h.connected(0, 1));
    }

    /**
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsConnected02() {
        WQUPC_2 h = new WQUPC_2(1,true);
        assertTrue(h.connected(0, 1));
    }


    /**
     *
     */
    @Test
    public void testConnect01() {
        WQUPC_2 h = new WQUPC_2(2,true);
        h.union(0, 1);
    }

    /**
     *
     */
    @Test
    public void testConnect02() {
        WQUPC_2 h = new WQUPC_2(2,true);
        h.union(0, 1);
        h.union(0, 1);
        assertTrue(h.connected(0, 1));
    }

    /**
     *
     */
    @Test
    public void testFind0() {
        WQUPC_2 h = new WQUPC_2(1,true);
        assertEquals(0, h.find(0));
    }

    /**
     *
     */
    @Test
    public void testFind1() {
        WQUPC_2 h = new WQUPC_2(2,true);
        h.union(0, 1);
        assertEquals(0, h.find(0));
        assertEquals(0, h.find(1));
    }

    /**
     *
     */
    @Test
    public void testFind2() {
        WQUPC_2 h = new WQUPC_2(3,true);
        h.union(0, 1);
        assertEquals(0, h.find(0));
        assertEquals(0, h.find(1));
        h.union(2, 1);
        assertEquals(0, h.find(0));
        assertEquals(0, h.find(1));
        assertEquals(0, h.find(2));
    }

    /**
     *
     */
    @Test
    public void testFind3() {
        WQUPC_2 h = new WQUPC_2(6,false);
        h.union(0, 1);
        h.union(0, 2);
        h.union(3, 4);
        h.union(3, 5);
        assertEquals(0, h.find(0));
        assertEquals(0, h.find(1));
        assertEquals(0, h.find(2));
        assertEquals(3, h.find(3));
        assertEquals(3, h.find(4));
        assertEquals(3, h.find(5));
        h.union(0, 3);
        assertEquals(0, h.find(0));
        assertEquals(0, h.find(1));
        assertEquals(0, h.find(2));
        assertEquals(0, h.find(3));
        assertEquals(0, h.find(4));
        assertEquals(0, h.find(5));
        final PrivateMethodTester tester = new PrivateMethodTester(h);
        assertEquals(3, tester.invokePrivate("getParent", 4));
        assertEquals(3, tester.invokePrivate("getParent", 5));
    }


    /**
     *
     */
    @Test
    public void testFind5() {
        WQUPC_2 h = new WQUPC_2(2,true);
        h.find(1);
    }


    /**
     *
     */
    @Test
    public void testConnected01() {
        WQUPC_2 h = new WQUPC_2(10,false);
//        h.show();
        assertFalse(h.connected(0, 1));
    }
}
