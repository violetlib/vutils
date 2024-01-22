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
  An interface that allows the runtime discovery of additional supported interfaces.

  <p>
  This approach to the runtime discovery of interfaces is preferred over using {@code instanceof} because it can be
  supported using delegation.

  @see Extensions#getExtension
*/

public interface Extensible
{
    /**
      Return an extension, if supported.
      <p>
      Note: this method should not be called directly.
      Use {@link Extensions#getExtension}.

      @param c The class or interface that designates the extension.

      @return the extension, if supported, or null.
    */

    <T> @Nullable T getExtension(@NotNull Class<T> c);
}
