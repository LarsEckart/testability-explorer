/*
 * Copyright 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.classpath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.google.classpath.RegExpResourceFilter.ANY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class ClassPathTest {

    protected ClassPath path;

    @BeforeEach
    protected void setUp() throws Exception {
        path = createClassPath();
    }

    protected abstract ClassPath createClassPath() throws IOException;

    void assertArrayEqualsAnyOrder(String[] actual, String... expected) {
        Set<String> expectedAsSet = new HashSet<>(Arrays.asList(expected));
        assertEquals(
                expectedAsSet.size(), expected.length, "Expected not allowed to contain duplicates for this assertion, but does.");

        String error = String.format(
                "Expected (%s length) not same as Actual (%s length)", expected.length,
                actual.length);
        assertEquals(expected.length, actual.length, error);

        Set<String> actualAsSet = new HashSet<>(Arrays.asList(actual));
        error = String.format("Expected: '%s' Actual: '%s'", expected, actualAsSet);
        assertEquals(actualAsSet, expectedAsSet, error);
    }

    @Test
    void testCheckForResource() {
        assertThat(path.isResource("A/1.file")).isTrue();
        assertThat(path.isResource("/A/1.file")).isTrue();
        assertThat(path.isResource("/A/1.file/")).isFalse();
        assertThat(path.isResource("/A/2.file")).isTrue();
        assertThat(path.isResource("NON_EXISTANT")).isFalse();
        assertThat(path.isResource("A")).isFalse();
        assertThat(path.isResource("/A")).isFalse();
        assertThat(path.isResource("/A/")).isFalse();
        assertThat(path.isPackage("NON_EXISTANT")).isFalse();
    }

    @Test
    void testCheckForPackage() {
        assertThat(path.isPackage("A")).isTrue();
        assertThat(path.isResource("A")).isFalse();
        assertThat(path.isPackage("NON_EXISTANT")).isFalse();
    }

    @Test
    void testListPackages() {
        assertArrayEqualsAnyOrder(path.listPackages(""), "A", "META-INF");
        assertArrayEqualsAnyOrder(path.listPackages("/"), "A", "META-INF");
        assertArrayEqualsAnyOrder(path.listPackages("A"), "B", "C");
        assertArrayEqualsAnyOrder(path.listPackages("/A"), "B", "C");
        assertArrayEqualsAnyOrder(path.listPackages("A/"), "B", "C");
        assertArrayEqualsAnyOrder(path.listPackages("/A/"), "B", "C");
    }

    @Test
    void testListResources() {
        assertArrayEqualsAnyOrder(path.listResources(""));
        assertArrayEqualsAnyOrder(path.listResources("/"));
        assertArrayEqualsAnyOrder(path.listResources("A"), "1.file", "2.file");
        assertArrayEqualsAnyOrder(path.listResources("/A"), "1.file", "2.file");
        assertArrayEqualsAnyOrder(path.listResources("/A/"), "1.file", "2.file");
    }

    @Test
    void testFindResources() {
        RegExpResourceFilter anyFile = new RegExpResourceFilter(ANY, ".*\\.file");
        assertArrayEqualsAnyOrder(path.findResources("X", anyFile));
        assertArrayEqualsAnyOrder(path.findResources("", anyFile), "A/1.file",
                "A/2.file", "A/B/3.file");
    }

    @Test
    void testReadResource() throws Exception {
        assertEquals("FILE1", read(path.getResourceAsStream("A/1.file")));
        assertEquals("FILE1", read(path.getResourceAsStream("/A/1.file")));
        assertNull(path.getResourceAsStream("A/1.file/"));
        assertNull(path.getResourceAsStream("/A/1.file/"));
        assertNull(path.getResourceAsStream("NON_EXISTANT"));
    }

    private String read(InputStream is) throws IOException {
        StringBuilder buf = new StringBuilder();
        int ch;
        while ((ch = is.read()) != -1) {
            buf.append((char) ch);
        }
        is.close();
        return buf.toString();
    }
}
