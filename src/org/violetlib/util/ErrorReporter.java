/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import org.jetbrains.annotations.*;

/**
  A simple interface for reporting errors.
*/

public interface ErrorReporter
{
    /**
      Return an error reporter that discards all messages.
    */

    static @NotNull ErrorReporter sink()
    {
        return NullReporter.get();
    }

    /**
      Report an error.

      @param message The error message.
    */

    void error(@NotNull String message);

    /**
      Report an error using a constructed message.

      <p>
      The standard format specifiers are <q>{@code %q}</q>, to include quoted text, <q>{@code %s}</q>, to include
      literal text, and <q>{@code %%}</q>, to include a percent sign.

      @param message The basic error message, which may include format specifiers.
      @param args Arguments to include in the message, based on the format specifiers.
    */

    default void formattedError(@NotNull String message, @NotNull Object... args)
    {
        error(FormattedMessageSupport.constructMessage(false, message, args));
    }

    /**
      Issue a warning.

      @param message The warning message.
    */

    default void warning(@NotNull String message)
    {
        error(message);
    }

    /**
      Issue a warning using a constructed message.

      <p>
      The standard format specifiers are <q>{@code %q}</q>, to include quoted text, <q>{@code %s}</q>, to include
      literal text, and <q>{@code %%}</q>, to include a percent sign.

      @param message The basic error message, which may include format specifiers.
      @param args Arguments to include in the message, based on the format specifiers.
    */

    default void formattedWarning(@NotNull String message, @NotNull Object... args)
    {
        warning(FormattedMessageSupport.constructMessage(false, message, args));
    }
}
