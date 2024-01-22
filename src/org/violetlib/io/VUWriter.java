/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.io;

import java.io.PrintWriter;
import java.io.Writer;

import org.jetbrains.annotations.*;
import org.violetlib.types.IORuntimeException;

/**
  Basic writer operations that either throw an unchecked exception ({@link IORuntimeException}) or throw no exceptions.
  Intentionally does not support the close method to avoid inadvertent incorrect use of try with resources.
  <p>
  This interface interprets newline characters as line separators. Implementations that write to files or other system
  devices should map newlines to the appropriate line separator.
*/

@SuppressWarnings("GrazieInspection")
public interface VUWriter
{
    static @NotNull VUWriter from(@NotNull Writer w)
    {
        return VUWriterWrapper.create(w);
    }

    default @NotNull Writer asWriter()
    {
        return WriterFromVUWriter.create(this);
    }

    default @NotNull PrintWriter asPrintWriter()
    {
        return new PrintWriter(asWriter(), true);
    }

    void write(char ch)
      throws IORuntimeException;

    void write(@NotNull String s)
      throws IORuntimeException;

    default void writeln()
      throws IORuntimeException
    {
        newLine();
    }

    default void writeln(@NotNull String s)
      throws IORuntimeException
    {
        write(s);
        newLine();
    }

    void newLine()
      throws IORuntimeException;

    void flush()
      throws IORuntimeException;
}
