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
  A description of parsed user input.

  @param <V> The type of valid values.
*/

public final @Immutable class ValueStatus<V>
{
    /**
      Return a state for valid user input.
    */

    public static @NotNull <V> ValueStatus<V> valid(@NotNull V value)
    {
        return new ValueStatus<>(value, null);
    }

    /**
      Create a state to describe user input that is not valid.

      @param description A description of the problem with the user input.
    */

    public static @NotNull <V> ValueStatus<V> invalid(@NotNull String description)
    {
        return new ValueStatus<>(null, description);
    }

    private final @Nullable V value;
    private final @Nullable String description;

    private ValueStatus(@Nullable V value, @Nullable String description)
    {
        this.value = value;
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
      Return the value, if the user input is valid.
      @return the value, or null if the user input is invalid.
    */

    public @Nullable V value()
    {
        return value;
    }

    /**
      Return a description of user input that is not valid.

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
        if (!(o instanceof ValueStatus)) return false;
        ValueStatus that = (ValueStatus) o;
        return Objects.equals(value, that.value) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(value, description);
    }

    public @NotNull ValidationStatus asValidationStatus()
    {
        return description != null ? ValidationStatus.invalid(description) : ValidationStatus.valid();
    }

    @Override
    public @NotNull String toString()
    {
        if (description != null) {
            return description;
        }
        assert value != null;
        return value.toString();
    }
}
