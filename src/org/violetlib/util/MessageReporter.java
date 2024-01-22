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

*/

public interface MessageReporter
{
    /**
      Return a message reporter that discards all messages.
    */

    static @NotNull MessageReporter sink()
    {
        return NullReporter.get();
    }

    /**
      Return a message reporter that collects messages in a String. Messages are separated by newlines. Use the {@code
      messages} method to obtain the available messages.
    */

    static @NotNull StringMessageReporter string()
    {
        return StringMessageReporter.create();
    }

    /**
      Report information.

      @param message The message.
    */

    void info(@NotNull String message);
}
