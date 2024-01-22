/*
 * Copyright (c) 2023 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import org.jetbrains.annotations.*;
import org.violetlib.annotations.Fixed;
import org.violetlib.annotations.Immutable;
import org.violetlib.types.InvalidTextException;

/*
  TBD:

  The goal is to use this model as input to cell renderers and field editors (possibly via converting view models).

  Ideally, the type parameter E should indicate whether null values are accepted.
*/

/**
  An encapsulation of the knowledge of presenting and (optionally) editing a certain class of values using text.

  <p>
  Although in most common cases, the text shown in an editor is the same text used to present the value, there are cases
  where the text to install in a text editor is different from the displayed text. One example is a floating point
  number that is rounded when presented, but shown at full precision when editied. Another example is a floating point
  number that is shown using decimal notation when presented, but is converted to integer notation for editing when the
  value is an integer. These examples demonstrate that the text to edit can be either more specific or less specific
  than the display representation.

  @param <E> The type of model values.

  <p>
  See also TextViewModel
*/

public @Immutable interface TextPresentationModel<E>
{
    /**
      Indicate whether this presentation model supports editing.

      @return true if and only if editing is supported.
    */

    @Fixed boolean isEditable();

    /**
      Indicate whether this presentation model validates supplied editor text.
      If supplied editor text is not validated, then all possible texts must be accepted.

      @return true if and only if supplied editor text is validated.
    */

    @Fixed boolean isValidating();

    /**
      Convert a value to its display representation. This method is used for display, not editing.

      @param value The value.

      @return the corresponding display representation.
    */

    @NotNull String toDisplayText(@Nullable E value);

    /**
      Convert a value to the text to display in an editor when opening an editor on the value.

      @param value The value.

      @return the corresponding text to show in a text editor.

      @throws UnsupportedOperationException if this model does not support editing.
    */

    @NotNull String toEditorText(E value)
      throws UnsupportedOperationException;

    /**
      Attempt to convert text obtained from a text editor to the appropriate value.

      @param text The text to be parsed.

      @return the value.

      @throws InvalidTextException if the text is not a valid representation.

      @throws UnsupportedOperationException if this model does not support editing.
    */

    E fromEditorText(@NotNull String text)
      throws InvalidTextException, UnsupportedOperationException;
}
