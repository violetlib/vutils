/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import org.jetbrains.annotations.*;
import org.violetlib.annotations.NoInstances;

/**

*/

public final @NoInstances class VObjects
{
    private VObjects()
    {
        throw new AssertionError("VObjects may not be instantiated");
    }

    /**
      Returns true if the arguments are equal to each other and false otherwise. Similar to {@link
      java.util.Objects#equals}, except that null is never passed to an equals method.
    */

    public static boolean equals(@Nullable Object o1, @Nullable Object o2)
    {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }
}
