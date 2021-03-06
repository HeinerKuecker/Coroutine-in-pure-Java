package de.heinerkuecker.coroutine.stmt.flow.exc;

import de.heinerkuecker.coroutine.stmt.flow.Break;
import de.heinerkuecker.coroutine.stmt.flow.Continue;

/**
 * Exception is throwing
 * when a loop lable for
 * {@link Break} or
 * {@link Continue}
 * is already in use
 * in the current
 * coroutine or function.
 *
 * @author Heiner K&uuml;cker
 */
public class LabelAlreadyInUseException
extends IllegalArgumentException
{
    /**
     * Serial version UID generated by eclipse.
     */
    private static final long serialVersionUID = 7213706475018911385L;

    /**
     * Constructor.
     *
     * @param s
     */
    public LabelAlreadyInUseException(
            final String s )
    {
        super(s);
    }

}
