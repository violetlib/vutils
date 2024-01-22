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
  Basic output stream operations that either throw an unchecked exception ({@link IORuntimeException}) or throw no
  exceptions. Intentionally does not support the close method to avoid inadvertent incorrect use with transactional
  output streams.
*/

public interface VUOutputStream
{
    void write(int b)
      throws IORuntimeException;

    default void write(byte @NotNull [] b)
      throws IORuntimeException {
        write(b, 0, b.length);
    }

    void write(byte @NotNull [] b, int off, int len)
      throws IORuntimeException;

    void flush()
      throws IORuntimeException;
}
