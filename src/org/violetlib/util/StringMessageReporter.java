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
  A message reporter that collects messages in a String. Messages are separated by newlines. Use the {@code messages}
  method to obtain the available messages.
*/

public final class StringMessageReporter
  implements MessageReporter
{
    public static @NotNull StringMessageReporter create()
    {
        return new StringMessageReporter();
    }

    private final @NotNull StringBuilder sb = new StringBuilder();

    private StringMessageReporter()
    {
    }

    @Override
    public void info(@NotNull String message)
    {
        if (sb.length() > 0) {
            sb.append('\n');
        }
        sb.append(message);
    }

    public @NotNull String messages()
    {
        return sb.toString();
    }
}
