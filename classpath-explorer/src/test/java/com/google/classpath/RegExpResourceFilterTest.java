package com.google.classpath;

import junit.framework.TestCase;

import static com.google.classpath.RegExpResourceFilter.ANY;

public class RegExpResourceFilterTest extends TestCase {

    public void testMatchPackage() throws Exception {
        assertFalse(new RegExpResourceFilter("match", ANY).match("X", "X"));
        assertTrue(new RegExpResourceFilter("match", ANY).match("match", "X"));
        assertTrue(new RegExpResourceFilter(ANY, ANY).match("X", "X"));
        assertTrue(new RegExpResourceFilter("match", "match").match("match", "match"));
    }
}
