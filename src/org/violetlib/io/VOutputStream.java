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
  Basic output stream operations, excluding {@code close}. A close method is not supported to avoid inadvertent
  incorrect use with transactional output streams.
*/

public interface VOutputStream
{
    void write(int b)
      throws IOException;

    default void write(byte @NotNull [] b)
      throws IOException {
        write(b, 0, b.length);
    }

    void write(byte @NotNull [] b, int off, int len)
      throws IOException;

    void flush()
      throws IOException;
}
