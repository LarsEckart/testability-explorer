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

import java.io.InputStream;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;

public class CompositeClassPath implements ClassPath {

    private final ClassPath[] classPaths;

    public CompositeClassPath(ClassPath... classPaths) {
        this.classPaths = classPaths;
    }

    @Override
    public boolean isResource(String resource) {
        for (ClassPath classPath : classPaths) {
            if (classPath.isResource(resource)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPackage(String packageName) {
        for (ClassPath classPath : classPaths) {
            if (classPath.isPackage(packageName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String[] listPackages(String packageName) {
        SortedSet<String> packages = new TreeSet<>();
        for (ClassPath classPath : classPaths) {
            packages.addAll(asList(classPath.listPackages(packageName)));
        }
        return packages.toArray(new String[0]);
    }

    @Override
    public String[] listResources(String packageName) {
        SortedSet<String> resources = new TreeSet<>();
        for (ClassPath classPath : classPaths) {
            resources.addAll(asList(classPath.listResources(packageName)));
        }
        return resources.toArray(new String[0]);
    }

    @Override
    public InputStream getResourceAsStream(String resource) {
        for (ClassPath classPath : classPaths) {
            InputStream is = classPath.getResourceAsStream(resource);
            if (is != null) {
                return is;
            }
        }
        return null;
    }

    @Override
    public String[] findResources(
            String rootPackageName,
            ResourceFilter resourceFilter) {
        SortedSet<String> resources = new TreeSet<>();
        for (ClassPath classPath : classPaths) {
            resources.addAll(asList(classPath.findResources(rootPackageName, resourceFilter)));
        }
        return resources.toArray(new String[0]);
    }
}
