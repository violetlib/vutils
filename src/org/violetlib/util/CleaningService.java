/*
 * Copyright (c) 2020 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import java.lang.reflect.Method;

import org.jetbrains.annotations.*;
import org.violetlib.annotations.NoInstances;

/**
  A wrapper around the JDK 8 and JDK 9+ Cleaners.
*/

public final @NoInstances class CleaningService
{
    private CleaningService()
    {
    }

    /**
      Register an object and a cleaning action to run when the object becomes phantom reachable.
      @param o The object to monitor.
      @param action The action to run when {@code o} becomes phantom reachable.
      @return an object that can be used to explicitly run the cleaning action. If the cleaning action is run
        explicitly, then it is not run again after the object becomes phantom reachable.
      @throws UnsupportedOperationException if this service is not supported.
    */

    public static @NotNull Cleanable create(@NotNull Object o, @NotNull Runnable action)
      throws UnsupportedOperationException
    {
        return getImpl().create(o, action);
    }

    /**
      This interface provides the ability to explicitly run a registered cleaning action.
    */

    public interface Cleanable
    {
        /**
          Unregister the cleaning action and its associated object and invoke the cleaning action. This method has
          no effect if the cleaning action has already been performed.
        */

        void clean();
    }

    private static @Nullable CleanerImpl cleanerImpl;

    private static synchronized @NotNull CleanerImpl getImpl()
    {
        if (cleanerImpl == null) {
            cleanerImpl = createImpl();
        }
        return cleanerImpl;
    }

    private static @NotNull CleanerImpl createImpl()
    {
        CleanerImpl c = createImpl9();
        if (c != null) {
            return c;
        }
        c = createImpl8();
        if (c != null) {
            return c;
        }
        throw new UnsupportedOperationException("Unable to create the CleaningService implementation");
    }

    private static @Nullable CleanerImpl createImpl8()
    {
        try {
            Class<?> cleaner8class = Class.forName("sun.misc.Cleaner");
            Method createMethod = cleaner8class.getMethod("create", Object.class, Runnable.class);
            Method cleanMethod = cleaner8class.getMethod("clean");
            return new CleanerImpl(null, createMethod, cleanMethod);
        } catch (Throwable ignore) {
            return null;
        }
    }

    private static @Nullable CleanerImpl createImpl9()
    {
        try {
            Class<?> cleaner9class = Class.forName("java.lang.ref.Cleaner");
            Method createCleanerMethod = cleaner9class.getMethod("create");
            Object cleaner = createCleanerMethod.invoke(null);
            Method createMethod = cleaner9class.getMethod("register", Object.class, Runnable.class);
            Class<?> cleanableType = createMethod.getReturnType();
            Method cleanMethod = cleanableType.getMethod("clean");
            return new CleanerImpl(cleaner, createMethod, cleanMethod);
        } catch (Throwable ignore) {
            return null;
        }
    }

    private static class CleanerImpl
    {
        private final @Nullable Object target;
        private final @NotNull Method createMethod;
        private final @NotNull Method cleanMethod;

        public CleanerImpl(@Nullable Object target, @NotNull Method createMethod, @NotNull Method cleanMethod)
        {
            this.target = target;
            this.createMethod = createMethod;
            this.cleanMethod = cleanMethod;
        }

        public @NotNull Cleanable create(@NotNull Object o, @NotNull Runnable action)
        {
            try {
                Object c = createMethod.invoke(target, o, action);
                return new CleanableImpl(c, cleanMethod);
            } catch (Throwable ex) {
                throw new UnsupportedOperationException("Unable to create cleanable object", ex);
            }
        }
    }

    private static class CleanableImpl
      implements Cleanable
    {
        private final @NotNull Object o;
        private final @NotNull Method m;

        public CleanableImpl(@NotNull Object o, @NotNull Method m)
        {
            this.o = o;
            this.m = m;
        }

        @Override
        public void clean()
        {
            try {
                m.invoke(o);
            } catch (Throwable ignore) {
            }
        }
    }
}
