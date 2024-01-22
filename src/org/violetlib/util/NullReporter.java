/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import org.jetbrains.annotations.*;
import org.violetlib.annotations.Immutable;

/**
  A reporter that discards the messages.
*/

public final @Immutable class NullReporter
  implements SimpleReporter
{
    /* package private */ static @NotNull SimpleReporter get()
    {
        return INSTANCE;
    }

    private static final @NotNull NullReporter INSTANCE = new NullReporter();

    private NullReporter()
    {
    }

    @Override
    public void warning(@NotNull String message)
    {
    }

    @Override
    public void error(@NotNull String message)
    {
    }

    @Override
    public void info(@NotNull String message)
    {
    }
}
