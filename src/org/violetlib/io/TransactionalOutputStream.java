/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.io;

import java.io.IOException;

/**
  A transactional output stream.
  <p>
  This interface defines two methods that terminate a transactional output operation. These methods change the state of
  the transactional output stream to {@code terminated}. In the {@code terminated} state, invocations of these methods
  have no effect and invocations of output operations throw an {@code IllegalStateException}.

  <p>
  The terminating methods are {@link #commit} and {@link #abort}. The {@link #commit commit} method must be
  called after the intended contents have been written to make the contents available, for example, as a new file.
  Otherwise, the {@link #abort abort} method must be called, both to ensure that temporary resources are released and to
  ensure that a subsequent invocation of {@link #commit}, even by accident, has no effect.

  <p>
  The above description means that one may call {@link #commit} after calling {@link #abort} or call {@link #abort}
  after calling {@link #commit}; in either case, the second method invocation will have no effect. Thus, it is possible
  and convenient to call {@link #commit} at the end of a <code>try</code> body and call {@link #abort} in a
  <code>finally</code> clause of the same <code>try</code> statement. Even more conveniently, if the transactional
  writer is created and used within a try-with-resources statement, no explicit invocation of {@link #abort} is
  required, because it will be called implicitly (as {@link #close} is defined to call {@link #abort}).
*/

public interface TransactionalOutputStream
  extends VOutputStream, IOTransactional
{
    /**
      This method has no effect if the output stream has already been committed or aborted. Otherwise, make the
      previously written contents available as appropriate, for example, in a new file.

      @throws IOException if the attempt to make the contents available failed.
    */

    void commit()
      throws IOException;

    /**
      This method has no effect if the output stream has already been committed or aborted. Otherwise, abort the output
      operation so that (if possible) no partial effects are visible.
    */

    void abort();

    /**
      The close method is defined to invoke the abort method. This behavior allows a transactional output stream to be
      used in a try-with-resources statement with no explicit invocation of {@link #abort}.
    */

    @Override
    default void close()
      throws IOException
    {
        abort();
    }
}
