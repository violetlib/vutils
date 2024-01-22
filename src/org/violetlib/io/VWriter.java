/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.io;

import java.io.IOException;

import org.jetbrains.annotations.*;

/**
  Basic writer operations that throw {@link java.io.IOException}.
  Intentionally does not support the close method to avoid inadvertent incorrect use with
  transactional writers.
  <p>
  This interface interprets newline characters as line separators. Implementations that write to files or other system
  devices should map newlines to the appropriate line separator.
*/

@SuppressWarnings("GrazieInspection")
public interface VWriter
{
    void write(char ch)
      throws IOException;

    void write(@NotNull String s)
      throws IOException;

    default void writeln()
      throws IOException
    {
        newLine();
    }

    default void writeln(@NotNull String s)
      throws IOException
    {
        write(s);
        newLine();
    }

    void newLine()
      throws IOException;

    void flush()
      throws IOException;
}
