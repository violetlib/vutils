/*
 * Copyright (c) 2023 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import org.jetbrains.annotations.*;

/**
  A simple interface for reporting errors and information.
*/

public interface SimpleReporter
  extends ErrorReporter, MessageReporter
{
    /**
      Return an error reporter that discards all messages.
    */

    static @NotNull SimpleReporter sink()
    {
        return NullReporter.get();
    }

    /**
      Report information using a constructed message.

      <p>
      The standard format specifiers are <q>{@code %q}</q>, to include quoted text, <q>{@code %s}</q>, to include
      literal text, and <q>{@code %%}</q>, to include a percent sign.

      @param message The basic message, which may include format specifiers.
      @param args Arguments to include in the message, based on the format specifiers.
    */

    default void formattedInfo(@NotNull String message, @NotNull Object... args)
    {
        info(FormattedMessageSupport.constructMessage(false, message, args));
    }
}
