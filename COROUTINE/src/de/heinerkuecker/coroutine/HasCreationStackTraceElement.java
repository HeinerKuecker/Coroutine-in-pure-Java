package de.heinerkuecker.coroutine;

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
        if ( CoroutineDebugSwitches.saveCreationSourcePosition )
        {
            creationStackTraceElement = new Exception().getStackTrace()[ creationStackOffset ];
        }
        else
        {
            creationStackTraceElement = null;
        }
    }

}
