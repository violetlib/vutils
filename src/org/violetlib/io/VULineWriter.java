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
import org.violetlib.types.IORuntimeException;

/**
  A writer that writes individual lines and throws an unchecked exception in case of error.
  Intentionally does not support the close method to avoid inadvertent incorrect use of try with resources.
*/

public interface VULineWriter
{
    default void writeln()
      throws IORuntimeException
    {
        writeln("");
    }

    /**
      Write the specified text as a single line.
      @param s The text, which should not include newlines.
      @throws IORuntimeException
    */

    void writeln(@NotNull String s)
      throws IORuntimeException;

    void flush()
      throws IOException;
}
