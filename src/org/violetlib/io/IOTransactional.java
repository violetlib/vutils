/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.io;

import java.io.Closeable;
import java.io.IOException;

/**
  A transactional operation on a closable resource.
  <p>
  A transactional operation is an atomic operation on data that may be defined incrementally. Between the creation and
  termination of the operation, creation of other transactional operations may be prevented, to ensure that each
  operation starts with the latest state of the associated data.
  <p>
  This interface defines two methods that terminate a transactional operation. These methods change the state of the
  transactional object to {@code terminated}. In the {@code terminated} state, invocations of these methods have no
  effect and invocations of other operations may throw an {@code IllegalStateException}.

  <p>
  The terminating methods are {@link #commit} and {@link #abort}. The {@link #commit commit} method must be called after
  the operation has been fully defined to actually perform the operation on the data. Otherwise, the {@link #abort
  abort} method must be called, to ensure that temporary resources are released, other transactional operations on the
  data can be created, and a subsequent invocation of {@link #commit}, even by accident, has no effect.

  <p>
  The above description means that one may call {@link #commit} after calling {@link #abort} or call {@link #abort}
  after calling {@link #commit}; in either case, the second method invocation will have no effect. Thus, it is possible
  and convenient to call {@link #commit} at the end of a <code>try</code> body and call {@link #abort} in a
  <code>finally</code> clause of the same <code>try</code> statement. Even more conveniently, if the transactional
  writer is created and used within a try-with-resources statement, no explicit invocation of {@link #abort} is
  required, because it will be called implicitly (as {@link #close} is defined to call {@link #abort}).
*/

public interface IOTransactional
  extends Transactional, Closeable
{
    /**
      This method has no effect if the operation has already been committed or aborted. Otherwise, commit the operation
      by updating the associated data.

      @throws IOException if the attempt to update the data was unsuccessful.
    */

    void commit()
      throws IOException;

    /**
      This method has no effect if the operation has already been committed or aborted. Otherwise, abort the operation
      so that (if possible) no partial effects are visible.
    */

   void abort();

    /**
      The close method is defined to invoke the abort method. This behavior allows a transactional operation to be used
      in a try-with-resources statement with no explicit invocation of {@link #abort}.
    */

   @Override
   default void close()
     throws IOException
   {
       abort();
   }
}
