/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.io;

import org.jetbrains.annotations.*;

/**
  A writer that collects text in a String. This writer does not throw IO exceptions.
*/

public class VStringWriter
  implements VUWriter
{
    public static @NotNull VStringWriter create()
    {
        return new VStringWriter();
    }

    private @Nullable StringBuilder sb = new StringBuilder();
    private @Nullable String result;

    private VStringWriter()
    {
    }

    @Override
    public void write(char ch)
    {
        StringBuilder sb = check();
        sb.append(ch);
    }

    @Override
    public void write(@NotNull String s)
    {
        StringBuilder sb = check();
        sb.append(s);
    }

    @Override
    public void newLine()
    {
        StringBuilder sb = check();
        sb.append('\n');
    }

    @Override
    public void flush()
    {
    }

    @Override
    public @NotNull String toString()
    {
        return sb != null ? sb.toString() : result;
    }

    private @NotNull StringBuilder check()
    {
        if (sb == null) {
            throw new IllegalStateException("VStringWriter has been closed");
        }
        return sb;
    }
}
