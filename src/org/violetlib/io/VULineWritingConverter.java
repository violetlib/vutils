/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.io;

import org.jetbrains.annotations.*;
import org.violetlib.types.IORuntimeException;

/**
  Allows a writer to supply text to a line writer.
*/

public class VULineWritingConverter
  implements VUWriter
{
    /**
      Create a writer that collects the supplied text into lines that are written indivually to the specified line
      writer. The newline character indicates the end of a line.
      <p>
      If the text ends with an unterminated line, that line is passed to the line writer as if it had been terminated,
      provided that {@link #flush} is called after all text has been written. The behavior of {@link #flush} is
      non-standard; it should not be called at any other time.

      @param target The line writer to receive the text lines.
    */

    public static @NotNull VUWriter create(@NotNull VULineWriter target)
    {
        return new VULineWritingConverter(target);
    }

    private final @NotNull VULineWriter target;
    private final @NotNull StringBuilder sb = new StringBuilder();

    private VULineWritingConverter(@NotNull VULineWriter target)
    {
        this.target = target;
    }

    @Override
    public void write(char ch)
      throws IORuntimeException
    {
        if (ch == '\n') {
            finishCurrentLine();
        } else {
            sb.append(ch);
        }
    }

    @Override
    public void write(@NotNull String s)
      throws IORuntimeException
    {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            write(s.charAt(i));
        }
    }

    @Override
    public void newLine()
      throws IORuntimeException
    {
        finishCurrentLine();
    }

    /**
      Calling flush will output the current non-empty partial line as if a newline had been written.
      This method should be called only when there is no more text to be written.
      Effectively, it adds a newline to text that ends with an unterminated line.
    */

    @Override
    public void flush()
      throws IORuntimeException
    {
        if (sb.length() > 0) {
            finishCurrentLine();
        }
    }

    private void finishCurrentLine()
    {
        String line = sb.toString();
        sb.setLength(0);
        target.writeln(line);
    }
}
