package de.heinerkuecker.util;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;

public abstract class HasCreationStackTraceElement
{

    // TODO rename to creation source position
    public final StackTraceElement creationStackTraceElement;

    /**
     * Constructor with safe creation line number optional.
     */
    protected HasCreationStackTraceElement(
            final int creationStackOffset )
    {
        if ( CoroutineIterator.safeCreationSourcePosition )
        {
            creationStackTraceElement = new Exception().getStackTrace()[ creationStackOffset ];
        }
        else
        {
            creationStackTraceElement = null;
        }
    }

}
