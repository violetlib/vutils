/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import java.util.Properties;

import org.violetlib.annotations.NoInstances;

/**
  Obtain information about the Java plaatform.
*/

public final @NoInstances class JavaPlatform
{
    private JavaPlatform()
    {
        throw new AssertionError("JavaPlatform may not be instantiated");
    }

    private static int javaMajorVersion = obtainJavaMajorVersion();

    /**
     Identify the Java major version.

     @return the Java major version, e.g. 8 (for 1.8) or 9.

     @throws UnsupportedOperationException if the major version cannot be determined.
    */

    public static int getJavaMajorVersion()
            throws UnsupportedOperationException
    {
        if (javaMajorVersion > 0) {
            return javaMajorVersion;
        }
        throw new UnsupportedOperationException("Unable to identify Java major version");
    }

    private static int obtainJavaMajorVersion()
    {
        Properties ps = System.getProperties();
        String s = ps.getProperty("java.version");
        if (s.startsWith("1.")) {
            s = s.substring(2);
        }
        int pos = s.indexOf('.');
        if (pos >= 0) {
            s = s.substring(0, pos);
        }
        if (s.endsWith("-internal")) {
            s = s.substring(0, s.length() - 9);
        } else if (s.endsWith("-ea")) {
            s = s.substring(0, s.length() - 3);
        }
        pos = s.indexOf('.');
        if (pos >= 0) {
            s = s.substring(0, pos);
        }
        try {
            int n = Integer.parseInt(s);
            if (n > 0) {
                return n;
            }
        } catch (NumberFormatException ignore) {
        }
        System.err.println("Unable to identify Java major version from " + ps.getProperty("java.version"));
        return -1;
    }
}
