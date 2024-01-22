/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.io;

import java.io.Writer;

import org.jetbrains.annotations.*;

/**
  A {@link java.io.Writer} that writes to a {@link org.violetlib.io.VUWriter}.
*/

public class WriterFromVUWriter
  extends Writer
{
    public static @NotNull Writer create(@NotNull VUWriter target)
    {
        return new WriterFromVUWriter(target);
    }

    private final @NotNull VUWriter target;

    private WriterFromVUWriter(@NotNull VUWriter target)
    {
        this.target = target;
    }

    @Override
    public void write(char @NotNull [] chars, int offset, int len)
    {
        String s = new String(chars, offset, len);
        target.write(s);
    }

    @Override
    public void flush()
    {
        target.flush();
    }

    /**
      This method has no effect.
    */

    @Override
    public void close()
    {
    }
}
