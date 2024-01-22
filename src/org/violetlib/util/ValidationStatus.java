/*
 * Copyright (c) 2022 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import java.util.Objects;

import org.jetbrains.annotations.*;
import org.violetlib.annotations.Immutable;

/**
  A description of the validity of user input.
*/

public final @Immutable class ValidationStatus
{
    private static final @NotNull ValidationStatus VALID_INSTANCE = new ValidationStatus();

    /**
      Return the validation state indicating that the user input is valid.
      There is only one such state.
    */

    public static @NotNull ValidationStatus valid()
    {
        return VALID_INSTANCE;
    }

    /**
      Create a state to describe user input that is not valid.

      @param description A description of the problem with the user input.
    */

    public static @NotNull ValidationStatus invalid(@NotNull String description)
    {
        return new ValidationStatus(description);
    }

    private final @Nullable String description;

    private ValidationStatus()
    {
        this.description = null;
    }

    /**
      Create a state to describe user input that is not valid.

      @param description A description of the problem with the user input.
    */

    private ValidationStatus(@NotNull String description)
    {
        this.description = description;
    }

    /**
      Indicate whether the user input is valid or not.

      @return true if and only if the input is valid.
    */

    public boolean isValid()
    {
        return description == null;
    }

    /**
      Return a description of a user input that is not valid.

      @return the description, or null if the input is valid.
    */

    public @Nullable String description()
    {
        return description;
    }

    @Override
    public boolean equals(@Nullable Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ValidationStatus)) return false;
        ValidationStatus that = (ValidationStatus) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(description);
    }

    @Override
    public @NotNull String toString()
    {
        return description != null ? description : "Valid";
    }
}
