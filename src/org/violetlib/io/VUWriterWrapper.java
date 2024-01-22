/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.io;

import java.io.IOException;
import java.io.Writer;

import org.jetbrains.annotations.*;
import org.violetlib.types.IORuntimeException;

/**
  Wraps a {@link java.io.Writer} to support the {@link org.violetlib.io.VUWriter} interface.
*/

public final class VUWriterWrapper
  implements VUWriter
{
    public static @NotNull VUWriter create(@NotNull Writer w)
    {
        return new VUWriterWrapper(w);
    }

    private static final boolean isNewlineSeparator = System.lineSeparator().equals("\n");

    private @Nullable Writer w;

    private VUWriterWrapper(@Nullable Writer w)
    {
        this.w = w;
    }

    @Override
    public void write(char ch)
    {
        Writer w = check();
        try {
            w.write(ch);
        } catch (IOException ex) {
            throw IORuntimeException.create(ex);
        }
    }

    @Override
    public void write(@NotNull String s)
    {
        Writer w = check();
        try {
            if (isNewlineSeparator) {
                w.write(s);
            } else {
                int offset = 0;
                for (;;) {
                    int i = s.indexOf('\n', offset);
                    if (i < 0) {
                        w.write(s.substring(offset));
                        return;
                    }
                    w.write(s, offset, i-offset);
                    w.write(System.lineSeparator());
                    offset = i+1;
                }
            }
        } catch (IOException ex) {
            throw IORuntimeException.create(ex);
        }
    }

    @Override
    public void newLine()
    {
        Writer w = check();
        try {
            w.write('\n');
        } catch (IOException ex) {
            throw IORuntimeException.create(ex);
        }
    }

    @Override
    public void flush()
    {
        Writer w = check();
        try {
            w.flush();
        } catch (IOException ex) {
            throw IORuntimeException.create(ex);
        }
    }

    private @NotNull Writer check()
    {
        if (w == null) {
            throw new IllegalStateException("Writer has been closed");
        }
        return w;
    }
}
