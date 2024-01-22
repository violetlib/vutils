/*
 * Copyright (c) 2022 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import org.jetbrains.annotations.*;

/**
  Obtain an object extension that is required. If the object is an instance of the designated class or interface, it
  is returned. Otherwise, if the object supports the {@link Extensible} interface, that service is used to locate
  the requested extension.

  @param o The object.

  @param c The class or interface that designates the extension.

  @return the extension. If not found, throws an AssertionError.
*/

public class Extensions
{
    public static <T> @NotNull T getRequiredExtension(@Nullable Object o, @NotNull Class<T> c)
    {
        T extension = getExtension(o, c);
        if (extension == null) {
            throw new AssertionError("Required extension not found: " + c.getName());
        }
        return extension;
    }

    /**
      Obtain an object extension, if supported. If the object is an instance of the designated class or interface, it
      is returned. Otherwise, if the object supports the {@link Extensible} interface, that service is used to locate
      the requested extension.

      @param o The object.

      @param c The class or interface that designates the extension.

      @return the extension, if supported, or null.
    */

    public static <T> @Nullable T getExtension(@Nullable Object o, @NotNull Class<T> c)
    {
        if (o == null) {
            return null;
        }

        /*
          I have found that great confusion can result if instance testing is not performed first. An object that
          implements getExtension() cannot be assumed to recognize its own class, or a class/interface that it supports
          directly.
        */

        if (c.isInstance(o)) {
            return c.cast(o);
        }

        if (o instanceof Extensible) {
            Extensible x = (Extensible) o;
            return x.getExtension(c);
        }

        return null;
    }

    /**
      Determine whether an object supports the specified extension.

      @param o The object.

      @param c The class or interface that designates the extension.

      @return true if {@code o} supports the extension designated by {@code c}, otherwise false.
    */

    public static boolean supports(@Nullable Object o, @NotNull Class<?> c)
    {
        return getExtension(o, c) != null;
    }

    /**
      Determine whether an object supports any of a specified set of extensions.

      @param o The object.

      @param cs The classes or interfaces that designate the extensions.

      @return true if {@code o} supports any of the extensions designated by {@code cs}, otherwise false.
    */

    public static boolean supportsAny(@Nullable Object o, Class<?>... cs)
    {
        if (o == null) {
            return false;
        }

        for (Class<?> c : cs) {
            if (supports(o, c)) {
                return true;
            }
        }

        return false;
    }
}
